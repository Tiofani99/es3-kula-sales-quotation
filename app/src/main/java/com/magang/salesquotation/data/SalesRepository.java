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

//Repository sumber semua data di sini
public class SalesRepository {
    private SalesDao salesDao;
    private ExecutorService executorService;

    public SalesRepository(Application application){
        executorService = Executors.newSingleThreadExecutor();
        SalesRoomDatabase db = SalesRoomDatabase.getDatabase(application);
        salesDao = db.salesDao();
    }

    //Untuk mendapatkan semua data
    public DataSource.Factory<Integer,Sales>getAllSales(String sort){
        SimpleSQLiteQuery query = SortUtils.getSortedQuery(sort);
        return salesDao.getAllSales(query);
    }

    //Untuk mendapatkan data tertentu yang dicari
    public DataSource.Factory<Integer,Sales>getSearchData(String search){
        return salesDao.getSearchData(search);
    }

    //Insert Data
    public void insert(final Sales sales){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                salesDao.insert(sales);
            }
        });
    }

    //Delete Data
    public void delete(final Sales sales){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                salesDao.delete(sales);
            }
        });
    }

    //Update Data
    public void update(final Sales sales){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                salesDao.update(sales);
            }
        });
    }
}
