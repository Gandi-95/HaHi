package com.gdi.hahi.utils

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.telephony.TelephonyManager

import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.Writer

/**
 * Created by gandi on 2017/9/6 0006.
 */

object DeviceIdUtils {

    fun getIMIEStatus(context: Context): String {
        val tm = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val deviceId = tm.deviceId
        return deviceId
    }

    // Mac地址
    fun getLocalMac(context: Context): String {
        val wifi = context
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = wifi.connectionInfo
        return info.macAddress
    }

    // Android Id
    fun getAndroidId(context: Context): String {
        val androidId = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ANDROID_ID)
        return androidId
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
