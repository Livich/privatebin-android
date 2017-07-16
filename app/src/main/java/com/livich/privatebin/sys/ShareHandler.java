package com.livich.privatebin.sys;

import android.content.ClipData;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.livich.privatebin.Application;
import com.livich.privatebin.R;
import com.livich.privatebin.facade.Retrofit;
import com.livich.privatebin.model.PrivateBinPaste;

import retrofit2.Call;
import retrofit2.Response;

public class ShareHandler {
    private static ShareHandler self;

    public static boolean handle(Intent intent) {
        //thanks to https://developer.android.com/intl/ru/training/sharing/receive.html
        if (ShareHandler.self == null) {
            __initialize();
        }
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                return self.handleSendText(intent.getStringExtra(Intent.EXTRA_TEXT));
            }
        }

        return false;
    }

    private boolean handleSendText(String text) {
        PrivateBinPaste pbPaste = new PrivateBinPaste(text);
        Call<PrivateBinPaste> call = pbPaste.publish();
        call.enqueue(new PrivateBinPublishCallback(pbPaste));
        return true;
    }

    private static void __initialize() {
        self = new ShareHandler();
    }

    private class PrivateBinPublishCallback implements retrofit2.Callback<PrivateBinPaste> {

        private final PrivateBinPaste paste;

        public PrivateBinPublishCallback(PrivateBinPaste paste) {
            this.paste = paste;
        }

        @Override
        public void onResponse(Call<PrivateBinPaste> call, Response<PrivateBinPaste> response) {
            Log.v(Application.TAG, "got server response");
            if (response.body().getStatus() == 1) {
                // TODO: handle this error more accurate
                Toast.makeText(Application.context(), "There was an error during publishing paste", Toast.LENGTH_SHORT).show();
                return;
            }
            String sURL = Retrofit.getServiceUrl().toString()
                    + "/?" + response.body().getId()
                    + "#" + paste.getPassword();
            Application.getClipboardManager().setPrimaryClip(
                    ClipData.newPlainText(
                            Application.getInstance().getString(R.string.s_url_title), sURL
                    )
            );
            Toast.makeText(Application.context(), R.string.s_link_copied, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<PrivateBinPaste> call, Throwable t) {
            Toast.makeText(
                    Application.context(),
                    R.string.s_paste_error,
                    Toast.LENGTH_SHORT).show();
            t.printStackTrace();
        }
    }
}
