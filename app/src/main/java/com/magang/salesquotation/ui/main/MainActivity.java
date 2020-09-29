package com.magang.salesquotation.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
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
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.tv_information)
    TextView tvInformation;
    SalesPagedListAdapter salesPagedListAdapter;

    private final Observer<PagedList<Sales>> salesObserver = salesList -> {
        if (salesList == null){
            tvInformation.setVisibility(View.VISIBLE);
        }
        if (salesList != null) {
            tvInformation.setVisibility(View.GONE);
            salesPagedListAdapter.submitList(salesList);
        }
    };
    private MainViewModel mainViewModel;

    @NonNull
    private static MainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        salesPagedListAdapter = new SalesPagedListAdapter(this);
        showData();
        refreshData();


        btnAddData.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddSalesQuotationActivity.class);
            startActivityForResult(intent, AddSalesQuotationActivity.REQUEST_ADD);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            showData();
            return false;
        });


    }

    private void showData() {
        rvHome.setLayoutManager(new LinearLayoutManager(this));
        rvHome.setHasFixedSize(true);
        rvHome.setAdapter(salesPagedListAdapter);
        mainViewModel = obtainViewModel(MainActivity.this);
        mainViewModel.getAllSales(SortUtils.NEWEST).observe(this, salesObserver);
    }

    private void searchData(String search) {
        rvHome.setLayoutManager(new LinearLayoutManager(this));
        rvHome.setHasFixedSize(true);
        rvHome.setAdapter(salesPagedListAdapter);
        mainViewModel = obtainViewModel(MainActivity.this);
        mainViewModel.getSearchData(search).observe(this, salesObserver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == AddSalesQuotationActivity.REQUEST_ADD) {
                if (resultCode == AddSalesQuotationActivity.RESULT_ADD) {
                    showSnackBarMessage(getString(R.string.added));
                }
            } else if (requestCode == AddSalesQuotationActivity.REQUEST_UPDATE) {
                if (resultCode == AddSalesQuotationActivity.RESULT_UPDATE) {
                    showSnackBarMessage(getString(R.string.changed));
                } else if (resultCode == AddSalesQuotationActivity.RESULT_DELETE) {
                    showSnackBarMessage(getString(R.string.deleted));
                }
            }
        }
    }

    private void showSnackBarMessage(String string) {
        Snackbar.make(rvHome, string, Snackbar.LENGTH_SHORT).show();
    }

    private void refreshData() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
                    swipeRefreshLayout.setRefreshing(false);
                    showData();
                }, 2000)
        );
    }
}