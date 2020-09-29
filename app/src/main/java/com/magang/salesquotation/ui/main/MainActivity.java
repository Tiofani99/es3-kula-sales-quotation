package com.magang.salesquotation.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.magang.salesquotation.R;
import com.magang.salesquotation.data.entity.Sales;
import com.magang.salesquotation.helper.SortUtils;
import com.magang.salesquotation.ui.addsales.AddSalesQuotationActivity;
import com.magang.salesquotation.viewmodel.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_add_data)
    Button btnAddData;
    @BindView(R.id.swipe_refresh_home)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;

    private MainViewModel mainViewModel;
    SalesPagedListAdapter salesPagedListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        salesPagedListAdapter = new SalesPagedListAdapter(this);
        rvHome.setLayoutManager(new LinearLayoutManager(this));
        rvHome.setHasFixedSize(true);
        rvHome.setAdapter(salesPagedListAdapter);

        btnAddData.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddSalesQuotationActivity.class));
        });

        mainViewModel = obtainViewModel(MainActivity.this);
        mainViewModel.getAllSales(SortUtils.NEWEST).observe(this, salesObserver);
    }

    private final Observer<PagedList<Sales>> salesObserver = salesList -> {
        if (salesList != null) {
            salesPagedListAdapter.submitList(salesList);
        }
    };
    @NonNull
    private static MainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MainViewModel.class);
    }
}