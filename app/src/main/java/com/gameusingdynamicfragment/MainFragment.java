package com.gameusingdynamicfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainFragment extends Fragment {

    public enum GameResultType {
        TYPE_1, TYPE_2, HIDE
    }

    // 所屬的 Activity 必須實作以下介面中的callback方法
    public interface CallbackInterface {
        public void updateGameResult(int iCountSet,
                                     int iCountPlayerWin,
                                     int iCountComWin,
                                     int iCountDraw);
        public void enableGameResult(GameResultType type);
    };

    private CallbackInterface mCallback;

    private ImageButton mImgBtnScissors,
                        mImgBtnStone,
                        mImgBtnPaper;
    private ImageView mImgViewComPlay;
    private TextView mTxtResult;
/*
    public EditText mEdtCountSet,
                    mEdtCountPlayerWin,
                    mEdtCountComWin,
                    mEdtCountDraw;
*/

    // 新增統計遊戲局數和輸贏的變數
    private int miCountSet = 0,
                miCountPlayerWin = 0,
                miCountComWin = 0,
                miCountDraw = 0;

    private Button mBtnShowResult,
                    mBtnShowResult2,
                    mBtnHiddenResult,
                    mRollDice;

    private boolean mbShowResult = false;

//    private final static String TAG = "Result";
//    private int mTagCount = 0;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (CallbackInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    "must implement GameFragment.CallbackInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 利用inflater物件的inflate()方法取得介面佈局檔，並將最後的結果傳回給系統
       return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 必須先呼叫getView()取得程式畫面物件，然後才能呼叫它的
        // findViewById()取得介面物件
        mTxtResult = (TextView) getView().findViewById(R.id.txtResult);
        //mImgBtnScissors = (ImageButton) getView().findViewById(R.id.imgBtnScissors);
        //mImgBtnStone = (ImageButton) getView().findViewById(R.id.imgBtnStone);
        //mImgBtnPaper = (ImageButton) getView().findViewById(R.id.imgBtnPaper);
        mImgViewComPlay = (ImageView) getView().findViewById(R.id.imgViewComPlay);

        // 以下介面元件是在另一個Fragment中，因此必須呼叫所屬的Activity
        // 才能取得這些介面元件
/*
        mEdtCountSet = (EditText) getActivity().findViewById(R.id.edtCountSet);
        mEdtCountPlayerWin = (EditText) getActivity().findViewById(R.id.edtCountPlayerWin);
        mEdtCountComWin = (EditText) getActivity().findViewById(R.id.edtCountComWin);
        mEdtCountDraw = (EditText) getActivity().findViewById(R.id.edtCountDraw);
*/

        //mImgBtnScissors.setOnClickListener(imgBtnScissorsOnClick);
        //mImgBtnStone.setOnClickListener(imgBtnStoneOnClick);
        //mImgBtnPaper.setOnClickListener(imgBtnPaperOnClick);
        mImgViewComPlay.setOnClickListener(imgBtnComPlay);

        mBtnShowResult = (Button) getView().findViewById(R.id.btnShowResult);
        //mBtnShowResult2 = (Button) getView().findViewById(R.id.btnShowResult2);
        //mBtnHiddenResult = (Button) getView().findViewById(R.id.btnHiddenResult);
        mRollDice = getView().findViewById(R.id.btnRollDice);

        mBtnShowResult.setOnClickListener(btnShowResultOnClick);
        //mBtnShowResult2.setOnClickListener(btnShowResult2OnClick);
        //mBtnHiddenResult.setOnClickListener(btnHiddenResultOnClick);
        mRollDice.setOnClickListener(btnRollDiceOnClick);
    }

    // Use static Handler to avoid memory leaks.
    private class StaticHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public StaticHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            //if (activity == null) return;

            int iRand = (int)(Math.random()*6 + 1);
            miCountSet++;
            switch (iRand) {
                case 1:
                    mImgViewComPlay.setImageResource(R.drawable.dice01);
                    mTxtResult.setText(R.string.player_win);
                    miCountPlayerWin++;
                    break;
                case 2:
                    mImgViewComPlay.setImageResource(R.drawable.dice02);
                    mTxtResult.setText(R.string.player_win);
                    miCountPlayerWin++;
                    break;
                case 3:
                    mImgViewComPlay.setImageResource(R.drawable.dice03);
                    mTxtResult.setText(R.string.player_draw);
                    miCountDraw++;
                    break;
                case 4:
                    mImgViewComPlay.setImageResource(R.drawable.dice04);
                    mTxtResult.setText(R.string.player_draw);
                    miCountDraw++;
                    break;
                case 5:
                    mImgViewComPlay.setImageResource(R.drawable.dice05);
                    mTxtResult.setText(R.string.player_lose);
                    miCountComWin++;
                    break;
                case 6:
                    mImgViewComPlay.setImageResource(R.drawable.dice06);
                    mTxtResult.setText(R.string.player_lose);
                    miCountComWin++;
                    break;
            }
            mCallback.updateGameResult(miCountSet, miCountPlayerWin,
                    miCountComWin, miCountDraw);
        }
    }

    public final StaticHandler mHandler = new StaticHandler((MainActivity)getActivity());

    private View.OnClickListener imgBtnComPlay = new View.OnClickListener(){
        public void onClick(View v) {
            RollTheDice();
        }
    };

    private void RollTheDice(){
        // 從程式資源中取得動畫檔，設定給ImageView物件，然後開始播放。
        Resources res = getResources();
        final AnimationDrawable animDraw =
                (AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
        mImgViewComPlay.setImageDrawable(animDraw);
        animDraw.start();

        // 啟動background thread進行計時。
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                animDraw.stop();
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        }).start();
    }

    private View.OnClickListener btnRollDiceOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            RollTheDice();
        }
    };

    private View.OnClickListener btnShowResultOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            Intent it = new Intent();
            it.setClass(getActivity(), GameResultActivity.class);

            Bundle bundle = new Bundle();
            bundle.putInt("KEY_COUNT_SET", miCountSet);
            bundle.putInt("KEY_COUNT_PLAYER_WIN", miCountPlayerWin);
            bundle.putInt("KEY_COUNT_COM_WIN", miCountComWin);
            bundle.putInt("KEY_COUNT_DRAW", miCountDraw);

            it.putExtras(bundle);

            startActivity(it);
        }
    };

    /*
    private View.OnClickListener btnHiddenResultOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            mCallback.enableGameResult(GameResultType.HIDE);
        }
    };*/

}
