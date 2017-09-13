package com.gdi.hahi.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by gandi on 2017/9/6 0006.
 */

public class DeviceIdUtils {

    public static String getIMIEStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    // Mac地址
    public static String getLocalMac(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    // Android Id
    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

//    public static void saveDeviceID(String str) {
//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            Writer out = new OutputStreamWriter(fos, "UTF-8");
//            out.write(str);
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String readDeviceID() {
//        StringBuffer buffer = new StringBuffer();
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
//            Reader in = new BufferedReader(isr);
//            int i;
//            while ((i = in.read()) > -1) {
//                buffer.append((char) i);
//            }
//            in.close();
//            return buffer.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
