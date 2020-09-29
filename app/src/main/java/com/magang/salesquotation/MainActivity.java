package com.magang.salesquotation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.magang.salesquotation.ui.addsales.AddSalesQuotationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_add_data)
    Button btnAddData;
    @BindView(R.id.swipe_refresh_home)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnAddData.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddSalesQuotationActivity.class));
        });
    }
}