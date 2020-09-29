package com.magang.salesquotation.ui.addsales;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.magang.salesquotation.R;

public class AddSalesQuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales_quotation);
        initTitle();
    }

    private void initTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.add_data));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showAlertDialog() {
        String dialogTitle;
        String dialogMessage;

        dialogTitle = "Kembali";
        dialogMessage = "Apakah anda yakin ingin kembali ?";


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> finish())
                .setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        showAlertDialog();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        showAlertDialog();
    }
}