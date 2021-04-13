package com.dung.gedung.Model;

import com.dung.gedung.configfile.ServerApi;

public class ModelFasilitas {
    private String id_fasilitas;
    private String nama_fasilitas;
    private String gambar_fasilitas;
    private String keterangan;

    public String getId_fasilitas() {
        return id_fasilitas;
    }

    public void setId_fasilitas(String id_fasilitas) {
        this.id_fasilitas = id_fasilitas;
    }

    public String getNama_fasilitas() {
        return nama_fasilitas;
    }

    public void setNama_fasilitas(String nama_fasilitas) {
        this.nama_fasilitas = nama_fasilitas;
    }

    public String getGambar_fasilitas() {
//        return gambar_fasilitas;
        return ServerApi.FotoFasilitas + gambar_fasilitas;
    }

    public void setGambar_fasilitas(String gambar_fasilitas) {
        this.gambar_fasilitas = gambar_fasilitas;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
