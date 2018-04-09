package edu.ntut.user.myhw3;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import static android.app.PendingIntent.getActivity;
import static edu.ntut.user.myhw3.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadGrp;
    private RadioButton mRadBtnSex_Male;
    private RadioButton mRadBtnSex_Female;
    private TextView mTxtNumFamily;
    private Button mBtnOK;
    private TextView mTxtSug, mTxtHob;
    private Spinner mSpnAgeRange;

    private String selectedSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        mRadGrp = (RadioGroup) findViewById(R.id.radGrpSex);
        mRadBtnSex_Male = (RadioButton) findViewById(R.id.radBtnSex_Male);
        mRadBtnSex_Female = (RadioButton) findViewById(R.id.radBtnSex_Female);
        mBtnOK = (Button) findViewById(R.id.btnOK);
        mTxtSug = (TextView) findViewById(R.id.txtSug);
        mTxtHob = (TextView) findViewById(R.id.txtHobbies);
        mSpnAgeRange = (Spinner) findViewById(R.id.spnAgeRange);

        mBtnOK.setOnClickListener(btnOKOnClick);
        mRadGrp.setOnCheckedChangeListener(radGrpSexOnCheckedChange);

        mRadGrp.check(R.id.radBtnSex_Male);
    }

    private RadioGroup.OnCheckedChangeListener radGrpSexOnCheckedChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            String[] ageRange;
            if(i == R.id.radBtnSex_Male){
                ageRange = getResources().getStringArray(R.array.maleAgeRange);
            }
            else {
                ageRange = getResources().getStringArray(R.array.femaleAgeRange);
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, ageRange);
            mSpnAgeRange.setAdapter(adapter);
        }
    };


    private View.OnClickListener btnOKOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            MarriageSuggestion ms = new MarriageSuggestion();
            String hobbies = GetAllHobbies();
            int iAgeRange = 1;

            switch (mSpnAgeRange.getSelectedItemPosition()){
                case 0:
                    iAgeRange = 1;
                    break;
                case 1:
                    iAgeRange = 2;
                    break;
                case 2:
                    iAgeRange = 3;
                    break;
            }

            mTxtSug.setText(ms.getSuggestion(iAgeRange));
            mTxtHob.setText(hobbies);
        }
    };

    private String GetAllHobbies(){
        CheckBox checkbox1 = findViewById(R.id.checkBox1);
        CheckBox checkbox2 = findViewById(R.id.checkBox2);
        CheckBox checkbox3 = findViewById(R.id.checkBox3);
        CheckBox checkbox4 = findViewById(R.id.checkBox4);
        CheckBox checkbox5 = findViewById(R.id.checkBox5);
        CheckBox checkbox6 = findViewById(R.id.checkBox6);
        CheckBox checkbox7 = findViewById(R.id.checkBox7);
        CheckBox checkbox8 = findViewById(R.id.checkBox8);
        CheckBox checkbox9 = findViewById(R.id.checkBox9);
        CheckBox checkbox10 = findViewById(R.id.checkBox10);
        String hobbies = "興趣：";

        if(checkbox1.isChecked()) hobbies += " " + checkbox1.getText().toString();
        if(checkbox2.isChecked()) hobbies += " " + checkbox2.getText().toString();
        if(checkbox3.isChecked()) hobbies += " " + checkbox3.getText().toString();
        if(checkbox4.isChecked()) hobbies += " " + checkbox4.getText().toString();
        if(checkbox5.isChecked()) hobbies += " " + checkbox5.getText().toString();
        if(checkbox6.isChecked()) hobbies += " " + checkbox6.getText().toString();
        if(checkbox7.isChecked()) hobbies += " " + checkbox7.getText().toString();
        if(checkbox8.isChecked()) hobbies += " " + checkbox8.getText().toString();
        if(checkbox9.isChecked()) hobbies += " " + checkbox9.getText().toString();
        if(checkbox10.isChecked()) hobbies += " " + checkbox10.getText().toString();

        return hobbies;
    }
}
