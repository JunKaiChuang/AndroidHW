package com.paperscissorsstonegame;

/**
 * Created by Junkai on 2018/4/9.
 */

public class PaperScissorsStone {
    public String CheckWinner(int comPlay, int playerPlay){
        String result = "輸贏判定：";
        String win = "恭喜，你贏了！";
        String lose = "很可惜，你輸了！";
        String draw = "雙方平手！";
        // 1 – 剪刀, 2 – 石頭, 3 – 布.
        switch (playerPlay){
            case 1:
                if(comPlay == 1) return result + draw;
                else if(comPlay == 2) return  result + lose;
                else return  result + win;
            case 2:
                if(comPlay == 1) return  result + win;
                else if(comPlay == 2) return  result + draw;
                else return  result + lose;
            default:
                if(comPlay == 1 ) return  result + lose;
                else if(comPlay == 2) return  result + win;
                else return  result + draw;
        }
    }

    public String GetPlayText(int play){
        switch (play){
            case 1:
                return "剪刀";
            case 2:
                return "石頭";
            default:
                return "布";
        }
    }

    public int ComPlay(){
        // 決定電腦出拳.
        int iComPlay = (int)(Math.random()*3 + 1);
        return iComPlay;
    }
}
