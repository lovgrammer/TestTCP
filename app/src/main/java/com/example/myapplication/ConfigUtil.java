package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigUtil {

    public static final String KEY_SESSION_PREFERENCE = "keys2";
    public static final String keys2Interval = "keys2Interval";
    public static final String keys2Hostname = "keys2Hostname";
    public static final String keys2SaveName = "keys2SaveName";    
    public static final String keys2Interface = "keys2Interface";    
    public static final String keys2Port = "keys2Port";
    public static final String keys2Timeout = "keys2Timeout";
    public static final String keys2Skip = "keys2Skip";

    public static int getInterval(Context context) {
        return getIntegerValue(context, keys2Interval);
    }

    public static int getPort(Context context) {
        return getIntegerValue(context, keys2Port);
    }

    public static String getSaveName(Context context) {
        return getStringValue(context, keys2SaveName);
    }

    public static String getHostname(Context context) {
        return getStringValue(context, keys2Hostname);
    }    
    
    public static String getInterface(Context context) {
        return getStringValue(context, keys2Interface);
    }    

    public static int getTimeout(Context context) {
        return getIntegerValue(context, keys2Timeout);
    }

    public static int getSkip(Context context) {
        return getIntegerValue(context, keys2Skip);
    }

    public static void setInterval(Context context, int interval) {
        saveIntegerValue(context, keys2Interval, interval);
    }

    public static void setPort(Context context, int port) {
        saveIntegerValue(context, keys2Port, port);
    }

    public static void setHostname(Context context, String hostname) {
        saveStringValue(context, keys2Hostname, hostname);
    }
    
    public static void setSaveName(Context context, String saveName) {
        saveStringValue(context, keys2SaveName, saveName);
    }
        
    public static void setInterface(Context context, String interfaceName) {
        saveStringValue(context, keys2Interface, interfaceName);
    }    

    public static void setTimeout(Context context, int timeout) {
        saveIntegerValue(context, keys2Timeout, timeout);
    }

    public static void setSkip(Context context, int skip) {
        saveIntegerValue(context, keys2Skip, skip);
    }

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(KEY_SESSION_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static int getIntegerValue(Context context, String name) {
        return getPrefs(context).getInt(name, 0);
    }

    public static void saveIntegerValue(Context context, String name, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static String getStringValue(Context context, String name) {
        return getPrefs(context).getString(name, "");
    }

    public static void saveStringValue(Context context, String name, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(name, value);
        editor.commit();
    }
}
