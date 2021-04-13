package com.dung.gedung.Model;

import com.dung.gedung.configfile.ServerApi;

public class ModelTestimoni {
    private String nama_testimoni;
    private String foto_testimoni;
    private String deskripsi;

    public String getNama_testimoni() {
        return nama_testimoni;
    }

    public void setNama_testimoni(String nama_testimoni) {
        this.nama_testimoni = nama_testimoni;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto_testimoni() {
//        return foto_testimoni;
        return ServerApi.FotoTestimoni + foto_testimoni;
    }

    public void setFoto_testimoni(String foto_testimoni) {
        this.foto_testimoni = foto_testimoni;
    }
}
