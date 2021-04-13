package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;
import com.dung.gedung.configfile.authdata;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilActivity extends AppCompatActivity {
    authdata authdataa;
    CardView cardubahprofil, cardubahpassword, cardhome, cardlogout;
    TextView nama, kota;

    Boolean doubleBackToExit;
    String id_pengguna;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        init();

        nama.setText(authdataa.getNama());
        kota.setText(authdataa.getKota_kab());
        id_pengguna = authdataa.getId_pengguna();

        cardubahprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ProfilActivity.this, UbahProfilActivity.class);
                startActivity(a);
            }
        });
        cardubahpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(ProfilActivity.this, UbahPasswordActivity.class);
                startActivity(b);
            }
        });
        cardhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent c = new Intent(ProfilActivity.this, BerandaActivity.class);
                startActivity(c);
            }
        });
        cardlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });
    }

    public void init(){
        authdataa = new authdata(this);
        requestQueue = Volley.newRequestQueue(this);
        cardubahprofil = findViewById(R.id.cardEditProfil);
        cardubahpassword = findViewById(R.id.cardEditPassword);
        cardhome = findViewById(R.id.cardKembaliHome);
        cardlogout = findViewById(R.id.cardLogout);
        nama = findViewById(R.id.edt_nama_akun);
        kota = findViewById(R.id.edt_kota_akun);
    }

    public void Logout(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_LOGOUT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(ProfilActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_pengguna", id_pengguna);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(ProfilActivity.this, BerandaActivity.class);
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