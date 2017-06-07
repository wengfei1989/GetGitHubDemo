package com.example.github;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.util.Log;

public class ObserveUserBean {
	// private static LiteHttp mLiteHttp;
	private static MainActivity mContext;

	public static class UserInfo {
		private String login;
		private String avatarUrl;
		private OnLanguageInfoUpdate languageInfoUpdate;
		private String language;
		private int index;

		public UserInfo() {
			// TODO Auto-generated constructor stub
		}

		public void setLogin(String login) {
			this.login = login;
			// StringRequest request = new
			// StringRequest(ServerUrl.getQueryUserReposUrl(mContext, login))
			// .setMethod(HttpMethods.Get).setHttpListener(new
			// HttpListener<String>() {
			// @Override
			// public void onSuccess(String data, Response<String> response) {
			// // TODO Auto-generated method stub
			// response.printInfo();
			// statLanguageInfos(data);
			// Log.i("wengfei", data);
			// }
			//
			// @Override
			// public void onFailure(HttpException e, Response<String> response)
			// {
			// // TODO Auto-generated method stub
			// response.printInfo();
			// }
			// });
			// StringRequest stringRequest = new StringRequest(Method.GET,
			// ServerUrl.getQueryUserReposUrl(mContext, login), new
			// Listener<String>() {
			//
			// @Override
			// public void onResponse(String data) {
			// // TODO Auto-generated method stub
			// // ObserveUserBean observeUserBean =
			// // ObserveUserBean.convertToJsonBean(mContext,
			// // data);
			// // myHandler.sendMessage(myHandler.obtainMessage(0,
			// // observeUserBean));
			// statLanguageInfos(data);
			// Log.i("wengfei", data);
			// }
			// }, new ErrorListener() {
			//
			// @Override
			// public void onErrorResponse(VolleyError arg0) {
			// // TODO Auto-generated method stub
			//
			// }
			// });
			JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(ServerUrl.getQueryUserReposUrl(mContext, login), new Listener<JSONArray>() {

				@Override
				public void onResponse(JSONArray jsonArray) {
					// TODO Auto-generated method stub
					statLanguageInfos(jsonArray);
				}
			}, new ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			mContext.getRequestQueue().add(jsonArrayRequest);
		}

		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}

		public String getLogin() {
			return login;
		}

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public String getLanguage() {
			return language;
		}

		private void statLanguageInfos(JSONArray jsonArray) {
			int max = 0;
			String lauange = null;
			try {
//				JSONArray jsonArray = j;
				List<String> strings = new ArrayList<String>();
				int count = jsonArray.length();
				for (int i = 0; i < count; i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					strings.add(object.getString("language"));
				}
				Map<String, Integer> map = new HashMap<String, Integer>();
				for (String string : strings) {
					if ("null".equals(string)) {
						continue;
					}
					if (map.containsKey(string)) {
						map.put(string, map.get(string) + 1);
						if (max < map.get(string)) {
							max = map.get(string);
							lauange = string;
						}
					} else {
						map.put(string, 1);
						if (max == 0) {
							max = 1;
							lauange = string;
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (lauange != null) {
				this.language = lauange;
				if (languageInfoUpdate != null) {
					languageInfoUpdate.onUpdate(index, lauange);
				}
			}
		}

		public void setOnLanguageInfoUpdate(Context mContext, OnLanguageInfoUpdate languageInfoUpdate, int index) {
			this.languageInfoUpdate = languageInfoUpdate;
			this.index = index;
		}
	}

	public interface OnLanguageInfoUpdate {
		public void onUpdate(int index, String language);
	}

	public ObserveUserBean() {
		// TODO Auto-generated constructor stub
		// this.mLiteHttp=liteHttp;
		userInfos = new ArrayList<ObserveUserBean.UserInfo>();
	}

	private ArrayList<UserInfo> userInfos;

	private void addUserInfo(UserInfo userInfo) {
		userInfos.add(userInfo);
	}

	public List<UserInfo> getUserInfo() {
		return userInfos;
	}

	public static ObserveUserBean convertToJsonBean(MainActivity mContext, JSONObject jsonTopObj) {
		// mLiteHttp = liteHttp;
		ObserveUserBean jsonBean = null;
		ObserveUserBean.mContext = mContext;
		try {
//			JSONObject jsonTopObj = new JSONObject(jsonStr);
			int count = jsonTopObj.getInt("total_count");
			JSONArray jsonArray = jsonTopObj.getJSONArray("items");
			UserInfo userInfo;
			jsonBean = new ObserveUserBean();
			for (int i = 0; i < count; i++) {
				userInfo = new UserInfo();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				userInfo.setLogin(jsonObject.getString("login"));
				userInfo.setAvatarUrl(jsonObject.getString("avatar_url"));
				jsonBean.addUserInfo(userInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonBean;
	}
}
