package com.livich.privatebin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.livich.privatebin.facade.Retrofit;

public class ShareActivity extends Activity {

    ShareService mService;
    boolean mBound = false;
    private Intent shareIntent;
    TextView tvInfo;
    Button btnShare, btnCancel;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            ShareService.ShareBinder binder = (ShareService.ShareBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    protected void onStart() {
        super.onStart();
        bindService(
                ShareService.createShareService(this),
                mConnection,
                Context.BIND_AUTO_CREATE);
    }

    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    private void _findControls() {
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareActivity.this.finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Retrofit.getRetrofit(false) == null
                || Application.getPreferences().getString(SettingsActivity.KEY_PREF_HOST, "").length() <= 1) {
            setContentView(R.layout.error_dialog);
            _findControls();
            tvInfo.setText(R.string.s_error_host);
            return;
        }

        shareIntent = getIntent();
        setContentView(R.layout.share_dialog);
        _findControls();
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setText(String.format(getString(R.string.s_share_dialog), Retrofit.getServiceUrl().toString()));

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    new Thread() {
                        public void run() {
                            mService.sharePaste(shareIntent);
                        }
                    }.start();
                    ShareActivity.this.finish();
                }
            }
        });

    }
}
