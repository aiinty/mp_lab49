package com.aiinty.lab49.image.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;

import com.aiinty.lab49.image.ImageLoadCallback;
import com.aiinty.lab49.image.ImageLoadersClass;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PicassoImageLoader extends ImageLoadersClass {
    public PicassoImageLoader(Context context) {
        super(context);
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, ImageView imageView, int placeholderResId) {
        Picasso.get()
                .load(url)
                .placeholder(placeholderResId)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, ImageView imageView, int placeholderResId, int errorResId) {
        Picasso.get()
                .load(url)
                .placeholder(placeholderResId)
                .error(errorResId)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, ImageView imageView, ImageLoadCallback callback) {
        Picasso.get()
                .load(url)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("!", "Successful");
                        callback.onSuccess();
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("!", "Error");
                        callback.onFailure(e);
                    }
                });
    }

    @Override
    public void resizeImageDefault(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    @Override
    public void resizeImageCustom(String url, ImageView imageView, int resizeWidth, int resizeHeight) {
        Picasso.get()
                .load(url)
                .resize(resizeWidth, resizeHeight)
                .into(imageView);
    }

    @Override
    public void centerCrop(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .resize(imageView.getWidth(), imageView.getHeight())
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void centerInside(String url, ImageView imageView) {

        Picasso.get()
                .load(url)
                .resize(imageView.getWidth(), imageView.getHeight())
                .centerInside()
                .into(imageView);
    }

    @Override
    public void fit(String url, ImageView imageView) {
        Picasso.get()
                .load(url)

                .fit()
                .into(imageView);
    }

    @Override
    public void rotateDefault(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }

    @Override
    public void rotateCustom(String url, ImageView imageView, float rotateF) {
        if (rotateF != 0) {
            Picasso.get()
                    .load(url)
                    .rotate(rotateF)
                    .into(imageView);
        } else {
            rotateDefault(url, imageView);
        }
    }

    @Override
    public void customTransform(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .transform(new CircleTransform())
                .into(imageView);
    }

    private class CircleTransform implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source){
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }
}
