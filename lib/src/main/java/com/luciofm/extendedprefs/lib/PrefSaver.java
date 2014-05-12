package com.luciofm.extendedprefs.lib;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by luciofm on 5/12/14.
 */
public class PrefSaver<T> {
    ExtendedPrefsEditor editor;

    public PrefSaver(ExtendedPrefsEditor editor) {
        this.editor = editor;
    }

    public ExtendedPrefsEditor save(String key, T data) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        editor.putData(key, clazz, data);
        return editor;
    }
}
