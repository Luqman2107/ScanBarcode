package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendOtpActivity extends AppCompatActivity {
    String emailintent;
    TextInputEditText edtemail, edtotp;
    Button btnverifOTP;
    TextView sendOtp;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    Boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);


        edtemail = findViewById(R.id.edt_email_OTP);
        edtotp = findViewById(R.id.edt_kode_OTP);
        sendOtp = findViewById(R.id.txt_sendOTP);
        btnverifOTP = findViewById(R.id.btn_verifotp);

//        Intent intent = getIntent();
//        emailintent = intent.getStringExtra("dataemailintent");
//        Log.e("test", emailintent);

//        if (emailintent.matches("")) {
//            edtemail.setText("");
//        } else {
//            edtemail.setText(emailintent);
//        }

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendotpnya();
            }
        });

        btnverifOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifOTPnya();
            }
        });
    }

    private void sendotpnya() {
        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_SENDOTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
//                    Toast.makeText(SendOtpActivity.this, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(SendOtpActivity.this, "Eror json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SendOtpActivity.this, "Eror volley", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", edtemail.getText().toString().trim());
                Log.e("data dari param ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void verifOTPnya() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_CHECKOTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String data = jsonObject.getString("data");
                    Boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        Intent a = new Intent(SendOtpActivity.this, MasukActivity.class);
                        Toast.makeText(SendOtpActivity.this, data, Toast.LENGTH_SHORT).show();
                        startActivity(a);
                        finish();
                    } else {
                        Toast.makeText(SendOtpActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(SendOtpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SendOtpActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("kode_otp", edtotp.getText().toString().trim());
                params.put("email", edtemail.getText().toString().trim());
//                Log.e("data dari param: ", "" + params);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(SendOtpActivity.this, MasukActivity.class);
            startActivity(abc);
            finish();
        }
    }
}