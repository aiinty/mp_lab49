package com.aiinty.lab49;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aiinty.lab49.image.ImageLibrary;

public class MainActivity extends AppCompatActivity implements StartFragment.OnLibrarySelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            changeFragment(StartFragment.newInstance(this));
        }
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, fragment)
                .commit();
    }

    @Override
    public void onLibrarySelect(ImageLibrary imageLibrary) {
        changeFragment(MainFragment.newInstance(imageLibrary));
    }
}