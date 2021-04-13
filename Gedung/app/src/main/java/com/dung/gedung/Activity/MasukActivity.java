package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MasukActivity extends AppCompatActivity {
    String token;
    TextView daftar;
    TextInputEditText email, password;
    TextInputLayout layoutPassword;
    Button btnmasuk;
    authdata authdataa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        init();
        authdataa = new authdata(this);

//        ---------------- token ----------------
        token = FirebaseInstanceId.getInstance().getToken();
        Log.e("aaa", token);
//        ---------------- token ----------------

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MasukActivity.this, DaftarActivity.class);
                startActivity(a);
                finish();
            }
        });

        btnmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(MasukActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()){
                    Toast.makeText(MasukActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().length() < 6) {
                    layoutPassword.setError("Kata Sandi minimal tediri dari 6 karakter");
                } else {
                    login();
                }
            }
        });

        if (authdataa.isLogin() == true) {
            Intent main = new Intent(MasukActivity.this, BerandaActivity.class);
            startActivity(main);
            finish();
        }
    }

    public void init(){
        daftar = findViewById(R.id.txt_daftar);
        email = findViewById(R.id.edt_email_masuk);
        password = findViewById(R.id.edt_password_masuk);
        layoutPassword = findViewById(R.id.passwordLayout_masuk);
        btnmasuk = findViewById(R.id.btn_masuk);
    }

    public void login(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject respon = jsonObject.getJSONObject("data");
//                    Toast.makeText(MasukActivity.this, respon.getString("pesan"), Toast.LENGTH_SHORT).show();

                    if (jsonObject.getString("data").matches("Maaf Akun Anda Tidak Aktif")) {
                        Intent intent = new Intent(MasukActivity.this, SendOtpActivity.class);
                        Toast.makeText(MasukActivity.this, "Silahkan aktivasi Akun anda!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        JSONObject data = jsonObject.getJSONObject("data");
                        authdataa.setdatauser(
                                data.getString("id_pengguna"),
                                data.getString("nama"),
                                data.getString("email"),
                                data.getString("nomor_telepon"),
                                data.getString("kota_kab"),
                                data.getString("foto_pengguna"),
                                data.getString("token")
                        );
                        Toast.makeText(MasukActivity.this, data.getString("pesan"), Toast.LENGTH_SHORT).show();
                    }


//                    if (data.getString("status").equals("2")) {
//                        if (data.getString("is_active").equals("1")){
////                            if (data.getString("token").equals("")){
//
//                                Intent intent = new Intent(MasukActivity.this, BerandaActivity.class);
//                                Toast.makeText(MasukActivity.this, data.getString("pesan"), Toast.LENGTH_SHORT).show();
//                                startActivity(intent);
//                                finish();
////                            } else {
////                                Toast.makeText(MasukActivity.this, "Akun anda masih digunakan!", Toast.LENGTH_SHORT).show();
////                            }
//                        } else {
//                            Intent intent = new Intent(MasukActivity.this, SendOtpActivity.class);
//                            Toast.makeText(MasukActivity.this, "Silahkan aktivasi Akun anda!", Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
//                        }
//                    } else {
//                        Toast.makeText(MasukActivity.this, "Aplikasi Hanya Untuk User / Pengguna.", Toast.LENGTH_SHORT).show();
//                    }
                } catch (JSONException e) {
                    Toast.makeText(MasukActivity.this, "Error json", Toast.LENGTH_SHORT).show();
                    Log.e("e: ", "" + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MasukActivity.this, "Email / No. Telepon yang anda masukkan salah !", Toast.LENGTH_SHORT).show();
                Log.e("e: ", "" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email.getText().toString().trim());
                params.put("password", password.getText().toString().trim());
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MasukActivity.this);
        requestQueue.add(stringRequest);
    }

}