package com.zhhome.xunjian.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by PC on 2018/4/2.
 */

public class TimeUtils {
    public static void showDatePickerDialog(Context context, final TextView tv, Calendar calendar) {
        // Calendar 需要这样来得到
        // Calendar calendar = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(context,
                // 绑定监听器(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        int myMonth = monthOfYear + 1;
                        // 此处得到选择的时间，可以进行你想要的操作
                        tv.setText(year + "-" + myMonth
                                + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    /**
     * 获取当前时间的前一天时间
     * @param cl
     * @return
     */
    public static Calendar getBeforeDay(Calendar cl){
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day-1);
        return cl;
    }

    /**
     * 获取当前时间的后一天时间
     * @param cl
     * @return
     */
    public static Calendar getAfterDay(Calendar cl){
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day+1);
        return cl;
    }

    /**
     * 获取当前时间的后一天时间
     * @param cl
     * @return
     */
    public static Calendar getAfterDay30(Calendar cl, int afterday){
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day+afterday);
        return cl;
    }

    /**
     * 打印时间
     * @param cl
     */
    public static String printCalendar(Calendar cl){
        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH)+1;
        int day = cl.get(Calendar.DATE);
        if(month < 10){
            if(day < 10){
                return year+"-" +"0" +month+"-" + "0"+day;
            }else {
                return year+"-" +"0" +month+"-"+day;
            }

        }else {
            if(day < 10){
                return year+"-" +month+"-" + "0"+day;
            }else {
                return year+"-" +month+"-"+day;
            }

        }

    }

    public static String printCalendar2(Calendar cl){
        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH)+1;
        int day = cl.get(Calendar.DATE);
        return month+"月"+day + "日";
    }


    public static String getWeek(Calendar mCalendar){
        String myWeek = null;
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        int month = mCalendar.get(Calendar.MONTH);
        switch (dayOfWeek)
        {
            case 1:
                myWeek = " 星期日";
                break;
            case 2:
                myWeek = " 星期一";
                break;
            case 3:
                myWeek = " 星期二";
                break;
            case 4:
                myWeek = " 星期三";
                break;
            case 5:
                myWeek = " 星期四";
                break;
            case 6:
                myWeek = " 星期五";
                break;
            case 7:
                myWeek = " 星期六";
                break;
        }
        return (month + 1) +  "-" + dayOfMonth + myWeek;

    }




    public static String showDatePickerDialog(Context context, int themeResId, final TextView tv, Calendar calendar) {
        final String[] time = new String[1];
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        DatePickerDialog datePickerDialog = new DatePickerDialog(context
                ,  themeResId
                // 绑定监听器(How the parent is notified that the date is set.)
                ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                int myMonth = monthOfYear + 1;
                tv.setText(year + "-" + myMonth
                        + "-" + dayOfMonth);


                String myWeek = null;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);//先指定年份
                calendar.set(Calendar.MONTH, monthOfYear);//再指定月份 Java月份从0开始算
                //获取指定年份月份中指定某天是星期几
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                switch (dayOfWeek)
                {
                    case 1:
                        myWeek = " 星期日";
                        break;
                    case 2:
                        myWeek = " 星期一";
                        break;
                    case 3:
                        myWeek = " 星期二";
                        break;
                    case 4:
                        myWeek = " 星期三";
                        break;
                    case 5:
                        myWeek = " 星期四";
                        break;
                    case 6:
                        myWeek = " 星期五";
                        break;
                    case 7:
                        myWeek = " 星期六";
                        break;
                }


                // 此处得到选择的时间，可以进行你想要的操作
                time[0] = myMonth
                        + "-" + dayOfMonth  + myWeek;
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH));

        //设置起始日期和结束日期
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(System.currentTimeMillis());
        //datePicker.setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
        return time[0];
    }



    /**
     * 获取 WebView 视图截图
     * @param context
     * @param view
     * @return
     */
    public static Bitmap getWebViewBitmap2(Context context, WebView view) {
        if (null != view){
            view.scrollTo(0, 0);
            view.buildDrawingCache(true);
            view.setDrawingCacheEnabled(true);
            view.setVerticalScrollBarEnabled(false);
            Bitmap b = getViewBitmapWithoutBottom(view);
            // 可见高度
            int vh = view.getHeight();
            // 容器内容实际高度
            int th = (int)(view.getContentHeight()*view.getScale());
            Bitmap temp = null;
            if (th > vh) {
                int w = getScreenWidth(context);
                int absVh = vh - view.getPaddingTop() - view.getPaddingBottom();
                do {
                    int restHeight = th - vh;
                    if (restHeight <= absVh) {
                        view.scrollBy(0, restHeight);
                        vh += restHeight;
                        temp = getViewBitmap(view);
                    } else {
                        view.scrollBy(0, absVh);
                        vh += absVh;
                        temp = getViewBitmapWithoutBottom(view);
                    }
                    b = mergeBitmap(vh, w, temp, 0, view.getScrollY(), b, 0, 0);
                } while (vh < th);
            }
            // 回滚到顶部
            view.scrollTo(0, 0);
            view.setVerticalScrollBarEnabled(true);
            view.setDrawingCacheEnabled(false);
            view.destroyDrawingCache();
            return b;
        }
        return null;
    }

    private static Bitmap getViewBitmapWithoutBottom(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }


        Bitmap bp = null;
        //这里容易异常，捕获一下(参考API createBitmap)
        try {
            bp = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight() - v.getPaddingBottom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }

    private static Bitmap mergeBitmap(int newImageH, int newImageW, Bitmap background, float backX, float backY, Bitmap foreground, float foreX, float foreY) {
        if (null == background || null == foreground) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(newImageW, newImageH, Bitmap.Config.RGB_565);
        Canvas cv = new Canvas(bitmap);
        cv.drawBitmap(background, backX, backY, null);
        cv.drawBitmap(foreground, foreX, foreY, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return bitmap;
    }

    public static Bitmap getViewBitmap(View v) {
        if (null == v) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(), (int) v.getX() + v.getMeasuredWidth(), (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        }
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return b;
    }

    /**
     * get the width of screen
     */
    public static int getScreenWidth(Context ctx) {
        int w = 0;
        if (Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(p);
            w = p.x;
        } else {
            w = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        }
        return w;
    }

}
