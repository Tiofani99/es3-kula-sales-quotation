package com.magang.salesquotation.ui.addsales;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.magang.salesquotation.R;
import com.magang.salesquotation.data.entity.Sales;
import com.magang.salesquotation.helper.DateHelper;
import com.magang.salesquotation.viewmodel.ViewModelFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

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
    @BindView(R.id.tv_information)
    TextView tvInformation;

    @BindView(R.id.edt_name)
    TextInputEditText etName;
    @BindView(R.id.edt_desc)
    TextInputEditText etDesc;
    @BindView(R.id.edt_discount)
    TextInputEditText etDiscount;
    @BindView(R.id.edt_price)
    TextInputEditText etPrice;
    @BindView(R.id.edt_quantity)
    TextInputEditText etQuantity;
    @BindView(R.id.edt_unit)
    AutoCompleteTextView etUnit;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.sw_tax)
    SwitchCompat switchCompat;


    private boolean isEdit = false;
    private String tax = "false";
    private String amount;
    private Sales sales;
    private int position;
    private AddSalesViewModel salesAddViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales_quotation);
        ButterKnife.bind(this);
        salesAddViewModel = obtainViewModel(AddSalesQuotationActivity.this);

        setUnit();
        initTitle();
        setSwitch();
        getData();

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Coba","TITLE : "+ Objects.requireNonNull(etName.getText()).toString());
                calculateAmount();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateAmount();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateAmount();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnSubmit.setOnClickListener(view -> {
            buttonAction();
        });
    }

    @NonNull
    private AddSalesViewModel obtainViewModel(AddSalesQuotationActivity addSalesQuotationActivity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(addSalesQuotationActivity.getApplication());

        return ViewModelProviders.of(addSalesQuotationActivity, factory).get(AddSalesViewModel.class);
    }

    private void setUnit() {
        String[] dataCategory = getResources().getStringArray(R.array.unit);
        ArrayList<String> listData = new ArrayList<>(Arrays.asList(dataCategory));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, listData);

        etUnit.setAdapter(adapter);
    }

    private void getData() {
        sales = getIntent().getParcelableExtra(EXTRA_SALES);
        if (sales != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
            etName.setText(sales.getItemName());
            etDesc.setText(sales.getDescription());
            etDiscount.setText(sales.getDiscount());
            etQuantity.setText(sales.getQuantity());
            etPrice.setText(sales.getUnitPrice());
            etUnit.setText(sales.getUnit());
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            tvAmount.setText(formatRupiah.format(Double.parseDouble(String.valueOf(sales.getAmount()))));
            if (sales.getTax().equals(tax)){
                switchCompat.setChecked(false);
            }else{
                switchCompat.setChecked(true);
            }
        } else {
            sales = new Sales();

        }
    }

    private void initTitle() {
        String actionBarTitle;
        String btnTitle;
        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);

            if (sales != null) {

            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tvInformation.setText(actionBarTitle);
        }

        btnSubmit.setText(btnTitle);
    }

    private void calculateAmount() {
        String sQuantity = Objects.requireNonNull(etQuantity.getText()).toString().trim();
        String sUnitPrice = Objects.requireNonNull(etPrice.getText()).toString().trim();
        String sDiscount = Objects.requireNonNull(etDiscount.getText()).toString().trim();
        double quantity = checkData(sQuantity);
        double unitPrice = checkData(sUnitPrice);
        double discount = checkData(sDiscount);

        double totalDiscount = (discount / 100) * (unitPrice * quantity);
        Log.d("TAG", "calculateAmount: " + totalDiscount);
        double fTotal;
        if (tax.equals("true")) {
            fTotal = (quantity * unitPrice) - totalDiscount + (0.1 * quantity * unitPrice);
        } else {
            fTotal = (quantity * unitPrice) - totalDiscount;
        }

        int total = (int) fTotal;
        amount = String.valueOf(total);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        tvAmount.setText(formatRupiah.format(Double.parseDouble(String.valueOf(total))));
    }

    private double checkData(String data) {
        if (data.equals("")) {
            return 0;
        } else {
            return Double.parseDouble(data);
        }
    }

    private void setSwitch() {
        switchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                tax = "true";
            } else {
                tax = "false";
            }
            calculateAmount();
        });
    }

    private void buttonAction() {
        String title = Objects.requireNonNull(etName.getText()).toString().trim();
        String description = Objects.requireNonNull(etDesc.getText()).toString().trim();
        String quantity = Objects.requireNonNull(etQuantity.getText()).toString().trim();
        String unitPrice = Objects.requireNonNull(etPrice.getText()).toString().trim();
        String discount = String.valueOf(checkData(Objects.requireNonNull(etDiscount.getText()).toString().trim()));
        String unit = Objects.requireNonNull(etUnit.getText()).toString().trim();

        if (title.isEmpty()) {
            etName.setError(getString(R.string.empty));
        } else if (description.isEmpty()) {
            etDesc.setError(getString(R.string.empty));
        } else if (quantity.isEmpty()) {
            etQuantity.setError(getString(R.string.empty));
        } else if (unitPrice.isEmpty()) {
            etPrice.setError(getString(R.string.empty));
        } else if (unit.isEmpty()) {
            etUnit.setError(getString(R.string.empty));
        } else {
            sales.setItemName(title);
            sales.setDescription(description);
            sales.setQuantity(quantity);
            sales.setUnitPrice(unitPrice);
            sales.setDiscount(discount);
            sales.setUnit(unit);
            sales.setAmount(amount);
            sales.setTax(tax);

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
        }

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
                .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                    if (isDialogClose) {
                        finish();
                    } else {
                        salesAddViewModel.delete(sales);

                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_POSITION, position);
                        setResult(RESULT_DELETE, intent);
                        finish();
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