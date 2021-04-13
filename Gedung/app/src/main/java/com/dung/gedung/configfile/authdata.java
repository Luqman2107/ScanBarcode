package com.dung.gedung.configfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.dung.gedung.Activity.BerandaActivity;
import com.dung.gedung.Activity.MasukActivity;

public class authdata {
    //    private static authdata mInstance;
    public SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public Context mCtx;

    public static final String SHARED_PREF_NAME = "Gedung";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    private static final String id_pengguna = "id_pengguna";
    private static final String nama = "nama";
    private static final String email = "email";
    private static final String nomor_telepon = "nomor_telepon";
    private static final String kota_kab = "kota_kab";
    private static final String foto_pengguna = "foto_pengguna";
    private static final String token = "token";


    public authdata(Context context) {
        this.mCtx = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setdatauser(String xid_pengguna, String xnama_pengguna,
                            String xemail_pengguna, String xnohp_pengguna,
                            String xkota_pengguna, String xtoken_pengguna,
                            String xfoto_pengguna) {

        editor.putBoolean(LOGIN_STATUS, true);
        editor.putString(id_pengguna, xid_pengguna);
        editor.putString(nama, xnama_pengguna);
        editor.putString(email, xemail_pengguna);
        editor.putString(nomor_telepon, xnohp_pengguna);
        editor.putString(kota_kab, xkota_pengguna);
        editor.putString(foto_pengguna, xfoto_pengguna);
        editor.putString(token, xtoken_pengguna);
        editor.apply();
    }


    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void logout() {
        editor.clear();
        editor.commit();

        Intent login = new Intent(mCtx, MasukActivity.class);
        mCtx.startActivity(login);
        ((BerandaActivity) mCtx).finish();
    }

    public String getId_pengguna() {
        return sharedPreferences.getString(id_pengguna, null);
    }

    public String getNama() {
        return sharedPreferences.getString(nama, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(email, null);
    }

    public String getNomor_telepon() {
        return sharedPreferences.getString(nomor_telepon, null);
    }

    public String getKota_kab() {
        return sharedPreferences.getString(kota_kab, null);
    }

    public String getFoto_pengguna() {
        return sharedPreferences.getString(foto_pengguna, null);
    }

    public String getToken() {
        return sharedPreferences.getString(token, null);
    }

    public void setNamaPengguna(String namaPengguna) {
        editor.putString(nama, namaPengguna);
        editor.apply();
    }

    public void setKota_kab(String kotaPengguna) {
        editor.putString(kota_kab, kotaPengguna);
        editor.apply();
    }

    public void setFotoPengguna(String fotoPengguna) {
        editor.putString(foto_pengguna, fotoPengguna);
        editor.apply();
    }

}
