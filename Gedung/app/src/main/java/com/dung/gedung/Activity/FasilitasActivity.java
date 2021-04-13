package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.Adapter.AdapterFasilitas;
import com.dung.gedung.Model.ModelFasilitas;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FasilitasActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelFasilitas> item;
    AdapterFasilitas adapterFasilitas;

    RequestQueue requestQueue;

    Toolbar toolbar;
    Boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fasilitas);
        recyclerView = findViewById(R.id.recyclerFasilitas);
        requestQueue = Volley.newRequestQueue(this);

//       ---------------------------------toolbar-------------------------------------
        toolbar = findViewById(R.id.toolbarfasilitas);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Fasilitas");
        toolbar.setNavigationIcon(R.drawable.imgkembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FasilitasActivity.this, BerandaActivity.class));
                finish();
            }
        });
//        ---------------------------------toolbar-------------------------------------
        loadFasilitas();
    }

    private void loadFasilitas() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_FASILITAS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");

                    item = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelFasilitas modelFasilitas = new ModelFasilitas();
                        JSONObject datanya = data.getJSONObject(i);
                        modelFasilitas.setId_fasilitas(datanya.getString("id_fasilitas"));
                        modelFasilitas.setNama_fasilitas(datanya.getString("nama_fasilitas"));
                        modelFasilitas.setGambar_fasilitas(datanya.getString("gambar_fasilitas"));
                        modelFasilitas.setKeterangan(datanya.getString("keterangan"));
                        item.add(modelFasilitas);
                    }
                    adapterFasilitas = new AdapterFasilitas(FasilitasActivity.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FasilitasActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapterFasilitas);

                    adapterFasilitas.setListener(new AdapterFasilitas.OnHistoryClickListener() {
                        @Override
                        public void onClick(int position) {
                            ModelFasilitas modelFasilitas = item.get(position);
                            Toast.makeText(FasilitasActivity.this, modelFasilitas.getGambar_fasilitas(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(FasilitasActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FasilitasActivity.this, "Fasilitas tidak ada.", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(FasilitasActivity.this, BerandaActivity.class);
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