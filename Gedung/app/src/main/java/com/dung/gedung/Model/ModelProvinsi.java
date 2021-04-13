package com.dung.gedung.Model;

import androidx.annotation.NonNull;

public class ModelProvinsi {
    String id_prov;
    String nama_prov;

    public ModelProvinsi(String id_prov, String nama_prov) {
        this.id_prov = id_prov;
        this.nama_prov = nama_prov;
    }

    public String getId_prov() {
        return id_prov;
    }

    public void setId_prov(String id_prov) {
        this.id_prov = id_prov;
    }

    public String getNama_prov() {
        return nama_prov;
    }

    public void setNama_prov(String nama_prov) {
        this.nama_prov = nama_prov;
    }

    @NonNull
    @Override
    public String toString() {
        return getNama_prov();
    }
}
