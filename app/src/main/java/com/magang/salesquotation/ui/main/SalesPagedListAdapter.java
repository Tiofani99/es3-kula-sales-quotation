package com.magang.salesquotation.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.magang.salesquotation.R;
import com.magang.salesquotation.data.entity.Sales;

public class SalesPagedListAdapter extends PagedListAdapter<Sales, SalesPagedListAdapter.SalesViewHolder> {

    private static DiffUtil.ItemCallback<Sales> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Sales>() {
                @Override
                public boolean areItemsTheSame(@NonNull Sales oldNote, @NonNull Sales newNote) {
                    return oldNote.getItemName().equals(newNote.getItemName()) && oldNote.getDescription().equals(newNote.getDescription());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Sales oldItem, @NonNull Sales newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private final Activity activity;

    SalesPagedListAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sales, parent, false);
        return new SalesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final SalesViewHolder holder, int position) {
        final Sales sales = getItem(position);
        if (sales != null) {
            Log.d("Coba", "Sales : " + sales.getItemName());
        }
    }


    public class SalesViewHolder extends RecyclerView.ViewHolder {

        public SalesViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}

