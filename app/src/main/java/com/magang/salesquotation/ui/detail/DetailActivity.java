package com.magang.salesquotation.ui.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.magang.salesquotation.R;
import com.magang.salesquotation.data.entity.Sales;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra position";
    public static final String EXTRA_SALES = "extra sales";

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_detail_name)
    TextView tvName;
    @BindView(R.id.tv_detail_date)
    TextView tvDate;
    @BindView(R.id.tv_detail_Description)
    TextView tvDesc;
    @BindView(R.id.tv_qty)
    TextView tvQty;
    @BindView(R.id.tv_satuan)
    TextView tvSatuan;
    @BindView(R.id.tv_unit_price)
    TextView tvUnitPrice;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_tax)
    TextView tvTax;
    @BindView(R.id.tv_subtotal)
    TextView tvSubTotal;
    @BindView(R.id.tv_total_tax)
    TextView tvTotalTax;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        initTitle();
        getData();
    }

    private void initTitle() {
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle("Detail Item");

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.colorWhite));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.colorWhite));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Item");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    @SuppressLint("SetTextI18n")
    private void getData() {
        Sales sales = getIntent().getParcelableExtra(EXTRA_SALES);
        assert sales != null;
        tvName.setText(sales.getItemName());
        tvDate.setText(sales.getDate());
        tvDesc.setText(sales.getDescription());
        tvQty.setText(sales.getQuantity());
        tvSatuan.setText(sales.getUnit());
        convert(tvUnitPrice, sales.getUnitPrice());
        Log.d("Coba",""+sales.getDiscount());
        tvDiscount.setText(sales.getDiscount() + "%");
        double unitPrice = Integer.parseInt(sales.getUnitPrice());
        double quantity = Integer.parseInt(sales.getQuantity());
        double total = unitPrice * quantity * 0.1;
        String sTotal = String.valueOf(total);
        if (sales.getTax().equals("true")) {
            tvTax.setText("10%");
            convert(tvTotalTax, sTotal);
        } else {
            tvTax.setText("0");
            tvTotalTax.setText("0");

        }
        convert(tvSubTotal, sales.getAmount());

    }

    private void convert(TextView textView, String price) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        textView.setText(formatRupiah.format(Double.parseDouble(String.valueOf(price))));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}