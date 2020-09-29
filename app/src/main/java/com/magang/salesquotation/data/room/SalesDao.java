package com.magang.salesquotation.data.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.magang.salesquotation.data.entity.Sales;

import java.util.List;

@Dao
public interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Sales sales);

    @Update
    void update(Sales sales);

    @Delete
    void delete(Sales sales);

    @RawQuery(observedEntities = Sales.class)
    DataSource.Factory<Integer, Sales> getAllSales(SupportSQLiteQuery query);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Sales> list);
}
