package com.aiinty.lab49;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aiinty.lab49.image.ImageLibrary;

public class StartFragment extends Fragment {

    private final OnLibrarySelectListener librarySelectListener;
    public interface OnLibrarySelectListener{
        void onLibrarySelect(ImageLibrary imageLibrary);
    }

    public StartFragment(OnLibrarySelectListener librarySelectListener) {
        this.librarySelectListener = librarySelectListener;
    }

    public static StartFragment newInstance(OnLibrarySelectListener librarySelectListener) {
        return new StartFragment(librarySelectListener);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_start, container, false);

        Button picassoButton = layout.findViewById(R.id.picassoButton);
        Button glideButton = layout.findViewById(R.id.glideButton);
        Button coilButton = layout.findViewById(R.id.coilButton);

        picassoButton.setOnClickListener(v -> { librarySelectListener.onLibrarySelect(ImageLibrary.PICASSO); });
        glideButton.setOnClickListener(v -> { librarySelectListener.onLibrarySelect(ImageLibrary.GLIDE); });
        coilButton.setOnClickListener(v -> { librarySelectListener.onLibrarySelect(ImageLibrary.COIL); });

        return layout;
    }
}