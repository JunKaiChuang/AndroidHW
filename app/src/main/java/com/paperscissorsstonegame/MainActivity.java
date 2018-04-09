package com.paperscissorsstonegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtComPlay, mTxtResult;
    private Button mBtnScissors, mBtnStone, mBtnPaper;
    private PaperScissorsStone pss;

    // 1 – 剪刀, 2 – 石頭, 3 – 布.
    private int Scissors = 1;
    private int Stone = 2;
    private int Paper = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtComPlay = (TextView)findViewById(R.id.txtComPlay);
        mTxtResult = (TextView)findViewById(R.id.txtResult);
        mBtnScissors = (Button)findViewById(R.id.btnScissors);
        mBtnStone = (Button)findViewById(R.id.btnStone);
        mBtnPaper = (Button)findViewById(R.id.btnPaper);

        mBtnScissors.setOnClickListener(btnScissorsOnClick);
        mBtnStone.setOnClickListener(btnStoneOnClick);
        mBtnPaper.setOnClickListener(btnPaperOnClick);

        pss = new PaperScissorsStone();
    }

    private View.OnClickListener btnScissorsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            // 決定電腦出拳.
            int iComPlay = pss.ComPlay();
            mTxtComPlay.setText(pss.GetPlayText(iComPlay));

            //判斷輸贏
            mTxtResult.setText(pss.CheckWinner(iComPlay, Scissors));
        }
    };

    private View.OnClickListener btnStoneOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            // 決定電腦出拳.
            int iComPlay = pss.ComPlay();
            mTxtComPlay.setText(pss.GetPlayText(iComPlay));

            //判斷輸贏
            mTxtResult.setText(pss.CheckWinner(iComPlay, Stone));
        }
    };

    private View.OnClickListener btnPaperOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            // 決定電腦出拳.
            int iComPlay = pss.ComPlay();
            mTxtComPlay.setText(pss.GetPlayText(iComPlay));

            //判斷輸贏
            mTxtResult.setText(pss.CheckWinner(iComPlay, Paper));
        }
    };
}
