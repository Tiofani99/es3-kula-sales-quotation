package com.magang.salesquotation.data.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.magang.salesquotation.data.entity.Sales;
import com.magang.salesquotation.helper.DateHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Sales.class},version = 1)
public abstract class SalesRoomDatabase extends RoomDatabase {

    private static volatile SalesRoomDatabase INSTANCE;

    public static SalesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SalesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SalesRoomDatabase.class, "sales_database")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    add();
                                }
                            })
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static void add() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Sales> list = new ArrayList<>();
                for (int i = 1 ; i <= 100 ; i++){
                    Sales dummySales = new Sales();
                    dummySales.setItemName("Barang ke "+i);
                    dummySales.setAmount(""+i);
                    dummySales.setDate(DateHelper.getCurrentDate());
                    dummySales.setDiscount(""+i);
                    dummySales.setQuantity(""+i);
                    dummySales.setUnit("Unit");
                    dummySales.setUnitPrice("100000");
                    dummySales.setTax("false");
                    dummySales.setDescription("Mantap ke "+i);
                    list.add(dummySales);
                }

                Log.d("Coba","Membuat Database");

                INSTANCE.salesDao().insertAll(list);
            }
        });
    }

    public abstract SalesDao salesDao();


}
