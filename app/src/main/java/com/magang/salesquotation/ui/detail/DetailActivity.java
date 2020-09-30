package com.magang.salesquotation.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.magang.salesquotation.R;
import com.magang.salesquotation.data.entity.Sales;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra position";
    public static final String EXTRA_SALES = "extra sales";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getData();
    }

    private void getData(){
        Sales sales = getIntent().getParcelableExtra(EXTRA_SALES);
    }
}