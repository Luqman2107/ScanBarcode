package com.example.scanbarcode.ConfigFile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class authdata {
    //    private static authdata mInstance;
    SharedPreferences sharedPreferences;
    public Context mCtx;

    public static final String SHARED_PREF_NAME = "ScanBarcode";
    private static final String sudahlogin = "n";
    public SharedPreferences.Editor editor;

    private static final String namaadm = "Adm_Name";
    private static final String noline = "Line_No";
    private static final String namastation = "Station_Name";
    private static final String namaio = "IO_Name";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";


    public authdata(Context context) {
        this.mCtx = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setdatauser(String xnama_adm, String xno_line, String xnama_station, String xnama_io) {
//        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();

        editor.putBoolean(LOGIN_STATUS, true);
        editor.putString(namaadm, xnama_adm);
        editor.putString(noline, xno_line);
        editor.putString(namastation, xnama_station);
        editor.putString(namaio, xnama_io);
        editor.putString(sudahlogin, "y");
        editor.apply();
    }


    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

//    public void logout() {
//        editor.clear();
//        editor.commit();
//
//        Intent login = new Intent(mCtx, LoginActivity.class);
//        mCtx.startActivity(login);
//        ((MainActivity) mCtx).finish();
//    }

    public String getNamaadm() {
        return sharedPreferences.getString(namaadm, null);
    }

    public String getNoline() {
        return sharedPreferences.getString(noline, null);
    }

    public String getNamastation() {
        return sharedPreferences.getString(namastation, null);
    }

    public String getNamaio() {
        return sharedPreferences.getString(namaio, null);
    }

    public void setNamaadm(String adm) {
        editor.putString(namaadm, adm);
        editor.apply();
    }

    public void setNoline(String line) {
        editor.putString(noline, line);
        editor.apply();
    }

    public void setNamastation(String station) {
        editor.putString(namastation, station);
        editor.apply();
    }

    public void setNamaio(String io) {
        editor.putString(namaio, io);
        editor.apply();
    }

}
