package com.magang.salesquotation.data;

import android.app.Application;

import androidx.paging.DataSource;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.magang.salesquotation.data.entity.Sales;
import com.magang.salesquotation.data.room.SalesDao;
import com.magang.salesquotation.data.room.SalesRoomDatabase;
import com.magang.salesquotation.helper.SortUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SalesRepository {
    private SalesDao salesDao;
    private ExecutorService executorService;

    public SalesRepository(Application application){
        executorService = Executors.newSingleThreadExecutor();
        SalesRoomDatabase db = SalesRoomDatabase.getDatabase(application);
        salesDao = db.salesDao();
    }

    public DataSource.Factory<Integer,Sales>getAllSales(String sort){
        SimpleSQLiteQuery query = SortUtils.getSortedQuery(sort);
        return salesDao.getAllSales(query);
    }

    public DataSource.Factory<Integer,Sales>getSearchData(String search){
        return salesDao.getSearchData(search);
    }

    public void insert(final Sales sales){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                salesDao.insert(sales);
            }
        });
    }

    public void delete(final Sales sales){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                salesDao.delete(sales);
            }
        });
    }

    public void update(final Sales sales){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                salesDao.update(sales);
            }
        });
    }
}
