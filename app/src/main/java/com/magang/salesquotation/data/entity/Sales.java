package com.magang.salesquotation.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Entity dan tabel yang akan dibuat pada sqlite
@Entity
public class Sales implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "itemName")
    private String itemName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "quantity")
    private String quantity;

    @ColumnInfo(name = "unit")
    private String unit;

    @ColumnInfo(name = "unitPrice")
    private String unitPrice;

    @ColumnInfo(name = "discount")
    private String discount;

    @ColumnInfo(name = "amount")
    private String amount;

    @ColumnInfo(name = "tax")
    private String tax;

    @ColumnInfo(name = "date")
    private String date;

    public Sales() {
    }

    public Sales(int id, String itemName, String description, String quantity, String unit, String unitPrice, String discount, String amount, String tax, String date) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.quantity = quantity;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.amount = amount;
        this.tax = tax;
        this.date = date;
    }

    protected Sales(Parcel in) {
        id = in.readInt();
        itemName = in.readString();
        description = in.readString();
        quantity = in.readString();
        unit = in.readString();
        unitPrice = in.readString();
        discount = in.readString();
        amount = in.readString();
        tax = in.readString();
        date = in.readString();
    }

    public static final Creator<Sales> CREATOR = new Creator<Sales>() {
        @Override
        public Sales createFromParcel(Parcel in) {
            return new Sales(in);
        }

        @Override
        public Sales[] newArray(int size) {
            return new Sales[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(itemName);
        parcel.writeString(description);
        parcel.writeString(quantity);
        parcel.writeString(unit);
        parcel.writeString(unitPrice);
        parcel.writeString(discount);
        parcel.writeString(amount);
        parcel.writeString(tax);
        parcel.writeString(date);
    }
}
