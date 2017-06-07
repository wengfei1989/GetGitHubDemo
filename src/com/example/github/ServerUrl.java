package com.example.github;

import android.content.Context;

public class ServerUrl {
	public static String getQueryUserInfoUrl(Context context, String userName) {
		String url = context.getResources().getString(R.string.query_user_info_url, userName);
		return url;
	}

	public static String getQueryUserReposUrl(Context context, String userName) {
		String url = context.getResources().getString(R.string.query_user_repos_info_url, userName);
		return url;
	}
}
