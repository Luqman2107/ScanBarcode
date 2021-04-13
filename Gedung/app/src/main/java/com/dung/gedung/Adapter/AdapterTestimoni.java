package com.dung.gedung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dung.gedung.Model.ModelTestimoni;
import com.dung.gedung.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTestimoni extends RecyclerView.Adapter<AdapterTestimoni.MyViewHolder>{
    private List<ModelTestimoni> item;
    private Context context;

    public AdapterTestimoni(Context context, List<ModelTestimoni> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_testimoni, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(layout);
        return myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelTestimoni me = item.get(position);
        holder.nama.setText(me.getNama_testimoni());
        holder.deskripsi.setText(me.getDeskripsi());
        Picasso.get().load(me.getFoto_testimoni()).into(holder.foto);
    }

    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama, deskripsi;
        CircleImageView foto;
        public MyViewHolder(View itemView){
            super(itemView);
            nama = itemView.findViewById(R.id.text_nama_testimoni);
            deskripsi = itemView.findViewById(R.id.text_deskripsi_testimoni);
            foto = itemView.findViewById(R.id.fotouser_testimoni);
        }
    }
}
