package com.magang.salesquotation.ui.addsales;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.magang.salesquotation.data.SalesRepository;
import com.magang.salesquotation.data.entity.Sales;

public class SalesAddViewModel extends ViewModel {
    private SalesRepository mSalesRepository;

    public SalesAddViewModel(Application application) {
        mSalesRepository = new SalesRepository(application);
    }

    public void insert(Sales sales) {
        mSalesRepository.insert(sales);
    }

    public void update(Sales sales) {
        mSalesRepository.update(sales);
    }

    public void delete(Sales sales) {
        mSalesRepository.delete(sales);
    }
}
