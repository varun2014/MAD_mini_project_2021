package com.android.udacity_foodquest.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

public class AppWidgetHelper {

    public static void updateAppWidget(Activity activity) {
        Intent intent = new Intent(activity, FoodQuestAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(activity).getAppWidgetIds(new ComponentName(activity, FoodQuestAppWidgetProvider.class));

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        activity.sendBroadcast(intent);
    }
}
