package com.a102590007.ntut.hw8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner mSpinMeal;
    private DatePicker mDatePicker;
    private EditText mETxtDate, mETxtAmount;
    private String strDate;
    private Button mBtnEnter, mBtnRecord;
    private List<MealSpending> mealSpendingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set object by id.
        mSpinMeal = findViewById(R.id.spinMeal);
        mDatePicker = findViewById(R.id.dpSpinner);
        mETxtDate = findViewById(R.id.eTxtDate);
        mETxtAmount = findViewById(R.id.eTxtAmount);
        mBtnEnter = findViewById(R.id.btnEnter);
        mBtnRecord = findViewById(R.id.btnRecord);

        //Set ArrayAdapter to spinner.
        ArrayAdapter<CharSequence> mealList = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.mealList,
                android.R.layout.simple_spinner_dropdown_item);
        mSpinMeal.setAdapter(mealList);

        //Initialize the DatePicker & EditText
        Calendar today = Calendar.getInstance();
        strDate = getCurrentDate();
        mETxtDate.setText(strDate);
        mDatePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        strDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth; //plus one because 'month' will return 0 to 11

                        mETxtDate.setText(strDate);
                    }
                }
        );

        //Set object's event listener.
    }

    private String getCurrentDate(){
        Calendar today = Calendar.getInstance();

        return today.get(Calendar.YEAR) + "/" + (today.get(Calendar.MONTH) + 1) + "/" + today.get(Calendar.DAY_OF_MONTH);
    }

    public void enterData(View view) {
        String itemName, date, meal, amount;
        itemName = "項目" + mealSpendingList.size();
        date = strDate;
        meal = mSpinMeal.getSelectedItem().toString();
        amount = mETxtAmount.getText().toString();

        MealSpending mealSpending = new MealSpending(itemName, date, meal, amount);
        mealSpendingList.add(mealSpending);

        Toast.makeText(MainActivity.this, amount, Toast.LENGTH_SHORT).show();

        clearInput();
    }

    public void clearInput() {
        mETxtAmount.setText("");
    }

    public void viewRecord(View view) {
        Intent intent = new Intent(MainActivity.this, RecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Record" , (ArrayList<MealSpending>) mealSpendingList);
        intent.putExtras(bundle);
        startActivity(intent);

        Toast.makeText(MainActivity.this, "紀錄顯示", Toast.LENGTH_SHORT).show();
    }
}
