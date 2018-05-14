package com.a102590007.ntut.hw8;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Junkai on 2018/5/14.
 */

public class MealSpending  implements Parcelable {
    private String strItemName;
    private String strDate;
    private String strMeal;
    private String strAmount;

    public MealSpending(String itemName, String date, String meal, String amount) {
        this.strItemName = itemName;
        this.strDate = date;
        this.strMeal = meal;
        this.strAmount = amount;
    }

    public MealSpending(Parcel parcel){
        this.strItemName = parcel.readString();
        this.strDate  = parcel.readString();
        this.strMeal = parcel.readString();
        this.strAmount = parcel.readString();
    }

    public static final Creator<MealSpending> CREATOR = new Creator<MealSpending>() {
        @Override
        public MealSpending createFromParcel(Parcel in) {
            return new MealSpending(in);
        }

        @Override
        public MealSpending[] newArray(int size) {
            return new MealSpending[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strItemName);
        dest.writeString(strDate);
        dest.writeString(strMeal);
        dest.writeString(strAmount);
    }

    @Override
    public String toString(){
        return this.strItemName + "  " + this.strDate + "  " + this.strMeal + "  " + this.strAmount;
    }


    /* Get */
    public String GetItemName(){
        return strItemName;
    }

    public String GetDate(){
        return strDate;
    }

    public String GetMeal(){
        return strMeal;
    }

    public String GetAmount(){
        return  strAmount;
    }
}
