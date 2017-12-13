package com.example.eclass.Tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enjoy life on 2017/9/24.
 */
/*
   活动管理器
 */

public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }
}
