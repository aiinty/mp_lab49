package com.aiinty.lab49.image;

import android.content.Context;

import com.aiinty.lab49.image.impl.CoilImageLoader;
import com.aiinty.lab49.image.impl.GlideImageLoader;
import com.aiinty.lab49.image.impl.PicassoImageLoader;

public class ImageLoaderFactory {
    public static ImageLoadersClass create(Context context, ImageLibrary loaderType) {
        switch (loaderType){
            case GLIDE:
                return new GlideImageLoader(context);
            case PICASSO:
                return new PicassoImageLoader(context);
            case COIL:
                return new CoilImageLoader(context);
            default:
                throw new IllegalArgumentException("Unknown loader type");
        }
    }
}