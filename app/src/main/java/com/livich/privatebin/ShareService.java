package com.livich.privatebin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.livich.privatebin.sys.ShareHandler;

public class ShareService extends Service {
    private final IBinder mBinder = new ShareBinder();

    class ShareBinder extends Binder {
        ShareService getService() {
            return ShareService.this;
        }
    }

    public static Intent createShareService(Context context) {
        return new Intent(context, ShareService.class);
    }

    public ShareService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void sharePaste(Intent i) {
        ShareHandler.handle(i);
    }
}
