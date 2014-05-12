package com.luciofm.extendedprefs.app;

/**
 * Created by luciofm on 5/12/14.
 */
public class TestObject2 {
    private String string1;
    private long number;
    private boolean trueOrFalse;

    public TestObject2() {
    }

    public TestObject2(String string1, long number, boolean trueOrFalse) {
        this.string1 = string1;
        this.number = number;
        this.trueOrFalse = trueOrFalse;
    }

    @Override
    public String toString() {
        return "TestObject2{" +
                       "string1='" + string1 + '\'' +
                       ", number=" + number +
                       ", trueOrFalse=" + trueOrFalse +
                       '}';
    }
}
