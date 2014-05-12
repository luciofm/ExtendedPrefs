package com.luciofm.extendedprefs.lib;

import java.lang.reflect.ParameterizedType;

/**
 * Created by luciofm on 5/12/14.
 */
public class PrefReader<T> {
    ExtendedPrefs prefs;
    Class<T> clazz;

    public PrefReader(ExtendedPrefs prefs) {
        this.prefs = prefs;
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T load(String key) {
        return (T) prefs.getData(key, clazz);
    }
}
