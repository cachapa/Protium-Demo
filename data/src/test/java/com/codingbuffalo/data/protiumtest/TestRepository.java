package com.codingbuffalo.data.protiumtest;

public class TestRepository {
    public int mValue;
    
    public TestRepository(int value) {
        mValue = value;
    }
    
    public int getValue() throws InterruptedException {
        Thread.sleep(100 * mValue);
        return mValue;
    }
}
