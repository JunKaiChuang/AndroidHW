package com.example.ntut.hw10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String DB_FILE = "contacts.db",
                            DB_TABLE = "contacts";
    private SQLiteDatabase mContactDb;

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set DB
        ContactDbOpenHelper contactDbOpenHelper =
                    new ContactDbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
        mContactDb = contactDbOpenHelper.getWritableDatabase();

        //檢查資料表是否存在，若無則新增一個
        Cursor cursor = mContactDb.rawQuery("" +
                "select distinct tbl_name from sqlite_master " +
                "where tbl_name = '" + DB_TABLE + "'"
                , null);
        if(cursor != null){
            if(cursor.getCount() == 0) //沒有資料表，要建立一個新的
                mContactDb.execSQL("create table " + DB_TABLE + "(" +
                "_id integer primary key," +
                "name text not null," +
                "phone text not null," +
                "phoneType text not null);");
            cursor.close();
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //設定ViewPager 和 Adapter
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //設定ViewPager給TabLayout，就會顯示tab pages。
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);

        //斷開魂結
        mContactDb.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            //根據目前位置回傳對應fragment
            switch (position){
                case 0:
                    fragment = new AddNewContact();
                    break;
                case 1:
                    fragment = new SearchContact();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Add New Contact";
                case 1:
                    return "Search Contact";
                default:
                        return null;
            }
        }


    }
}
