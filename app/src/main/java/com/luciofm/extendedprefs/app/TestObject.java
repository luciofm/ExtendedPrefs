package com.luciofm.extendedprefs.app;

/**
 * Created by luciofm on 5/12/14.
 */
public class TestObject {
    private String name;
    private String other;
    private TestObject2 test;

    public TestObject() {
    }

    public TestObject(String name, String other, TestObject2 test) {
        this.name = name;
        this.other = other;
        this.test = test;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                       "name='" + name + '\'' +
                       ", other='" + other + '\'' +
                       ", test=" + test +
                       '}';
    }
}
