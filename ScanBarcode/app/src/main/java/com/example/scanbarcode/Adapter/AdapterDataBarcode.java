package com.example.scanbarcode.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanbarcode.Model.ModelDataBarcode;
import com.example.scanbarcode.R;

import java.util.List;

public class AdapterDataBarcode extends RecyclerView.Adapter<AdapterDataBarcode.MyViewHolder>{
    private List<ModelDataBarcode> item;
    private Context context;

    public AdapterDataBarcode(Context context, List<ModelDataBarcode> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_datainput, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(layout);
        return myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelDataBarcode me = item.get(position);
        holder.BarcodeNo.setText(me.getBarcodeNo());
        holder.ScanDate.setText(me.getScanDate());
    }

    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView BarcodeNo, ScanDate;
        public MyViewHolder(View itemView){
            super(itemView);
            BarcodeNo = itemView.findViewById(R.id.text_barcode);
            ScanDate = itemView.findViewById(R.id.text_tglbarcode);
        }
    }
}
