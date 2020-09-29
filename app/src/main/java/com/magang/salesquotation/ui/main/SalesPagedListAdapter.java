package com.magang.salesquotation.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.magang.salesquotation.R;
import com.magang.salesquotation.data.entity.Sales;
import com.magang.salesquotation.ui.addsales.AddSalesQuotationActivity;
import com.magang.salesquotation.ui.detail.DetailActivity;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            holder.bind(sales);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_POSITION, holder.getAdapterPosition());
                intent.putExtra(DetailActivity.EXTRA_SALES, sales);
                activity.startActivity(intent);
            });

            holder.itemView.setOnLongClickListener(view -> {
                holder.setLong(sales,holder.getAdapterPosition());
                return false;
            });
        }


    }


    public class SalesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView tvName;
        @BindView(R.id.tv_item_desc)
        TextView tvDesc;
        @BindView(R.id.tv_item_price)
        TextView tvPrice;

        public SalesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Sales sales) {
            tvName.setText(sales.getItemName());
            tvDesc.setText(sales.getDescription());
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            tvPrice.setText(formatRupiah.format(Double.parseDouble(String.valueOf(sales.getUnitPrice()))));
        }

        public void setLong(Sales sales, int position) {

            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            String update = activity.getResources().getString(R.string.update);
            String[] options = {update};

            builder.setItems(options, (dialogInterface, i) -> {
                if (i == 0) {
                    Intent intent = new Intent(activity, AddSalesQuotationActivity.class);
                    intent.putExtra(AddSalesQuotationActivity.EXTRA_POSITION, position);
                    intent.putExtra(AddSalesQuotationActivity.EXTRA_SALES, sales);
                    activity.startActivityForResult(intent, AddSalesQuotationActivity.REQUEST_UPDATE);
                }
            });

            builder.create().show();
        }


    }

}

