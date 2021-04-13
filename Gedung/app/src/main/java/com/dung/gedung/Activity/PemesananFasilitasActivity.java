package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.Adapter.AdapterPesanFasilitas;
import com.dung.gedung.Model.ModelFasilitas;
import com.dung.gedung.Model.ModelPesanFasilitas;
import com.dung.gedung.Model.ModelSpinnerFasilitasPesan;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;
import com.dung.gedung.configfile.authdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PemesananFasilitasActivity extends AppCompatActivity {
    String id_acara, id_fasilitas;
    Spinner spinner;
    Button btntambah, btnsimpan;
    RecyclerView recyclerView;

    List<ModelSpinnerFasilitasPesan> fasilitasList;
    ArrayAdapter<ModelSpinnerFasilitasPesan> adapterPesanFasilitas;

    AdapterPesanFasilitas adapter;
    List<ModelPesanFasilitas> item;

    ProgressDialog progressDialog;
    authdata authdataa;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan_fasilitas);
        init();

        Intent intent = getIntent();
        id_acara = intent.getStringExtra("dataidacara");

        loadFasilitasById();

//        -------------------Spinner Fasilitas------------------------
        fasilitasList = new ArrayList<>();
        fasilitasList.add(new ModelSpinnerFasilitasPesan("0", "Pilih Fasilitas"));
        loadFasilitas();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModelSpinnerFasilitasPesan fasilitasSelected = (ModelSpinnerFasilitasPesan) adapterView.getSelectedItem();
                id_fasilitas = fasilitasSelected.getId_fasilitas();

//                if (spinner.getSelectedItem().toString().trim().equals("Pilih Fasilitas")){
//                    txtkotakab.setEnabled(false);
//                } else {
//                    loadKota();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanFasilitas();
            }
        });

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void init(){
        progressDialog = new ProgressDialog(this);
        authdataa = new authdata(this);
        requestQueue = Volley.newRequestQueue(this);
        spinner = findViewById(R.id.provinsiLayout_daftar2);
        btntambah = findViewById(R.id.btn_tambah_fasilitas);
        btnsimpan = findViewById(R.id.btn_simpan_fasilitas);
        recyclerView = findViewById(R.id.recyclerPesanFasilitas);
    }

    public void loadFasilitas(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_FASILITAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.setMessage("Loading");
                progressDialog.show();
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject itemFasilitas = data.getJSONObject(i);

                        fasilitasList.add(new ModelSpinnerFasilitasPesan(itemFasilitas.getString("id_fasilitas"), itemFasilitas.getString("nama_fasilitas")));
                    }
                    adapterPesanFasilitas = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_item, fasilitasList);
                    adapterPesanFasilitas.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapterPesanFasilitas);
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void simpanFasilitas(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_FASILITAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean status = jsonObject.getBoolean("status");
                    String pesan = jsonObject.getString("pesan");
                    if (status) {
                        Toast.makeText(PemesananFasilitasActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        loadFasilitasById();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent main = new Intent(PemesananFasilitasActivity.this, IlustrasiSuksesActivity.class);
//                                startActivity(main);
//                                finish();
//                            }
//                        }, 1500);
                    } else {
                        Toast.makeText(PemesananFasilitasActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(PemesananFasilitasActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PemesananFasilitasActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_acara", id_acara);
                params.put("id_fasilitas", id_fasilitas);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void loadFasilitasById(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_FASILITASBYID + id_acara, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    item = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelPesanFasilitas playerModel = new ModelPesanFasilitas();
                        JSONObject datae = data.getJSONObject(i);
                        playerModel.setId_fasilitas(datae.getString("id_fasilitas"));
                        playerModel.setNama_fasilitas("- " + datae.getString("nama_fasilitas"));

                        item.add(playerModel);
                    }
                    adapter = new AdapterPesanFasilitas(PemesananFasilitasActivity.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PemesananFasilitasActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(PemesananFasilitasActivity.this, "Fasilitas Belum Dipilih", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PemesananFasilitasActivity.this, "Fasilitas Belum Dipilih", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PemesananFasilitasActivity.this);

        // set title dialog
        alertDialogBuilder.setTitle("Simpan ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Simpan untuk melanjutkan!")
//                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Simpan",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Intent intent = new Intent(PemesananFasilitasActivity.this, IlustrasiSuksesActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PemesananFasilitasActivity.this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar ?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Keluar untuk kembali ke Beranda!")
//                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Keluar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        Intent intent = new Intent(PemesananFasilitasActivity.this, BerandaActivity.class);
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