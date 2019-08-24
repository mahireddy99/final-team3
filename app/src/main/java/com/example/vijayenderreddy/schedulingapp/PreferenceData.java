package com.example.vijayenderreddy.schedulingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceData {

    static final String UserID = "";

    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoggedInUserEmail(Context ctx, String email)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(UserID, email);
        editor.commit();
    }

    public static String getLoggedInEmailUser(Context ctx)
    {
        return getSharedPreferences(ctx).getString(UserID,"");
    }

    public static void clearLoggedInEmailAddress(Context ctx)
    {
        Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(UserID);
        editor.commit();
    }
}
