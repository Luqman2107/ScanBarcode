package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.Adapter.AdapterJadwal;
import com.dung.gedung.Adapter.AdapterPesanFasilitas;
import com.dung.gedung.Model.ModelJadwal;
import com.dung.gedung.Model.ModelPesanFasilitas;
import com.dung.gedung.Model.ModelSpinnerFasilitasPesan;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JadwalActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText tglsewa, tglkembali;
    Button btnfilter;
    RecyclerView recyclerView;

    AdapterJadwal adapter;
    List<ModelJadwal> item;

    Boolean doubleBackToExit;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        tglsewa = findViewById(R.id.btn_tglsewa_jadwal);
        tglkembali = findViewById(R.id.btn_tglkembali_jadwal);
        btnfilter = findViewById(R.id.btn_filter_jadwal);
        recyclerView = findViewById(R.id.recyclerJadwal);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        toolbar = findViewById(R.id.toolbarjadwal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Jadwal");
        toolbar.setNavigationIcon(R.drawable.imgkembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JadwalActivity.this, BerandaActivity.class));
                finish();
            }
        });

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadtanggal();
            }
        });
    }

    public void loadtanggal(){
        final String tglsewanya = tglsewa.getText().toString().trim();
        final String tglkembalinya = tglkembali.getText().toString().trim();

        if (tglsewanya.matches("")){
            Toast.makeText(JadwalActivity.this, "Tanggal Sewa tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tglkembalinya.matches("")){
            Toast.makeText(JadwalActivity.this, "Tanggal Kembali tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_JADWAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.setMessage("Loading");
                progressDialog.show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String pesan = jsonObject.getString("pesan");
                    JSONArray data = jsonObject.getJSONArray("data");
                    item = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelJadwal playerModel = new ModelJadwal();
                        JSONObject datae = data.getJSONObject(i);
                        playerModel.setStatus(datae.getString("status"));
                        playerModel.setTanggal_mulai(datae.getString("tanggal_mulai"));

                        item.add(playerModel);
                    }
                    adapter = new AdapterJadwal(JadwalActivity.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(JadwalActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tanggal_mulai", tglsewanya);
                params.put("tanggal_selesai", tglkembalinya);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(JadwalActivity.this, BerandaActivity.class);
            startActivity(abc);
            finish();
        }

        this.doubleBackToExit = true;
        Toast.makeText(this, "Tekan sekali lagi untuk kembali", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExit=false;
            }
        }, 1000);
    }
}