package com.magang.salesquotation.ui.addsales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.magang.salesquotation.R;
import com.magang.salesquotation.data.entity.Sales;
import com.magang.salesquotation.helper.DateHelper;
import com.magang.salesquotation.viewmodel.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSalesQuotationActivity extends AppCompatActivity {

    public static final String EXTRA_SALES = "extra_sales";
    public static final String EXTRA_POSITION = "extra_position";

    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    @BindView(R.id.btn_add_data)
    Button btnSubmit;


    private boolean isEdit = false;
    private Sales sales;
    private int position;
    private SalesAddViewModel salesAddViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales_quotation);
        ButterKnife.bind(this);
        salesAddViewModel = obtainViewModel(AddSalesQuotationActivity.this);

        sales = getIntent().getParcelableExtra(EXTRA_SALES);
        if (sales != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            sales = new Sales();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);

            if (sales != null) {
                //Ketika update
            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSubmit.setText(btnTitle);
        btnSubmit.setOnClickListener(view -> {
//            String title = edtTitle.getText().toString().trim();
//            String description = edtDescription.getText().toString().trim();

//            if (title.isEmpty()) {
//                edtTitle.setError(getString(R.string.empty));
//            } else if (description.isEmpty()) {
//                edtDescription.setError(getString(R.string.empty));
//            } else {
//                sales.setItemName();


            Intent intent = new Intent();
            intent.putExtra(EXTRA_SALES, sales);
            intent.putExtra(EXTRA_POSITION, position);

            if (isEdit) {
                salesAddViewModel.update(sales);
                setResult(RESULT_UPDATE, intent);
            } else {
                sales.setDate(DateHelper.getCurrentDate());
                salesAddViewModel.insert(sales);
                setResult(RESULT_ADD, intent);
            }
            finish();
//            }
        });
    }

    @NonNull
    private SalesAddViewModel obtainViewModel(AddSalesQuotationActivity addSalesQuotationActivity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(addSalesQuotationActivity.getApplication());

        return ViewModelProviders.of(addSalesQuotationActivity, factory).get(SalesAddViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;

            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle;
        String dialogMessage;

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);
        } else {
            dialogTitle = getString(R.string.delete);
            dialogMessage = getString(R.string.message_delete);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogClose) {
                            finish();
                        } else {
                            salesAddViewModel.delete(sales);

                            Intent intent = new Intent();
                            intent.putExtra(EXTRA_POSITION, position);
                            setResult(RESULT_DELETE, intent);
                            finish();
                        }
                    }
                })

                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}