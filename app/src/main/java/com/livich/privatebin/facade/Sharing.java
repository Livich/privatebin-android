package com.livich.privatebin.facade;

import android.content.Context;
import android.content.Intent;


public class Sharing {
    public static void shareText(Context ctx, String text, String subject, String title) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        ctx.startActivity(
                Intent.createChooser(sharingIntent, title)
        );
    }
}
