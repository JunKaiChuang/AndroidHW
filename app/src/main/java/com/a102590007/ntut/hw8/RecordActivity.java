package com.a102590007.ntut.hw8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {

    private ListView mLstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mLstView = findViewById(R.id.lstRecord);

        showRecord();
    }

    private void showRecord(){
        Bundle bundle = getIntent().getExtras();


        ArrayList<MealSpending> mealSpendingArrayList = bundle.getParcelableArrayList("Record");

        ArrayAdapter<MealSpending> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mealSpendingArrayList);
        mLstView.setAdapter(adapter);
    }
}
