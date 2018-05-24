package com.a102590007.ntut.hw8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private Spinner mSpinMeal;
    private DatePicker mDatePicker;
    private EditText mETxtDate, mETxtAmount;
    private String strDate;
    private Button mBtnEnter, mBtnRecord;
    private List<MealSpending> mealSpendingList = new ArrayList<>();

    private static final int MENU_MUSIC = Menu.FIRST,
                            MENU_PLAY_MUSIC = Menu.FIRST + 1,
                            MENU_STOP_PLAYING_MUSIC = Menu.FIRST + 2,
                            MENU_ABOUT = Menu.FIRST + 3,
                            MENU_EXIT = Menu.FIRST + 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set object by id.
        mRelativeLayout = findViewById(R.id.relativeLayout);
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

        //Register
        registerForContextMenu(mRelativeLayout);
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

    private void CreateMenu(Menu menu){
        SubMenu subMenu = menu.addSubMenu(0, MENU_MUSIC, 0, "背景音樂")
                .setIcon(android.R.drawable.ic_media_ff);
        subMenu.add(0, MENU_PLAY_MUSIC, 0, "播放背景音樂");
        subMenu.add(0, MENU_STOP_PLAYING_MUSIC, 0 , "停止播放背景音樂");
        menu.add(0, MENU_ABOUT, 1, "關於這個程式")
                .setIcon(android.R.drawable.ic_dialog_info);
        menu.add(0 ,MENU_EXIT, 2, "結束")
                .setIcon(android.R.drawable.ic_menu_close_clear_cancel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_PLAY_MUSIC:
                Intent it = new Intent(MainActivity.this, MediaPlayService.class);
                startService(it);
                return true;
            case MENU_STOP_PLAYING_MUSIC:
                it = new Intent(MainActivity.this, MediaPlayService.class);
                stopService(it);
                return true;
            case MENU_ABOUT:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("關於這個程式")
                        .setMessage("選單範例程式")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.star_big_on)
                        .setPositiveButton("確定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                return true;
            case MENU_EXIT:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        CreateMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        onOptionsItemSelected(item);
        return super.onContextItemSelected(item);
    }
}
