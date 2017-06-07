package com.example.github;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class BitmapCache implements ImageCache {
	private LruCache<String, Bitmap> mCache;

	public BitmapCache() {
		int maxSize = 4 * 1024 * 1024;
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// return bitmap.getByteCount(); // >api12 才能用
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		if (bitmap != null) {
			mCache.put(url, bitmap);
		}
	}
}
