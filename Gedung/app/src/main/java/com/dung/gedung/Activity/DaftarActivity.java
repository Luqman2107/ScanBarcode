package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.Model.ModelKota;
import com.dung.gedung.Model.ModelProvinsi;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;
import com.dung.gedung.configfile.authdata;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class DaftarActivity extends AppCompatActivity {
    List<ModelProvinsi> provinsiList;
    List<ModelKota> kotaList;
    ArrayAdapter<ModelProvinsi> provinsiAdapter;
    ArrayAdapter<ModelKota> kotaAdapter;

    authdata authdataa;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    TextInputLayout layoutpassword;
    TextInputEditText txtjeniskelamin, txtnama, txtemail, txtalamat, txtnotelepon, txtpassword, txtpassword2;
    MaterialButton btndaftar;
    TextView textmasuk;
    Spinner txtprovinsi, txtkotakab;
    String id_provinsi, id_kota;

    boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        init();

//        ------------------------------------ Spinner ---------------------------------------
        kotaList = new ArrayList<>();
        kotaList.add(new ModelKota("0", "Pilih Kabupaten / Kota"));
        txtkotakab.setEnabled(false);

        provinsiList = new ArrayList<>();
        provinsiList.add(new ModelProvinsi("0", "Pilih Provinsi"));
        loadProvinsi();
        txtprovinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModelProvinsi provinsiSelected = (ModelProvinsi) adapterView.getSelectedItem();
//                Toast.makeText(DaftarActivity.this, provinsiSelected.getId_prov() + " " + provinsiSelected.getNama_prov(), Toast.LENGTH_SHORT).show();
                id_provinsi = provinsiSelected.getId_prov();

                txtkotakab.setEnabled(true);

                if (txtprovinsi.getSelectedItem().toString().trim().equals("Pilih Provinsi")){
                    txtkotakab.setEnabled(false);
                } else {
                    loadKota();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtkotakab.setEnabled(false);
            }
        });

        txtkotakab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ModelKota kotaSelected = (ModelKota) adapterView.getSelectedItem();
                id_kota = kotaSelected.getId_kota();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        ------------------------------------ Spinner ---------------------------------------

        txtjeniskelamin.setInputType(InputType.TYPE_NULL);
        txtjeniskelamin.setFocusable(false);
        txtjeniskelamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DaftarActivity.this);
                builder.setTitle("Pilih Jenis Kelamin");

                // buat array list
                final String[] options = {"Pria", "Wanita"};

                //Pass array list di Alert dialog
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txtjeniskelamin.setText(options[which]);
                    }
                });
                // buat dan tampilkan alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String namanya = txtnama.getText().toString().trim();
                final String emailnya = txtemail.getText().toString().trim();
                final String alamatnya =txtalamat.getText().toString().trim();
                final String hpnya = txtnotelepon.getText().toString().trim();
                final String kelaminnya = txtjeniskelamin.getText().toString().trim();
                final String passwordnya = txtpassword.getText().toString().trim();
                final String passwordnya2 = txtpassword2.getText().toString().trim();

                if (namanya.matches("")){
                    Toast.makeText(DaftarActivity.this, "Masukkan Nama Lengkap Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (emailnya.matches("")){
                    Toast.makeText(DaftarActivity.this, "Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (alamatnya.matches("")){
                    Toast.makeText(DaftarActivity.this, "Masukkan Alamat Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (hpnya.matches("")){
                    Toast.makeText(DaftarActivity.this, "Masukkan No Telpon Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (kelaminnya.matches("")){
                    Toast.makeText(DaftarActivity.this, "Masukkan Jenis Kelamin Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordnya.matches("")){
                    Toast.makeText(DaftarActivity.this, "Masukkan Kata Sandi Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordnya2.matches("")){
                    Toast.makeText(DaftarActivity.this, "Masukkan Kata Sandi Anda", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwordnya.length() < 6) {
                    layoutpassword.setError("Kata Sandi minimal tediri dari 6 karakter");
                    return;
                } else {
                    layoutpassword.setError("");
                }
                if (!passwordnya.equals(passwordnya2))
                {
                    Toast.makeText(DaftarActivity.this, "Kata Sandi harus sama", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent daftar = new Intent(DaftarActivity.this, UploadFotoDaftarActivity.class);
                daftar.putExtra("datanama", namanya);
                daftar.putExtra("dataemail", emailnya);
                daftar.putExtra("dataalamat", alamatnya);
                daftar.putExtra("datahp", hpnya);
                daftar.putExtra("dataprov", id_provinsi);
                daftar.putExtra("datakota", id_kota);
                daftar.putExtra("datajenis", kelaminnya);
                daftar.putExtra("datapassword", passwordnya);
                startActivity(daftar);
            }
        });
    }

    public void init() {
        authdataa = new authdata(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        layoutpassword = findViewById(R.id.passwordLayout_daftar);
        txtjeniskelamin = findViewById(R.id.edt_jeniskelamin_daftar);
        txtprovinsi = findViewById(R.id.provinsiLayout_daftar2);
        txtkotakab = findViewById(R.id.kotakabLayout_daftar2);
        txtnama = findViewById(R.id.edt_nama_daftar);
        txtemail = findViewById(R.id.edt_email_daftar);
        txtalamat = findViewById(R.id.edt_alamat_daftar);
        txtnotelepon = findViewById(R.id.edt_hp_daftar);
        txtpassword = findViewById(R.id.edt_password_daftar);
        txtpassword2 = findViewById(R.id.edt_ulangipassword_daftar);
        textmasuk = findViewById(R.id.txt_masuk);
        btndaftar = findViewById(R.id.btn_daftar);
    }

    private void loadProvinsi() {
        StringRequest provinsi = new StringRequest(Request.Method.GET, ServerApi.URL_PROVINSI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.setMessage("Loading");
                progressDialog.show();
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject itemProvinsi = data.getJSONObject(i);

                        provinsiList.add(new ModelProvinsi(itemProvinsi.getString("id_prov"), itemProvinsi.getString("nama")));
                    }
                    provinsiAdapter = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_item, provinsiList);
                    provinsiAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    txtprovinsi.setAdapter(provinsiAdapter);
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
        requestQueue.add(provinsi);
    }

    private void loadKota() {
        StringRequest kota = new StringRequest(Request.Method.GET, ServerApi.URL_KOTA + id_provinsi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject itemKota = data.getJSONObject(i);

                        String id_kab = itemKota.getString("id_kab");
                        String namaKab = itemKota.getString("nama");

                        kotaList.add(new ModelKota(id_kab, namaKab));
                    }
                    kotaAdapter = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_item, kotaList);
                    kotaAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    txtkotakab.setAdapter(kotaAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(kota);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(DaftarActivity.this, MasukActivity.class);
            startActivity(abc);
            finish();
        }
    }
}