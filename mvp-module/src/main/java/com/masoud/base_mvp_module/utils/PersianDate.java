package com.masoud.base_mvp_module.utils;

/**
 * Created by Masoud pc on 5/10/2016.
 */

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PersianDate {

    private boolean isFormatSlash;
    public static final String DateFormat = "MM/dd/yyyy hh:mm:ss aa";

    public PersianDate(boolean isFormatSlash) {

        this.isFormatSlash = isFormatSlash;
    }

    public String todayShamsi() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String curentDateandTime = sdf.format(new Date());
        String year = curentDateandTime.substring(0, 4);
        String month = curentDateandTime.substring(4, 6);
        String day = curentDateandTime.substring(6, 8);
        int Y = Integer.valueOf(year);
        int M = Integer.valueOf(month);
        int D = Integer.valueOf(day);
        return Shamsi(Y, M, D);
    }

    public String Shamsi(int Y, int M, int D) {
        if (Y == 0)
            Y = 2000;
        if (Y < 100)
            Y = Y + 1900;
        if (Y == 2000) {
            if (M > 2) {
                SimpleDateFormat temp = new SimpleDateFormat("yyyyMMdd");
                String curentDateandTime = temp.format(new Date());
                String year = curentDateandTime.substring(0, 4);
                String month = curentDateandTime.substring(4, 6);
                String day = curentDateandTime.substring(6, 8);
                Y = Integer.valueOf(year);
                M = Integer.valueOf(month);
                D = Integer.valueOf(day);
            }
        }
        if (M < 3 || (M == 3 && D < 21))
            Y = Y - 622;
        else
            Y = Y - 621;
        switch (M) {
            case 1:
                if (D < 21) {
                    M = 10;
                    D = D + 10;
                } else {
                    M = 11;
                    D = D - 20;
                }
                break;
            case 2:
                if (D < 20) {
                    M = 11;
                    D = D + 11;
                } else {
                    M = 12;
                    D = D - 19;
                }
                break;
            case 3:
                if (D < 21) {
                    M = 12;
                    D = D + 9;
                } else {
                    M = 1;
                    D = D - 20;
                }
                break;
            case 4:
                if (D < 21) {
                    M = 1;
                    D = D + 11;
                } else {
                    M = 2;
                    D = D - 20;
                }
                break;
            case 5:
                if (D < 22) {
                    M = M - 3;
                    D = D + 10;
                } else {
                    M = M - 2;
                    D = D - 21;
                }
                break;
            case 6:
                if (D < 22) {
                    M = M - 3;
                    D = D + 10;
                } else {
                    M = M - 2;
                    D = D - 21;
                }
                break;
            case 7:
                if (D < 23) {
                    M = M - 3;
                    D = D + 9;
                } else {
                    M = M - 2;
                    D = D - 22;
                }
                break;
            case 8:
                if (D < 23) {
                    M = M - 3;
                    D = D + 9;
                } else {
                    M = M - 2;
                    D = D - 22;
                }
                break;
            case 9:
                if (D < 23) {
                    M = M - 3;
                    D = D + 9;
                } else {
                    M = M - 2;
                    D = D - 22;
                }
                break;
            case 10:
                if (D < 23) {
                    M = 7;
                    D = D + 8;
                } else {
                    M = 8;
                    D = D - 22;
                }
                break;
            case 11:
                if (D < 22) {
                    M = M - 3;
                    D = D + 9;
                } else {
                    M = M - 2;
                    D = D - 21;
                }
                break;
            case 12:
                if (D < 22) {
                    M = M - 3;
                    D = D + 9;
                } else {
                    M = M - 2;
                    D = D - 21;
                }
                break;
        }
        String month = "00";
        String day = "00";
        D = Integer.valueOf(D);
        if (M < 10) {
            month = "0" + M;
        } else {
            month = String.valueOf(M);
        }
        if (D < 10) {
            day = "0" + D;
        } else {
            day = String.valueOf(D);
        }
        if (isFormatSlash)
            return String.valueOf(Y) + "/" + month + "/" + day;
        else
            return String.valueOf(Y) + "_" + month + "_" + day;
    }

    public String convertToPersianDate(Date sourceDate , boolean isHour){

        String ret = "";

        if (sourceDate != null) {

            Format formatter = new SimpleDateFormat(DateFormat, Locale.ENGLISH);
            String date = formatter.format(sourceDate);

            String test = date;
            if (date != null && !"".equals(date)) {
                String y = test.substring(6, 10);
                String m = test.substring(0, 2);
                String d = test.substring(3, 5);

                String h = test.substring(11, 13);
                String M = test.substring(14, 16);
                //String s = test.substring(12, 14);
                try {
                    ret = Shamsi(Integer.valueOf(y), Integer.valueOf(m), Integer.valueOf(d)) + "  " + h + ":" + M
                    ;
                } catch (Exception ex) {
                    ret = "";
                }

                if (!isHour)
                    return ret.substring(0,10);
            }
        }
        return ret;
    }
}
