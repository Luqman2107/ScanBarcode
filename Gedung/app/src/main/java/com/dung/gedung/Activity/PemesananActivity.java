package com.dung.gedung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dung.gedung.MainActivity;
import com.dung.gedung.R;
import com.dung.gedung.configfile.ServerApi;
import com.dung.gedung.configfile.authdata;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PemesananActivity extends AppCompatActivity {
    TextInputEditText edtnama, edtnohp, edtemail, edtacara, edtjnsacara, edtjmlpeserta;
    TextInputLayout textInputLayout;
    CheckBox checkBox;
    Toolbar toolbar;
    EditText tglsewa, tglkembali;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date, date2;
    Button btnpesan;
    TextView textView;

    String id_pengguna;

    authdata authdataa;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    Boolean doubleBackToExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);
        init();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PemesananActivity.this, "Syarat dan Ketentuan belum ada", Toast.LENGTH_SHORT).show();
            }
        });

//        ----------Toolbar-------------
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pemesanan");
        toolbar.setNavigationIcon(R.drawable.imgkembali);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PemesananActivity.this, BerandaActivity.class));
                finish();
            }
        });
//        ----------Toolbar-------------

//        ----------Calendar-------------
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = tahun + "-" + bulan + "-" + hari;
                tglsewa.setText(text);
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                String myFormat = "dd-MMMM-yyyy";
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                tglsewa.setText(simpleDateFormat.format(myCalendar.getTime()));
            }
        };

        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = tahun + "-" + bulan + "-" + hari;
                tglkembali.setText(text);
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                String myFormat = "dd-MMMM-yyyy";
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                tglkembali.setText(simpleDateFormat.format(myCalendar.getTime()));
            }
        };

        tglsewa.setInputType(InputType.TYPE_NULL);
        tglsewa.setFocusable(true);
        tglkembali.setInputType(InputType.TYPE_NULL);
        tglkembali.setFocusable(true);

        tglsewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PemesananActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tglkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(PemesananActivity.this, date2,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//        ----------Calendar-------------

        edtjnsacara.setInputType(InputType.TYPE_NULL);
        edtjnsacara.setFocusable(false);
        edtjnsacara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PemesananActivity.this);
                builder.setTitle("Pilih Jenis Acara");

                // buat array list
                final String[] options = {"Olahraga", "Umum", "Perayaan", "Pendidikan"};

                //Pass array list di Alert dialog
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edtjnsacara.setText(options[which]);
                    }
                });
                // buat dan tampilkan alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    pemesanan();
                } else {
                    Toast.makeText(PemesananActivity.this, "Setujui Ketentuan dan Persyaratan yang ada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void init(){
        authdataa = new authdata(this);
        progressDialog = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);
        textView = findViewById(R.id.text_syaratdanketentuan);
        toolbar = findViewById(R.id.toolbarpemesanan);
        tglsewa = findViewById(R.id.edt_tglsewa_pemesanan);
        tglkembali = findViewById(R.id.edt_tglpengembalian_pemesanan);
        edtnama = findViewById(R.id.edt_nama_pemesanan);
        edtnohp = findViewById(R.id.edt_notelepon_pemesanan);
        edtemail = findViewById(R.id.edt_email_pemesanan);
        edtacara = findViewById(R.id.edt_acara_pemesanan);
        edtjnsacara = findViewById(R.id.edt_jenisacara_pemesanan);
        edtjmlpeserta = findViewById(R.id.edt_jmlpeserta_pemesanan);
        checkBox = findViewById(R.id.check_syaratketentuan_pemesanan);
        btnpesan = findViewById(R.id.btn_pemesanan);
        textInputLayout = findViewById(R.id.jmlpesertaLayout_pemesanan);

        id_pengguna = authdataa.getId_pengguna();
        edtnama.setText(authdataa.getNama());
        edtnama.setEnabled(false);
        edtnohp.setText(authdataa.getNomor_telepon());
        edtnohp.setEnabled(false);
        edtemail.setText(authdataa.getEmail());
        edtemail.setEnabled(false);
    }

    public void pemesanan(){
        final String nama = edtnama.getText().toString().trim();
        final String nohp = edtnohp.getText().toString().trim();
        final String email = edtemail.getText().toString().trim();
        final String namacara = edtacara.getText().toString().trim();
        final String jenisacara = edtjnsacara.getText().toString().trim();
        final String tglssewa = tglsewa.getText().toString().trim();
        final String tglskembali = tglkembali.getText().toString().trim();
        final String jumlahpst = edtjmlpeserta.getText().toString().trim();

        if (nama.matches("")){
            Toast.makeText(PemesananActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nohp.matches("")){
            Toast.makeText(PemesananActivity.this, "No Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.matches("")){
            Toast.makeText(PemesananActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (namacara.matches("")){
            Toast.makeText(PemesananActivity.this, "Nama Acara tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (jenisacara.matches("")){
            Toast.makeText(PemesananActivity.this, "Jenis Acara tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tglssewa.matches("")){
            Toast.makeText(PemesananActivity.this, "Tanggal Sewa tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tglskembali.matches("")){
            Toast.makeText(PemesananActivity.this, "Tanggal pengembalian tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (jumlahpst.matches("")){
            Toast.makeText(PemesananActivity.this, "Jumlah Peserta tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }
        if (jumlahpst.length() > 4) {
            textInputLayout.setError("Maksimal peserta 1000 orang");
            return;
        } else {
            textInputLayout.setError("");
        }

        progressDialog.setMessage("Loading");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_PEMESANAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean status = jsonObject.getBoolean("status");
                    String pesan = jsonObject.getString("pesan");
                    JSONObject data = jsonObject.getJSONObject("data");
                    String id_acara = data.getString("id_acara");
                    if (status){
                        Toast.makeText(PemesananActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent main = new Intent(PemesananActivity.this, PemesananFasilitasActivity.class);
                                main.putExtra("dataidacara", id_acara);
                                startActivity(main);
                                finish();
                            }
                        }, 1500);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(PemesananActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    Toast.makeText(PemesananActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PemesananActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama_acara", namacara);
                params.put("tanggal_mulai", tglssewa);
                params.put("tanggal_selesai", tglskembali);
                params.put("jumlah_peserta", jumlahpst);
                params.put("jenis_kegiatan", jenisacara);
                params.put("id_pengguna", id_pengguna);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            Intent abc = new Intent(PemesananActivity.this, BerandaActivity.class);
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