package com.aiinty.lab49.image;

public interface ImageLoadCallback {
    void onSuccess();
    void onFailure(Exception e);
}
