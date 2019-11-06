package com.masoud.base_mvp_module.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.masoud.base_mvp_module.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseUtils {

    private Context context;
    private PersianDate persianDate;

    private static BaseUtils instance;

    private BaseUtils(Context context) {

        this.context = context;
        persianDate = new PersianDate(true);
    }

    public static BaseUtils getInstance(Context context) {

        if (instance == null)
            instance = new BaseUtils(context);
        return instance;
    }


    public void log(String nameClass, String methodName, String error) {

        Logger.d(nameClass + "==" + methodName, error);

    }

    public void log(String nameClass, String methodName, Exception error) {

        Logger.d(nameClass + "==" + methodName, error.toString());
    }

    public String getMethodName() {

        try {

            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            return stackTraceElements[3].getMethodName();

        } catch (Exception e) {

            log(getClass().getSimpleName(), "getMethodName", e.toString());
        }

        return "getMethodName";
    }

    public int getScreenSize(boolean isWidth) {

        int width = 0;
        int height = 0;

        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            final Point point = new Point();
            try {
                display.getSize(point);

                if (isTablet(context)) {
//                    width = (int) (point.x * StaticValues.RateScreenWidthTablet);
//                    height = point.y;

                    width = point.x;
                    height = point.y;

                } else {
                    width = point.x;
                    height = point.y;
                }
            } catch (NoSuchMethodError ignore) { // Older device

                if (isTablet(context)) {

//                    width = point.x = (int) (display.getWidth() * StaticValues.RateScreenWidthTablet);
//                    height = point.y = display.getHeight();

                    width = point.x = display.getWidth();
                    height = point.y = display.getHeight();


                } else {
                    width = point.x = display.getWidth();
                    height = point.y = display.getHeight();
                }

            }
        }
        if (isWidth)
            return width;
        else
            return height;

    }

    public boolean isTablet(Context context) {

        boolean istablet = false;

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            istablet = true;
        } else {
            istablet = false;
        }

        return istablet;
    }

    public int getSizeContentFrame(boolean isWidth) {

        int height = 0;
        int width = 0;

        if (context != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            final Point point = new Point();
            try {
                display.getSize(point);

                width = point.x;
                height = point.y;

            } catch (NoSuchMethodError ignore) { // Older device

                width = point.x = display.getWidth();
                height = point.y = display.getHeight();

            }
        }

        if (isWidth)
            return width;
        else return height;

    }

    public String generateUniqueID() {

        return UUID.randomUUID().toString();

    }

    public void copyToClipboard(String value) {

        ClipboardManager clipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData cData = ClipData.newPlainText("text", value);
        clipMan.setPrimaryClip(cData);

    }

    public static Bitmap circleBitmap(ImageView imageView, Bitmap bitmap) {

        Bitmap output = null;
        Canvas canvas = null;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (output != null)
            canvas = new Canvas(output);

        if (canvas != null) {

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }

        if (imageView != null)
            imageView.setImageBitmap(output);

        return output;
    }

    public byte[] byteArray(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        return b;


    }

    public boolean isValidUsername(String str) {


        if (str.equals("") || str.startsWith("0") || str.startsWith("1") || str.startsWith("2") || str.startsWith("3") || str.startsWith("4")
                || str.startsWith("5") || str.startsWith("6") || str.startsWith("7") || str.startsWith("8") || str.startsWith("9"))
            return false;

        else {

            String expression = "^[a-z_A-Z0-9]*$";
            CharSequence inputStr = str;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);

            boolean isValid = matcher.matches();


            return isValid;
        }
    }

    public float roundFloatNumber(float value) {


        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat(".00", otherSymbols);
        df.setRoundingMode(RoundingMode.DOWN); // N
        String s = df.format(value);

        return Float.valueOf(s);

    }

    public void startCall(String phoneNumber) {

        try {

            String uri = "tel:" + phoneNumber.trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

        } catch (Exception e) {

        }


    }

    public String getFileSize(long size) {

        String hrSize = "";

        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }

    public void animation_outAnimation(View view) {

        Animation animation = AnimationUtils.makeOutAnimation(context, false);
        view.startAnimation(animation);
    }

    public String unitCount(long value) {

        if (value > 0) {

            if (value < 1000)
                return String.valueOf(value);
            else if (value <= 999999) {
                int k = (int) (value / 1000);
                return k + " K";
            } else {
                int M = (int) (value / 1000000);
                return M + " M";

            }

        } else
            return "";
    }

    public String convertToPersianDate(long date, boolean isHour) {
        return persianDate.convertToPersianDate(new Date(date), isHour);
    }

    public String formatNumber(String str) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }

    public String formatClock(int hour, int minute) {

        return String.format(Locale.ENGLISH, "%02d:%02d", hour, minute);
    }

    public byte[] readByteFromFile(File file) {

        byte[] image = null;

        try {

            FileInputStream fileInputStream = new FileInputStream(file);
            image = new byte[fileInputStream.available()];
            fileInputStream.read(image, 0, image.length);
            fileInputStream.close();

        } catch (Exception e) {
            log(getClass().getSimpleName(), getMethodName(), e);
        }

        return image;
    }

    public String httpUrl(String address, boolean isExistProtocol) {

        if (isExistProtocol) {

            if (!address.startsWith("http://") || !address.startsWith("https://")) {

                address = "https://" + address;

            }

        } else {

            if (address.startsWith("http://") || address.startsWith("https://")) {

                address = address.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");

            }

        }
        return address;
    }

    public void share_intent(Context context, String link, String title) {

        String url = httpUrl(link, false);
        String shareBody = title + "\n" + "\n" + url;

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        try {

            if (sharingIntent.resolveActivity(context.getPackageManager()) != null)
                context.startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"));


        } catch (Exception e) {

            log(getClass().getSimpleName(), getMethodName(), e);

        }
    }


}
