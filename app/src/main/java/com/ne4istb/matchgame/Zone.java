package com.ne4istb.matchgame;

public class Zone {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public Zone(int left, int top, int right, int bottom, float factor, int offset) {
        this.left = (int) (left * factor);
        this.top = (int) (top * factor + offset);
        this.right = (int) (right * factor);
        this.bottom = (int) (bottom * factor + offset);
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }
}
