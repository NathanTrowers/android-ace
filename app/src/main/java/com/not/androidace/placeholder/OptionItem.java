package com.not.androidace.placeholder;

public class OptionItem {

    public final String id;
    public final String content;

    public OptionItem(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
