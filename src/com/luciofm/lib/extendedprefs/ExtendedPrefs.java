/*
 * Copyright 2011,2012 Lucio Maciel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.luciofm.lib.extendedprefs;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ExtendedPrefs {

	protected Context context;
	protected SharedPreferences mPrefs;
	protected OnExtendedPreferenceChangeListener changeExtListener;

	private static final Object mContent = new Object();
	private final WeakHashMap<OnExtendedPreferenceChangeListener, Object> mListeners = new WeakHashMap<OnExtendedPreferenceChangeListener, Object>();

	public static ExtendedPrefs getExtendedPreferences(Context context, String name) {
		return getExtendedPreferences(context, name, Context.MODE_PRIVATE);
	}

	public static ExtendedPrefs getExtendedPreferences(Context context, String name, int mode) {
		return new ExtendedPrefs(context, name, mode);
	}

	public static ExtendedPrefsEditor edit(Context context, String name) {
		return getExtendedPreferences(context, name).edit();
	}

	private ExtendedPrefs(Context context, String name, int mode) {
		this.context = context;
		mPrefs = context.getSharedPreferences(name, mode);
	}

	public ExtendedPrefsEditor edit() {
		Editor edit = mPrefs.edit();
		return new ExtendedPrefsEditor(context, edit, false);
	}

	public ExtendedPrefsEditor edit(boolean backup) {
		Editor edit = mPrefs.edit();
		return new ExtendedPrefsEditor(context, edit, backup);
	}

	public boolean contains(String key) {
		return mPrefs.contains(key);
	}

	public Map<String, ?> getAll() {
		return mPrefs.getAll();
	}

	public boolean getBoolean(String key, boolean defValue) {
		return mPrefs.getBoolean(key, defValue);
	}

	public float getFloat(String key, float defValue) {
		return mPrefs.getFloat(key, defValue);
	}

	public int getInt(String key, int defValue) {
		return mPrefs.getInt(key, defValue);
	}

	public long getLong(String key, long defValue) {
		return mPrefs.getLong(key, defValue);
	}

	public String getString(String key, String defValue) {
		return mPrefs.getString(key, defValue);
	}

	public Set<String> getStringSet(String key, Set<String> values) {
		return mPrefs.getStringSet(key, values);
	}

	public String[] getStringArray(String key, String[] values) {
		String jsonString = mPrefs.getString(key, null);
		if (TextUtils.isEmpty(jsonString))
			return values;
		Gson gson = new GsonBuilder().create();
		Type type = new TypeToken<String[]>(){}.getType();
		String[] strings = gson.fromJson(jsonString, type);
		if (strings == null)
			return values;
		return strings;
	}

	public List<?> getList(String key, Type type, List<?> values) {
		String jsonString = mPrefs.getString(key, null);
		if (TextUtils.isEmpty(jsonString))
			return values;
		Gson gson = new GsonBuilder().create();;
		List<?> ret = gson.fromJson(jsonString, type);
		if (ret != null)
			return ret;
		return values;
	}

	public Object getData(String key, Type type) {
		String jsonString = mPrefs.getString(key, null);
		if (TextUtils.isEmpty(jsonString))
			return null;
		Gson gson = new GsonBuilder().create();;
		return gson.fromJson(jsonString, type);
	}

	public void registerOnExtendedPreferenceChangeListener(
			OnExtendedPreferenceChangeListener listener) {
		mListeners.put(listener, mContent);
		mPrefs.registerOnSharedPreferenceChangeListener(changedListener);
	}

	public void unregisterOnExtendedPreferenceChangeListener(
			OnExtendedPreferenceChangeListener listener) {
		mListeners.remove(listener);
		mPrefs.unregisterOnSharedPreferenceChangeListener(changedListener);
	}

	public interface OnExtendedPreferenceChangeListener {
		/**
		 * Called when a shared preference is changed, added, or removed. This
		 * may be called even if a preference is set to its existing value.
		 * 
		 * <p>
		 * This callback will be run on your main thread.
		 * 
		 * @param sharedPreferences The {@link SharedPreferences} 
		 *                          that received the change.
		 * @param key The key of the preference that was changed,
		 *            added, or removed.
		 */
		void onExtendedPreferenceChanged(ExtendedPrefs prefs,
				String key);
	}

	private OnSharedPreferenceChangeListener changedListener = new OnSharedPreferenceChangeListener() {
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			Set<OnExtendedPreferenceChangeListener> set = mListeners.keySet();
			for (OnExtendedPreferenceChangeListener listener : set) {
				if (listener != null)
					listener.onExtendedPreferenceChanged(ExtendedPrefs.this, key);
			}
		}
	};
}
