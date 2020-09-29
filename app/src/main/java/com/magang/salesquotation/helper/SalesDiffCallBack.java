package com.magang.salesquotation.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.magang.salesquotation.data.entity.Sales;

import java.util.List;

public class SalesDiffCallBack extends DiffUtil.Callback {

    private final List<Sales> mOldSalesList;
    private final List<Sales> mNewSalesList;

    public SalesDiffCallBack(List<Sales> mOldSalesList, List<Sales> mNewSalesList) {
        this.mOldSalesList = mOldSalesList;
        this.mNewSalesList = mNewSalesList;
    }

    @Override
    public int getOldListSize() {
        return mOldSalesList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewSalesList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldSalesList.get(oldItemPosition).getId() == mNewSalesList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Sales oldEmployee = mOldSalesList.get(oldItemPosition);
        final Sales newEmployee = mNewSalesList.get(newItemPosition);

        return oldEmployee.getItemName().equals(newEmployee.getItemName()) && oldEmployee.getDescription().equals(newEmployee.getDescription());

    }
}
