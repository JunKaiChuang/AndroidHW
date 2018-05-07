package com.gameusingdynamicfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GameResultActivity extends AppCompatActivity {

    private EditText mEditCountSet,
                    mEditCountPlayerWin,
                    mEditCountComWin,
                    mEditCountDraw;
    private Button mBtnBackToGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        mEditCountSet = findViewById(R.id.editCountSet);
        mEditCountPlayerWin = findViewById(R.id.editCountPlayerWin);
        mEditCountComWin = findViewById(R.id.editCountComWin);
        mEditCountDraw = findViewById(R.id.editCountDraw);
        mBtnBackToGame = findViewById(R.id.btnBackToGame);

        mBtnBackToGame.setOnClickListener(btnBackToGameOnClick);

        showResult();
    }

    private View.OnClickListener btnBackToGameOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void showResult(){
        //從bundle物件取出資料
        Bundle bundle = getIntent().getExtras();

        int iCountSet = bundle.getInt("KEY_COUNT_SET");
        int iCountPlayerWin = bundle.getInt("KEY_COUNT_PLAYER_WIN");
        int iCountComWin = bundle.getInt("KEY_COUNT_COM_WIN");
        int iCountDraw = bundle.getInt("KEY_COUNT_DRAW");

        mEditCountSet.setText(Integer.toString(iCountSet));
        mEditCountPlayerWin.setText(Integer.toString(iCountPlayerWin));
        mEditCountComWin.setText(Integer.toString(iCountComWin));
        mEditCountDraw.setText(Integer.toString(iCountDraw));
    }
}
