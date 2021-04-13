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
import com.dung.gedung.Adapter.AdapterTestimoni;
import com.dung.gedung.Model.ModelFasilitas;
import com.dung.gedung.Model.ModelTestimoni;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestimoniActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ModelTestimoni> item;
    AdapterTestimoni adapterTestimoni;

    RequestQueue requestQueue;

    Toolbar toolbar;
    Boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimoni);
        recyclerView = findViewById(R.id.recyclerTestimoni);
        requestQueue = Volley.newRequestQueue(this);

        //       ---------------------------------toolbar-------------------------------------
        toolbar = findViewById(R.id.toolbartestimoni);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Testimoni");
        toolbar.setNavigationIcon(R.drawable.imgkembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestimoniActivity.this, BerandaActivity.class));
                finish();
            }
        });
//        ---------------------------------toolbar-------------------------------------

        loadTestimoni();
    }

    public void loadTestimoni(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_TESTIMONI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");

                    item = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelTestimoni modelTestimoni = new ModelTestimoni();
                        JSONObject datanya = data.getJSONObject(i);
                        modelTestimoni.setNama_testimoni(datanya.getString("nama"));
                        modelTestimoni.setDeskripsi(datanya.getString("ulasan"));
                        modelTestimoni.setFoto_testimoni(datanya.getString("gambar_testimoni"));;
                        item.add(modelTestimoni);
                    }
                    adapterTestimoni = new AdapterTestimoni(TestimoniActivity.this, item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TestimoniActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapterTestimoni);
                } catch (JSONException e) {
                    Log.e("Error json", e.toString() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse: ", error.toString());
                Toast.makeText(TestimoniActivity.this, "Testimoni tidak ada.", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(TestimoniActivity.this, BerandaActivity.class);
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