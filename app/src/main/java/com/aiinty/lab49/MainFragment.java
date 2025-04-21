package com.aiinty.lab49;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aiinty.lab49.image.ImageLibrary;
import com.aiinty.lab49.image.ImageLoadCallback;
import com.aiinty.lab49.image.ImageLoaderFactory;
import com.aiinty.lab49.image.ImageLoadersClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainFragment extends Fragment {

    private final static String BASE_URL = "https://static.wikia.nocookie.net/megamitensei/images/b/b5/P5_Sojiro_Sakura_sad.png/revision/latest?cb=20200610232953";
    private final ImageLibrary imageLibrary;
    private ImageLoadersClass imageLoader;

    public MainFragment(ImageLibrary imageLibrary) {
        this.imageLibrary = imageLibrary;
    }

    public static MainFragment newInstance(ImageLibrary imageLibrary) {
        return new MainFragment(imageLibrary);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoaderFactory.create(getContext(), imageLibrary);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        EditText widthBox = layout.findViewById(R.id.widthBox);
        EditText heightBox = layout.findViewById(R.id.heightBox);
        EditText angleBox = layout.findViewById(R.id.angleBox);

        ImageView imageView = layout.findViewById(R.id.imageView);

        Button defaultButton = layout.findViewById(R.id.defaultButton);
        Button resizeButton = layout.findViewById(R.id.resizeButton);
        Button cropButton = layout.findViewById(R.id.cropButton);
        Button insideButton = layout.findViewById(R.id.insideButton);
        Button fitButton = layout.findViewById(R.id.fitButton);
        Button placeholderButton = layout.findViewById(R.id.placeholderButton);
        Button callbackButton = layout.findViewById(R.id.callbackButton);
        Button errorButton = layout.findViewById(R.id.errorButton);
        Button rotateButton = layout.findViewById(R.id.rotateButton);
        Button circleButton = layout.findViewById(R.id.circleButton);
        Button gifButton = layout.findViewById(R.id.gifButton);

        defaultButton.setOnClickListener(v -> {
            imageLoader.loadImage(BASE_URL, imageView);
            Toast.makeText(getContext(), "Default called", Toast.LENGTH_SHORT).show();
        });
        resizeButton.setOnClickListener(v -> {
            if (widthBox.getText().toString().isEmpty() || heightBox.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Fill the width and height fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int width = Integer.parseInt(widthBox.getText().toString());
            int height = Integer.parseInt(heightBox.getText().toString());
            imageLoader.resizeImageCustom(BASE_URL, imageView, width, height);
            Toast.makeText(getContext(), "Resize called", Toast.LENGTH_SHORT).show();
        });
        cropButton.setOnClickListener(v -> {
            imageLoader.centerCrop(BASE_URL, imageView);
            Toast.makeText(getContext(), "Crop called", Toast.LENGTH_SHORT).show();
        });
        insideButton.setOnClickListener(v -> {
            imageLoader.centerInside(BASE_URL, imageView);
            Toast.makeText(getContext(), "Inside called", Toast.LENGTH_SHORT).show();
        });
        fitButton.setOnClickListener(v -> {
            imageLoader.fit(BASE_URL, imageView);
            Toast.makeText(getContext(), "Fit called", Toast.LENGTH_SHORT).show();
        });
        placeholderButton.setOnClickListener(v -> {
            imageLoader.loadImage(BASE_URL, imageView, R.drawable.placeholder);
            Toast.makeText(getContext(), "Placeholder called", Toast.LENGTH_SHORT).show();
        });
        callbackButton.setOnClickListener(v -> {
            imageLoader.loadImage(BASE_URL, imageView, new ImageLoadCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getContext(), "Loading successful!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getContext(), "Loading failed!", Toast.LENGTH_SHORT).show();
                }
            });
            Toast.makeText(getContext(), "Callback called", Toast.LENGTH_SHORT).show();
        });
        errorButton.setOnClickListener(v -> {
            imageLoader.loadImage(BASE_URL, imageView, R.drawable.placeholder, R.drawable.error);
            Toast.makeText(getContext(), "Error called", Toast.LENGTH_SHORT).show();
        });
        rotateButton.setOnClickListener(v -> {
            if (angleBox.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Fill the angle field", Toast.LENGTH_SHORT).show();
                return;
            }

            float angle = Float.parseFloat(angleBox.getText().toString());
            imageLoader.rotateCustom(BASE_URL, imageView, angle);
            Toast.makeText(getContext(), "Rotate called", Toast.LENGTH_SHORT).show();
        });
        circleButton.setOnClickListener(v -> {
            imageLoader.customTransform(BASE_URL, imageView);
            Toast.makeText(getContext(), "Circle called", Toast.LENGTH_SHORT).show();
        });
        gifButton.setOnClickListener(v -> {
            Glide.with(this)
                            .asGif()
                            .load("https://media1.tenor.com/m/hKbSHQjJg3YAAAAd/cat-stare.gif")
                            .error(R.drawable.error)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(imageView);
            Toast.makeText(getContext(), "Gif called", Toast.LENGTH_SHORT).show();
        });

        return layout;
    }
}