package com.dung.gedung.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dung.gedung.R;
import com.dung.gedung.VolleyMultipartRequest;
import com.dung.gedung.configfile.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.EasyImage;

public class UploadFotoDaftarActivity extends AppCompatActivity {
    Toolbar toolbar;

    private int CAMERA_REQUEST = 1888;
    private int GALLERY_REQUEST = 1999;
    final CharSequence[] dialogItems = {"Kamera", "Galeri", "Batal"};
    public static final int REQUEST_CODE_CAMERA = 001;
    public static final int REQUEST_CODE_GALLERY = 002;

    ProgressBar progressBar;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    Bitmap bitmapDaftar;
    String nama, email, alamat, hp, prov, kota, jenis, password;
    CircleImageView imageView;
    Button btnsimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_foto_daftar);
        progressBar = new ProgressBar(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

//      ------------  toolbar  ----------------
        toolbar = findViewById(R.id.toolbaruploadfotodaftar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadFotoDaftarActivity.this, DaftarActivity.class));
                finish();
            }
        });
//      ------------  toolbar  ----------------

//      --------------- Paring data ------------------
        Intent intent = getIntent();
        nama = intent.getStringExtra("datanama");
        email = intent.getStringExtra("dataemail");
        alamat = intent.getStringExtra("dataalamat");
        hp = intent.getStringExtra("datahp");
        prov = intent.getStringExtra("dataprov");
        kota = intent.getStringExtra("datakota");
        jenis = intent.getStringExtra("datajenis");
        password = intent.getStringExtra("datapassword");
//        Log.e("qwewqewq", nama);
//        --------------- Paring data ------------------

        imageView = findViewById(R.id.imguploadfoto);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestImage();
            }
        });

        btnsimpan = findViewById(R.id.btn_uploadfotodaftar);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftarfoto();
            }
        });
    }

    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
            return false;
        } else {
            return true;
        }
    }

    private void setRequestImage() {
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Pilih Foto Profil Anda")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                //Membuka Kamera Untuk Mengambil Gambar
                                EasyImage.openCamera(UploadFotoDaftarActivity.this, REQUEST_CODE_CAMERA);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(UploadFotoDaftarActivity.this, REQUEST_CODE_GALLERY);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Method Ini Digunakan Untuk Menghandle Error pada Image
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                //Method Ini Digunakan Untuk Menghandle Image
                switch (type) {
                    case REQUEST_CODE_CAMERA:
                        Glide.with(UploadFotoDaftarActivity.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                        bitmapDaftar = BitmapFactory.decodeFile(imageFile.getPath());
                        break;

                    case REQUEST_CODE_GALLERY:
                        Glide.with(UploadFotoDaftarActivity.this)
                                .load(imageFile)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                        bitmapDaftar = BitmapFactory.decodeFile(imageFile.getPath());
                        break;
                }
//                if (bitmap != null) {
//                    btn_pilih_foto_profil.setVisibility(View.GONE);
//                    btn_edit_foto_profil.setVisibility(View.VISIBLE);
//                    btn_simpan.setVisibility(View.VISIBLE);
//                    btn_simpan_disable.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Batalkan penanganan, Anda mungkin ingin menghapus foto yang diambil jika dibatalkan

            }
        });
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void daftarfoto() {
        progressDialog.setMessage("Loading");
        progressDialog.show();
        final VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, ServerApi.URL_REGIS, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    progressDialog.dismiss();
                    Toast.makeText(UploadFotoDaftarActivity.this, "Berhasil mendaftarkan akun", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadFotoDaftarActivity.this, SendOtpActivity.class);
                    intent.putExtra("dataemailintent", email);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UploadFotoDaftarActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("e: ", "" + e);
//                    Intent intent = new Intent(UploadFotoDaftarActivity.this, IlustrasiSuksesActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(UploadFotoDaftarActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.e("err: ", "" + error);
//                Intent intent = new Intent(UploadFotoDaftarActivity.this, IlustrasiSuksesActivity.class);
//                startActivity(intent);
//                finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("nomor_telepon", hp);
                params.put("kota_kab", kota);
                params.put("provinsi", prov);
                params.put("jenis_kelamin", jenis);
                params.put("password", password);
                Log.e("data dari param: ", "" + params);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("foto_pengguna", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(bitmapDaftar)));
                Log.e("foto: ", "" + imagename);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(volleyMultipartRequest);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UploadFotoDaftarActivity.this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Keluar untuk kembali he halaman Masuk!")
//                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Keluar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Intent intent = new Intent(UploadFotoDaftarActivity.this, MasukActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}