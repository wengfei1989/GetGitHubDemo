package com.example.github;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	private EditText userInfoEt;
	private Button queryBt;
	private ListView userInfos;
	private MainActivity mContext;
	private RequestQueue mRequestQueue;

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		userInfoEt = (EditText) findViewById(R.id.user_info_et);
		queryBt = (Button) findViewById(R.id.query_bt);
		userInfos = (ListView) findViewById(R.id.user_infos_lv);
		queryBt.setOnClickListener(this);
		// mLiteHttp = LiteHttp.build(this).create();
		mRequestQueue = Volley.newRequestQueue(mContext);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				userInfos.setAdapter(new UserInfoAdapter(mContext, ((ObserveUserBean) msg.obj).getUserInfo()));
				break;

			default:
				break;
			}
		};
	};

	public void execute(final String userName) {
		// StringRequest request = new
		// StringRequest(ServerUrl.getQueryUserInfoUrl(mContext, userName))
		// .setMethod(HttpMethods.Get).setHttpListener(new
		// HttpListener<String>() {
		// @Override
		// public void onSuccess(String data, Response<String> response) {
		// // TODO Auto-generated method stub
		// response.printInfo();
		// ObserveUserBean observeUserBean =
		// ObserveUserBean.convertToJsonBean(mContext, mLiteHttp, data);
		// myHandler.sendMessage(myHandler.obtainMessage(0, observeUserBean));
		// }
		//
		// @Override
		// public void onFailure(HttpException e, Response<String> response) {
		// // TODO Auto-generated method stub
		// response.printInfo();
		// }
		// });
//		StringRequest stringRequest = new StringRequest(Method.GET, ServerUrl.getQueryUserInfoUrl(mContext, userName),
//				new Listener<String>() {
//
//					@Override
//					public void onResponse(String data) {
//						// TODO Auto-generated method stub
//						Log.i("wengfei", data);
//						ObserveUserBean observeUserBean = ObserveUserBean.convertToJsonBean(mContext, data);
//						myHandler.sendMessage(myHandler.obtainMessage(0, observeUserBean));
//					}
//				}, new ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError arg0) {
//						// TODO Auto-generated method stub
//
//					}
//				});
		// mLiteHttp.executeAsync(request);
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Method.GET, ServerUrl.getQueryUserInfoUrl(mContext, userName), null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject data) {
				// TODO Auto-generated method stub
				Log.i("wengfei", data.toString());
				ObserveUserBean observeUserBean = ObserveUserBean.convertToJsonBean(mContext, data);
				myHandler.sendMessage(myHandler.obtainMessage(0, observeUserBean));
			}
				}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		mRequestQueue.add(jsonObjectRequest);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == queryBt) {
			String userInfoS = userInfoEt.getText().toString();
			if (userInfoS != null) {
				execute(userInfoS);
			}
		}
	}
}
