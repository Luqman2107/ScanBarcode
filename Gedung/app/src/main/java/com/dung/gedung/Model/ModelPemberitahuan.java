package com.dung.gedung.Model;

public class ModelPemberitahuan {
    private String status;
    private String deskripsi;
    private String tgl_pemesanan;
    private String foto;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeskripsi(){
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi){
        this.deskripsi = deskripsi;
    }

    public String getTgl_pemesanan(){
        return tgl_pemesanan;
    }

    public void setTgl_pemesanan(String tgl_pemesanan){
        this.tgl_pemesanan = tgl_pemesanan;
    }

    public String getFoto(){
        return foto;
    }

    public void setFoto(String foto){
        this.foto = foto;
    }
}
