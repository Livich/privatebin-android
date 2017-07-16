package com.livich.privatebin.facade;

import com.livich.privatebin.Application;
import com.livich.privatebin.SettingsActivity;

import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static retrofit2.Retrofit _retrofit;
    private static URL _serviceUrl;

    public static URL getServiceUrl() {
        return _serviceUrl;
    }

    public static retrofit2.Retrofit getRetrofit(boolean forceNew) {
        if (_retrofit == null || forceNew) {
            try {
                _serviceUrl = new URL(
                        Application.getPreferences().getString(SettingsActivity.KEY_PREF_PROTOCOL, "http"),
                        Application.getPreferences().getString(SettingsActivity.KEY_PREF_HOST, ""),
                        Integer.parseInt(Application.getPreferences().getString(SettingsActivity.KEY_PREF_PORT, "80")),
                        ""
                );

                //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient client = new OkHttpClient.Builder()/*.addInterceptor(interceptor)*/.build();
                _retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl(_serviceUrl.toString())
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } catch (Exception e) {
                // TODO: catch more accurately
                e.printStackTrace();
                return null;
            }
        }
        return _retrofit;
    }
}
