package net.cachapa.data.protiumtest;

public class TestGateway {
    public int mValue;
    
    public TestGateway(int value) {
        mValue = value;
    }
    
    public int getValue() throws InterruptedException {
        Thread.sleep(100 * mValue);
        return mValue;
    }
}
