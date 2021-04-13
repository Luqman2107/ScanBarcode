package com.dung.gedung.Model;

import com.dung.gedung.configfile.ServerApi;

public class ModelUnit {
    private String gambar;
    private String nama_unit;
    private String deskripsi_unit;

    public String getGambar() {
//        return gambar;
        return ServerApi.FotoUnitKerja + gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNama_unit() {
        return nama_unit;
    }

    public void setNama_unit(String nama_unit) {
        this.nama_unit = nama_unit;
    }

    public String getDeskripsi_unit() {
        return deskripsi_unit;
    }

    public void setDeskripsi_unit(String deskripsi_unit) {
        this.deskripsi_unit = deskripsi_unit;
    }
}
