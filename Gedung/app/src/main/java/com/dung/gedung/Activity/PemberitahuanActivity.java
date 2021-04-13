package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.Adapter.AdapterPemberitahuan;
import com.dung.gedung.Model.ModelPemberitahuan;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;
import com.dung.gedung.configfile.authdata;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PemberitahuanActivity extends AppCompatActivity {
    Toolbar toolbar;

    RecyclerView recyclerView;
    List<ModelPemberitahuan> item;
    AdapterPemberitahuan adapterPemberitahuan;

    RequestQueue requestQueue;
    authdata authdataa;

    String mIdPengguna;

    Boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemberitahuan);
        authdataa = new authdata(this);
        requestQueue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.recyclerPemberitahuan);
        mIdPengguna = authdataa.getId_pengguna();
        toolbar = findViewById(R.id.toolbarpemberitahuan);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Pemberitahuan");
        toolbar.setNavigationIcon(R.drawable.imgkembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PemberitahuanActivity.this, BerandaActivity.class));
                finish();
            }
        });

    }

    private void loadPemberitahuan() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_PEMESANAN + mIdPengguna, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(PemberitahuanActivity.this, BerandaActivity.class);
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
        }, 2000);
    }
}