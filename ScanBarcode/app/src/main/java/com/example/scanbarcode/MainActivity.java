package com.example.scanbarcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scanbarcode.ConfigFile.ServerApi;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    ZXingScannerView mScannerView;
    String Barcode;
//    Boolean doubleBackToExit;

    String admData, lineData, stationData, inoutData;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

//        Parsing data
        Intent intent = getIntent();
        admData = intent.getStringExtra("intent_admData");
        lineData = intent.getStringExtra("intent_lineData");
        stationData = intent.getStringExtra("intent_stationData");
        inoutData = intent.getStringExtra("intent_inoutData");

        Log.e("Hasil", admData);
        Log.e("Hasil", lineData);
        Log.e("Hasil", stationData);
        Log.e("Hasil", inoutData);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText() + " (" + rawResult.getBarcodeFormat().toString() + ')');
        builder.setCancelable(false);

        builder.setPositiveButton("Simpan",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // jika tombol diklik, maka akan menutup activity ini
                inputData();
//                Log.e("Hasil", admData);
//                Log.e("Hasil", lineData);
//                Log.e("Hasil", stationData);
//                Log.e("Hasil", inoutData);
            }
        });
        builder.setNegativeButton("Batal",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // jika tombol ini diklik, akan menutup dialog
                // dan tidak terjadi apa2
                dialog.cancel();
            }
        });

        Barcode = rawResult.getText() + rawResult.getBarcodeFormat().toString();
        Log.e("barcode", Barcode);
        //builder.setMessage(rawResult.getBarcodeFormat().toString());
        AlertDialog alert1 = builder.create();
        alert1.show();

        mScannerView.resumeCameraPreview(this);
    }

//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExit) {
//            Intent abc = new Intent(MainActivity.this, InputActivity.class);
//            startActivity(abc);
//            finish();
//        }
//
//        this.doubleBackToExit = true;
//        Toast.makeText(this, "Tekan sekali lagi untuk kembali", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExit=false;
//            }
//        }, 1000);
//    }

    public void inputData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DATASCAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String pesan = jsonObject.getString("pesan");
                    Toast.makeText(MainActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    Intent save = new Intent(MainActivity.this, InputActivity.class);
                    startActivity(save);
                    finish();
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Codingan Error !", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "No Data !", Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Adm_Name", admData);
                params.put("Line_No", lineData);
                params.put("Station_Name", stationData);
                params.put("IO_Name", inoutData);
                params.put("BarcodeNo", Barcode);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}