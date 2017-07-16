package com.livich.privatebin.model;

import android.content.res.AssetManager;
import android.util.Base64;

import com.livich.privatebin.Application;

import org.liquidplayer.webkit.javascriptcore.JSContext;
import org.liquidplayer.webkit.javascriptcore.JSValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * FIXME: this class cannot be tested using Robolectric due to
 * org.liquidplayer.webkit dependency which works correctly on Android only.
 */

public class PrivateBin {

    private transient JSContext jsContext = null;

    public PrivateBin() {
        if (this.jsContext == null) {
            try {
                initSJCL();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*public JSONObject getCipherData(String json){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;

        try {
            obj = (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert obj != null;

        try {
            obj = (JSONObject) parser.parse((String) obj.get("data"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return obj;
    }*/

    private void initSJCL() throws IOException {
        String _sFuncPattern = "function %s(pass,data){try{return sjcl.%s(pass,data);}catch(e){return e;}}";
        jsContext = new JSContext();
        AssetManager am = Application.getInstance().getAssets();
        InputStream sjclStream = am.open("sjcl.js");
        byte[] buf = new byte[sjclStream.available()];
        sjclStream.read(buf);
        sjclStream.close();
        jsContext.evaluateScript(new String(buf));
        jsContext.evaluateScript(String.format(_sFuncPattern, "encrypt", "encrypt"));
        jsContext.evaluateScript(String.format(_sFuncPattern, "decrypt", "decrypt"));
    }

    public String encrypt(String password, byte[] data) {
        return encrypt(password, deflate(data));
    }

    public String encrypt(String password, String data) {
        jsContext.property("p", password);
        jsContext.property("d", data);
        jsContext.evaluateScript("var result = encrypt(p,d);");
        JSValue result = jsContext.property("result");
        return result.toString();
    }

    public String decrypt(String password, String data) {
        jsContext.property("p", password);
        jsContext.property("d", data);
        jsContext.evaluateScript("var result = decrypt(p,d);");
        JSValue result = jsContext.property("result");
        return result.toString();
    }

    private static String deflate(byte[] data) {
        DeflaterOutputStream def = null;
        String compressed = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // create deflater without header
            def = new DeflaterOutputStream(out, new Deflater(Deflater.DEFAULT_COMPRESSION, true));
            def.write(data);
            def.close();
            compressed = Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP);
        } catch (Exception e) {
            ///TODO
            e.printStackTrace();
            return null;
        }
        return compressed;
    }

}
