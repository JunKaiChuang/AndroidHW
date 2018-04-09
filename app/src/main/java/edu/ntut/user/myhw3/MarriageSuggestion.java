package edu.ntut.user.myhw3;
/**
 * Created by user on 2018/3/20.
 */

public class MarriageSuggestion{
    public String getSuggestion(int iAgeRange) {

        String strSug = "建議：";


        switch (iAgeRange) {
            case 1:

                    strSug += "還不急";
                break;
            case 2:

                    strSug += "開始找對象";
                break;
            case 3:

                    strSug += "趕快結婚";
                break;
        }

        return strSug;
    }
}
