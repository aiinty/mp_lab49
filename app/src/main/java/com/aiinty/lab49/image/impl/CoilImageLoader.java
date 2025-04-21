package com.aiinty.lab49.image.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aiinty.lab49.image.ImageLoadCallback;
import com.aiinty.lab49.image.ImageLoadersClass;

import coil.ImageLoader;
import coil.request.ImageRequest;
import coil.size.Scale;
import coil.size.Size;
import coil.target.Target;
import coil.transform.CircleCropTransformation;
import coil.transform.Transformation;
import kotlin.coroutines.Continuation;

public class CoilImageLoader extends ImageLoadersClass {
    ImageLoader imageLoader = new ImageLoader.Builder(context).build();

    public CoilImageLoader(Context context) {
        super(context);
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void loadImage(String url, ImageView imageView, int placeholderResId) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .placeholder(placeholderResId)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void loadImage(String url, ImageView imageView, int placeholderResId, int errorResId) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .placeholder(placeholderResId)
                .error(errorResId)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void loadImage(String url, ImageView imageView, ImageLoadCallback callback) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .target(new Target() {
                    @Override
                    public void onError(@Nullable Drawable error) {
                        callback.onFailure(new Exception());
                        Target.super.onError(error);
                    }

                    @Override
                    public void onStart(@Nullable Drawable placeholder) {
                        Target.super.onStart(placeholder);
                    }

                    @Override
                    public void onSuccess(@NonNull Drawable result) {
                        callback.onSuccess();
                        Target.super.onSuccess(result);
                    }
                })
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void resizeImageDefault(String url, ImageView imageView) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void resizeImageCustom(String url, ImageView imageView, int resizeWidth, int resizeHeight) {
        if (resizeWidth != 0 && resizeHeight != 0) {
            ImageRequest request = new ImageRequest.Builder(context)
                    .data(url)
                    .target(imageView)
                    .size(resizeWidth, resizeHeight)
                    .build();

            imageLoader.enqueue(request);
        } else {
            resizeImageDefault(url, imageView);
        }
    }

    @Override
    public void centerCrop(String url, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void centerInside(String url, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void fit(String url, ImageView imageView) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .scale(Scale.FIT)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void rotateDefault(String url, ImageView imageView) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    @Override
    public void rotateCustom(String url, ImageView imageView, float rotateF) {
        if (rotateF != 0) {
            ImageRequest request = new ImageRequest.Builder(context)
                    .data(url)
                    .transformations(new RotateTransformation(rotateF))
                    .target(imageView)
                    .build();

            imageLoader.enqueue(request);
        } else {
            rotateDefault(url, imageView);
        }
    }

    @Override
    public void customTransform(String url, ImageView imageView) {
        ImageRequest request = new ImageRequest.Builder(context)
                .data(url)
                .transformations(new CircleCropTransformation())
                .target(imageView)
                .build();

        imageLoader.enqueue(request);
    }

    private class RotateTransformation implements Transformation {
        private final float degrees;

        public RotateTransformation(float degrees) {
            this.degrees = degrees;
        }

        @NonNull
        @Override
        public String getCacheKey() {
            return "rotate_" + degrees;
        }

        @Nullable
        @Override
        public Object transform(@NonNull Bitmap bitmap, @NonNull Size size, @NonNull Continuation<? super Bitmap> continuation) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }
}
