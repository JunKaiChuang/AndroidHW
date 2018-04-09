package com.paperscissorsstonegame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    private PaperScissorsStone pss;
    // 1 – 剪刀, 2 – 石頭, 3 – 布.
    private int Scissors = 1;
    private int Stone = 2;
    private int Paper = 3;
    @Before
    public void setup() {
        pss = new PaperScissorsStone();
    }

    @Test
    public void checkWinner() throws Exception {
        String result = "輸贏判定：";
        String win = "恭喜，你贏了！";
        String lose = "很可惜，你輸了！";
        String draw = "雙方平手！";

        assertEquals(result + draw, pss.CheckWinner(Scissors, Scissors));
        assertEquals(result + lose, pss.CheckWinner(Stone, Scissors));
        assertEquals(result + win, pss.CheckWinner(Paper, Scissors));

        assertEquals(result + win, pss.CheckWinner(Scissors, Stone));
        assertEquals(result + draw, pss.CheckWinner(Stone, Stone));
        assertEquals(result + lose, pss.CheckWinner(Paper, Stone));

        assertEquals(result + lose, pss.CheckWinner(Scissors, Paper));
        assertEquals(result + win, pss.CheckWinner(Stone, Paper));
        assertEquals(result + draw, pss.CheckWinner(Paper, Paper));
    }

    @Test
    public void getPlayText() throws Exception{
        assertEquals("剪刀", pss.GetPlayText(Scissors));
        assertEquals("石頭", pss.GetPlayText(Stone));
        assertEquals("布", pss.GetPlayText(Paper));
    }
}