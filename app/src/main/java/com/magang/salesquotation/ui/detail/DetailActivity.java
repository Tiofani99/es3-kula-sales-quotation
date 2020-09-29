package com.magang.salesquotation.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.magang.salesquotation.R;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra position";
    public static final String EXTRA_SALES = "extra sales";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}