package com.dung.gedung.configfile;

public class ServerApi {
    public static final String IPServer="http://desangoran.flow-byte.com/api/";
    public static final String URL_REGIS=IPServer+"Register";
    public static final String URL_LOGIN=IPServer+"Login";
    public static final String URL_PROVINSI=IPServer+"Provinsi";
    public static final String URL_KOTA=IPServer+"Kota?id_prov=";
    public static final String URL_PEMESANAN=IPServer+"Acara";
    public static final String URL_FASILITAS=IPServer+"Fasilitas";
    public static final String URL_SENDOTP=IPServer+"SendOTP";
    public static final String URL_CHECKOTP=IPServer+"CekOTP";
    public static final String URL_JADWAL=IPServer+"FilterByTanggal";
    public static final String URL_PROFILGEDUNG=IPServer+"Profile";
    public static final String URL_UNITKERJA=IPServer+"UnitDesa";
    public static final String URL_FASILITASBYID=IPServer+"FasilitasById?id_acara=";
    public static final String URL_LOGOUT=IPServer+"Logout";
    public static final String URL_TESTIMONI=IPServer+"Testimoni";

//    LINK URL FOTO
    public static final String FotoFasilitas="http://desangoran.flow-byte.com/uploads/Fasilitas/";
    public static final String FotoUnitKerja="http://desangoran.flow-byte.com/uploads/unitdesa/";
    public static final String FotoTestimoni="http://desangoran.flow-byte.com/uploads/Testimoni/";
    public static final String FotoUser="http://desangoran.flow-byte.com/uploads/profile_user/";
}
