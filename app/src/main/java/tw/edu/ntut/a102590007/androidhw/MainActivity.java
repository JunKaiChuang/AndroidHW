package tw.edu.ntut.a102590007.androidhw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //宣告物件用以對應介面上的元件
    EditText mEditSex, mEditAge;
    TextView mTxtR;
    Button mBtnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //利用findViewById()取得介面元件，並設定給程式宣告的物件
        mEditSex = findViewById(R.id.editSex);
        mEditAge = findViewById(R.id.editAge);
        mTxtR = findViewById(R.id.txtR);
        mBtnOK = findViewById(R.id.btnOK);

        //設定mBtnOK的OnClickListener
        mBtnOK.setOnClickListener(btnOKOnClick);
    }

    private View.OnClickListener btnOKOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String strSex = mEditSex.getText().toString();
            int iAge = Integer.parseInt(mEditAge.getText().toString());

            String strSug = getString(R.string.suggestion);
            if (strSex.equals(getString(R.string.sex_male)))
                if (iAge < 30)
                    strSug += getString(R.string.sug_not_hurry);
                else if (iAge < 36)
                    strSug += getString(R.string.sug_get_married);
                else
                    strSug += getString(R.string.sug_find_couple);
            else
                if (iAge < 28)
                    strSug += getString(R.string.sug_not_hurry);
                else if (iAge < 33)
                    strSug += getString(R.string.sug_get_married);
                else
                    strSug += getString(R.string.sug_find_couple);


            //輸出至textView
            mTxtR.setText(strSug);
        }
    };
}
