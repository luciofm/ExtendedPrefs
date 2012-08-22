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
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Abstract base class that can be extended to provide classes that save
 * {@link SharedPreferences} in the most efficient way possible. Decendent
 * classes can optionally choose to backup some {@link SharedPreferences} to the
 * Google {@link BackupService} on platforms where this is available.
 */
public class ExtendedPrefsEditor {

	protected Editor mEditor;
	protected Context context;
	protected SharedPreferenceSaver mSaver;
	protected boolean backup;

	protected ExtendedPrefsEditor(Context context, Editor editor, boolean backup) {
		this.context = context;
		this.mEditor = editor;
		this.backup = backup;
		mSaver = PlatformSpecificImplementationFactory.getSharedPreferenceSaver(context);
	}

	public void apply() {
		mSaver.savePreferences(mEditor, false);
	}

	public ExtendedPrefsEditor clear() {
		mEditor = mEditor.clear();
		return this;
	}

	public boolean commit() {
		mSaver.savePreferences(mEditor, false);
		return true;
	}

	public void backup() {
		mSaver.backup();
	}

	public ExtendedPrefsEditor putBoolean(String key, boolean value) {
		mEditor.putBoolean(key, value);
		return this;
	}

	public ExtendedPrefsEditor putFloat(String key, float value) {
		mEditor.putFloat(key, value);
		return this;
	}

	public ExtendedPrefsEditor putInt(String key, int value) {
		mEditor.putInt(key, value);
		return this;
	}

	public ExtendedPrefsEditor putLong(String key, long value) {
		mEditor.putLong(key, value);
		return this;
	}

	public ExtendedPrefsEditor putString(String key, String value) {
		mEditor.putString(key, value);
		return this;
	}

	public ExtendedPrefsEditor putStringSet(String key, Set<String> value) {
		mEditor.putStringSet(key, value);
		return this;
	}

	public ExtendedPrefsEditor putStringArray(String key, String[] values) {
		Gson gson = new GsonBuilder().create();
		Type type = new TypeToken<String[]>(){}.getType();
		String jsonValue = gson.toJson(values, type);
		if (jsonValue != null)
			mEditor.putString(key, jsonValue);
		return this;
	}

	public ExtendedPrefsEditor putList(String key, Type type, List<?> values) {
		Gson gson = new GsonBuilder().create();
		String jsonValue = gson.toJson(values, type);
		if (jsonValue != null)
			mEditor.putString(key, jsonValue);
		return this;
	}

	public ExtendedPrefsEditor putData(String key, Type type, Object value) {
		Gson gson = new GsonBuilder().create();
		String jsonValue = gson.toJson(value, type);
		if (jsonValue != null)
			mEditor.putString(key, jsonValue);
		return this;
	}

	public ExtendedPrefsEditor remove(String key) {
		mEditor.remove(key);
		return this;
	}
}
