package com.livich.privatebin;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

public class Application extends android.app.Application {
    public static final String TAG = "PrivateBin";
    private static Application instance;
    private static Context context;

    public Application() {
        super();
        Application.instance = this;
    }


    public static Application getInstance() {
        return instance;
    }

    public static Context context() {
        if (Application.context == null) {
            Application.context = Application.getInstance().getApplicationContext();
        }
        return Application.context;
    }

    public static ClipboardManager getClipboardManager() {
        return (ClipboardManager) Application.instance.getSystemService(CLIPBOARD_SERVICE);
    }

    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(Application.context());
    }
}