package com.dung.gedung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dung.gedung.Model.ModelPesanFasilitas;
import com.dung.gedung.R;

import java.util.List;

public class AdapterPesanFasilitas extends RecyclerView.Adapter<AdapterPesanFasilitas.MyViewHolder> {
    private List<ModelPesanFasilitas> item;
    private Context context;

    public AdapterPesanFasilitas(Context context, List<ModelPesanFasilitas> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pesan_fasilitas, parent, false);
        MyViewHolder myViewHolder = new AdapterPesanFasilitas.MyViewHolder(layout);
        return myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelPesanFasilitas me = item.get(position);
        holder.id_fasilitas.setText(me.getId_fasilitas());
        holder.nama_fasilitas.setText(me.getNama_fasilitas());
    }

    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id_fasilitas, nama_fasilitas;
        public MyViewHolder(View itemView){
            super(itemView);
            id_fasilitas = itemView.findViewById(R.id.kode_fasilitas_pesan);
            nama_fasilitas = itemView.findViewById(R.id.nama_fasilitas_pesan);
        }
    }
}
