package com.xbk1jk.zldkbk.zhulihuavrsdtrutil;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityZhuLiDaiKuanHuadewgCollector {

    private static List<Activity> activityList = new ArrayList<>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activityList){
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }

}
