package com.dung.gedung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dung.gedung.Model.ModelJadwal;
import com.dung.gedung.Model.ModelUnit;
import com.dung.gedung.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUnit extends RecyclerView.Adapter<AdapterUnit.MyViewHolder>{
    private List<ModelUnit> item;
    private Context context;

    public AdapterUnit(Context context, List<ModelUnit> item){
        this.context = context;
        this.item = item;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelUnit me = item.get(position);
        Picasso.get().load(me.getGambar()).into(holder.gambar);
        holder.nama_unit.setText(me.getNama_unit());
        holder.deskripsi_unit.setText(me.getDeskripsi_unit());
    }

    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama_unit, deskripsi_unit;
        ImageView gambar;
        public MyViewHolder(View itemView){
            super(itemView);
            gambar = itemView.findViewById(R.id.gambar_unit);
            nama_unit = itemView.findViewById(R.id.nama_unit);
            deskripsi_unit = itemView.findViewById(R.id.deskripsi_unit);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_unit_kerja, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(layout);
        return myViewHolder;
    }
}
