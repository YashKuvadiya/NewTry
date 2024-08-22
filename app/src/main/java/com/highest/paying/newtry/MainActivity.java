package com.highest.paying.newtry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.highest.paying.commonutils.ImageInflate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageInflate imageClass = findViewById(R.id.imageClass);

        imageClass.IconInflate(MainActivity.this, "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhYqPshtcc72LfIovmL7yYsotZbN8e67Ck10WVIux1e6WjjkdgCFH5_KWRB9RfBKogO6vJgt1peMuuqEyUBsMv20xohGPyZFOBiuLU-Rn27Eq33biA0lUObGvd8ToAnNuygNSoRBpBmltRADd0vpaMXGklP7L_xB9LqIlZ1fjSN6vK1i2vc_0KP-aTo/w1200-h630-p-k-no-nu/Android%20Studio%20-%20Social.png");
    }
}