package com.magang.salesquotation.ui.main;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.magang.salesquotation.data.SalesRepository;
import com.magang.salesquotation.data.entity.Sales;

public class MainViewModel extends ViewModel {
    private SalesRepository mSalesRepository;

    public MainViewModel(Application application) {
        mSalesRepository = new SalesRepository(application);
    }

    LiveData<PagedList<Sales>> getAllSales(String sort) {
        return new LivePagedListBuilder<>(mSalesRepository.getAllSales(sort), 20).build();
    }
}
