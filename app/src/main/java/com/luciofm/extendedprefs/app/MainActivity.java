package com.luciofm.extendedprefs.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.luciofm.extendedprefs.lib.ExtendedPrefs;
import com.luciofm.extendedprefs.lib.ExtendedPrefsEditor;
import com.luciofm.extendedprefs.lib.PrefReader;
import com.luciofm.extendedprefs.lib.PrefSaver;

import java.lang.reflect.Type;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestObject2 test2 = new TestObject2("test2", 1234, true);
        TestObject test = new TestObject("test", "object", test2);

        Log.d("ExtendedPrefs", "Saving object: " + test.toString());

        ExtendedPrefs prefs = ExtendedPrefs.getExtendedPreferences(this, "test");
        new PrefSaver<TestObject>(prefs.edit()){}.save("test", test).commit();

        TestObject saved = new PrefReader<TestObject>(prefs){}.load("test");
        Log.d("ExtendedPrefs", "Load object: " + saved.toString());


        /* Another way to read/save objects */
        ExtendedPrefsEditor editor = prefs.edit();
        Type type = new TypeToken<TestObject>(){}.getType();
        editor.putData("test2", type, test);

        saved = (TestObject) prefs.getData("test2", type);
        Log.d("ExtendedPrefs", "Load object 2: " + saved.toString());
    }
}
