/*
 * Copyright 2011 Google Inc.
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

import android.app.backup.BackupManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Save {@link SharedPreferences} and provide the option to notify the
 * BackupManager to initiate a backup.
 */
public class FroyoSharedPreferenceSaver extends LegacySharedPreferenceSaver {

	protected BackupManager backupManager;

	public FroyoSharedPreferenceSaver(Context context) {
		super(context);
		backupManager = new BackupManager(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void savePreferences(Editor editor, boolean backup) {
		editor.commit();
		if (backup)
			backupManager.dataChanged();
	}

	@Override
	public void backup() {
		backupManager.dataChanged();
	}
}
