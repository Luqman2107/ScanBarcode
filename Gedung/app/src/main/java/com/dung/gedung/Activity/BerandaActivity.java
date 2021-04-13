package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.Adapter.AdapterJadwal;
import com.dung.gedung.Adapter.AdapterUnit;
import com.dung.gedung.Model.ModelJadwal;
import com.dung.gedung.Model.ModelUnit;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;
import com.dung.gedung.configfile.authdata;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BerandaActivity extends AppCompatActivity {
    TextView txtprofildesangoran;
    LinearLayout linearjadwal, linearpemesanan, linearpemberitahuan, linearfasilitas, linearprofil, lineartestimoni;
    CarouselView carouselView;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    AdapterUnit adapter;
    List<ModelUnit> item;

    int[] sampleImgae = {
            R.drawable.gedung1,
            R.drawable.gedung2,
            R.drawable.gedung3
    };

    authdata authdataa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        init();

        deskripsi();
        loadUnit();

        linearprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(BerandaActivity.this, ProfilActivity.class);
                startActivity(a);
                finish();
            }
        });

        linearjadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(BerandaActivity.this, JadwalActivity.class);
                startActivity(b);
                finish();
            }
        });
        linearpemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(BerandaActivity.this, PemesananActivity.class);
                startActivity(c);
                finish();
            }
        });
        linearpemberitahuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent d = new Intent(BerandaActivity.this, PemberitahuanActivity.class);
                startActivity(d);
                finish();
            }
        });
        linearfasilitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(BerandaActivity.this, FasilitasActivity.class);
                startActivity(e);
                finish();
            }
        });

        lineartestimoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f = new Intent(BerandaActivity.this, TestimoniActivity.class);
                startActivity(f);
                finish();
            }
        });

        carouselView.setPageCount(sampleImgae.length);
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImgae[position]);
            }
        };
        carouselView.setImageListener(imageListener);
    }

    public void init() {
        authdataa = new authdata(this);
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recyclerUnit);
        linearprofil = findViewById(R.id.linearProfil);
        lineartestimoni = findViewById(R.id.linearTestimoni);
        linearjadwal = findViewById(R.id.linearJadwal);
        linearpemesanan = findViewById(R.id.linearPemesanan);
        linearpemberitahuan = findViewById(R.id.linearPemberitahuan);
        linearfasilitas = findViewById(R.id.linearFasilitas);
        txtprofildesangoran = findViewById(R.id.edt_profildesangoran);
        carouselView = findViewById(R.id.carouselViewHome);
    }

    public void deskripsi(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_PROFILGEDUNG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    String deskripsi = data.getString("deskripsi");
                    txtprofildesangoran.setText(deskripsi);
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void loadUnit() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_UNITKERJA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    item = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelUnit playerModel = new ModelUnit();
                        JSONObject datae = data.getJSONObject(i);
                        playerModel.setGambar(datae.getString("gambar"));
                        playerModel.setNama_unit(datae.getString("nama_unit"));
                        playerModel.setDeskripsi_unit(datae.getString("deskripsi"));

                        item.add(playerModel);
                    }
                    adapter = new AdapterUnit(BerandaActivity.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BerandaActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(BerandaActivity.this, "Error JSON !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BerandaActivity.this, "Error Connection !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}