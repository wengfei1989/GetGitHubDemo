package com.example.github;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.github.ObserveUserBean.OnLanguageInfoUpdate;
import com.example.github.ObserveUserBean.UserInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserInfoAdapter extends BaseAdapter implements OnLanguageInfoUpdate {
	private List<UserInfo> mUserInfos;
	private Context mContext;
	private RequestQueue queue;
	private ImageLoader imageLoader;

	public UserInfoAdapter(MainActivity context, List<UserInfo> userInfos) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mUserInfos = userInfos;
		queue = context.getRequestQueue();
		imageLoader = new ImageLoader(queue, new BitmapCache());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mUserInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mUserInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolde viewHolde;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.list_item, null);
			viewHolde = new ViewHolde();
			viewHolde.mLogin = (TextView) view.findViewById(R.id.login_name);
			viewHolde.mUserIcon = (NetworkImageView) view.findViewById(R.id.user_icon);
			viewHolde.mLanguage = (TextView) view.findViewById(R.id.code_language);
			view.setTag(viewHolde);
		} else {
			viewHolde = (ViewHolde) view.getTag();
		}
		UserInfo userInfo = mUserInfos.get(index);
		final String imgUrl = userInfo.getAvatarUrl();
		viewHolde.mLogin.setText(userInfo.getLogin());
		if (imgUrl != null && !imgUrl.equals("")) {
			viewHolde.mUserIcon.setDefaultImageResId(R.drawable.ic_launcher);
			viewHolde.mUserIcon.setErrorImageResId(R.drawable.ic_launcher);
			viewHolde.mUserIcon.setImageUrl(imgUrl, imageLoader);
		}
		if (userInfo.getLanguage() != null) {
			viewHolde.mLanguage.setText(userInfo.getLanguage());
		} else {
			userInfo.setOnLanguageInfoUpdate(mContext, this, index);
		}
		return view;
	}

	private class ViewHolde {
		public TextView mLogin;
		public NetworkImageView mUserIcon;
		public TextView mLanguage;
	}

	@Override
	public void onUpdate(int index, String language) {
		// TODO Auto-generated method stub
		Log.i("wengf", "index=" + index + ":language=" + language);
		notifyDataSetChanged();
	}

	// public void updataView(int posi, ListView listView) {
	// int visibleFirstPosi = listView.getFirstVisiblePosition();
	// int visibleLastPosi = listView.getLastVisiblePosition();
	// if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
	// View view = listView.getChildAt(posi - visibleFirstPosi);
	// ViewHolde holder = (ViewHolde) view.getTag();
	//   
	//    String txt = holder.strText.getText().toString();
	//    txt = txt + "++;";
	//    holder.strText.setText(txt);
	//    strList.set(posi, txt);
	//   } else {
	//    String txt = strList.get(posi);
	//    txt = txt + "++;";
	//    strList.set(posi, txt);
	//   }
	//  }
}
