package com.livich.privatebin.model;

import android.util.Base64;

import com.livich.privatebin.Application;
import com.livich.privatebin.SettingsActivity;
import com.livich.privatebin.facade.Retrofit;
import com.livich.privatebin.sys.PasswordGenerator;


import retrofit2.Call;

public class PrivateBinPaste extends PrivateBin {
    private int status;
    private String id;
    private String deletetoken;
    private transient String text;
    private String cipherdata, pass;

    public String getId() {
        return id;
    }

    public String getDeletetoken() {
        return deletetoken;
    }

    public int getStatus() {
        return status;
    }

    public String getText() {
        return this.text;
    }

    public PrivateBinPaste(String text) {
        super();
        this.setText(text);
    }

    public PrivateBinPaste setText(String text) {
        this.text = text;
        renewPassword();
        return this;
    }

    public PrivateBinPaste renewPassword() {
        this.pass = Base64.encodeToString(PasswordGenerator.randomBytes(32), Base64.NO_WRAP);
        try {
            this.cipherdata = this.encrypt(pass, text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getCipherdata() {
        return this.cipherdata;
    }

    public String getPassword() {
        return this.pass;
    }

    public Call<PrivateBinPaste> publish() {
        return Retrofit.getRetrofit(false).create(PrivateBinService.class).publish(
                this.getCipherdata(),
                Application.getPreferences().getString(SettingsActivity.KEY_PREF_EXPIRATION, "1hour"),
                Application.getPreferences().getBoolean(SettingsActivity.KEY_PREF_BURN, false) ? 1 : 0,
                Application.getPreferences().getBoolean(SettingsActivity.KEY_PREF_DISCUSS, false) ? 1 : 0,
                Application.getPreferences().getBoolean(SettingsActivity.KEY_PREF_SYNTAX, false) ? 1 : 0,
                "plaintext"
        );
    }
}
