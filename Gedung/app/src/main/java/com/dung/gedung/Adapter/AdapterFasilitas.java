package com.dung.gedung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dung.gedung.Model.ModelFasilitas;
import com.dung.gedung.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFasilitas extends RecyclerView.Adapter<AdapterFasilitas.MyViewHolder> {
    private List<ModelFasilitas> item;
    private Context context;
    private OnHistoryClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id_fasilitas, nama_fasilitas, keterangan;
        ImageView gambar_fasilitas;
        public MyViewHolder(View itemView, final OnHistoryClickListener listener) {
            super(itemView);
            id_fasilitas = itemView.findViewById(R.id.kode_fasilitas);
            nama_fasilitas = itemView.findViewById(R.id.text_nama_fasilitas);
            keterangan = itemView.findViewById(R.id.keterangan_fasilitas);
            gambar_fasilitas = itemView.findViewById(R.id.foto_fasilitas);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClick(position);
                        }
                    }
                }
            });
        }
    }

    public AdapterFasilitas(Context context, List<ModelFasilitas> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public AdapterFasilitas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fasilitas, parent, false);
        AdapterFasilitas.MyViewHolder myViewHolder = new AdapterFasilitas.MyViewHolder(layout, listener);
        return myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelFasilitas me = item.get(position);
        holder.id_fasilitas.setText(me.getId_fasilitas());
        holder.nama_fasilitas.setText(me.getNama_fasilitas());
        holder.keterangan.setText(me.getKeterangan());
        Picasso.get().load(me.getGambar_fasilitas()).into(holder.gambar_fasilitas);
//        holder.foto.setImageResource(R.drawable.ic_chevron_right_primary);
    }

    public int getItemCount() {
        return item.size();
    }

    public interface OnHistoryClickListener {
        public void onClick(int position);
    }

    public void setListener(OnHistoryClickListener listener) {
        this.listener = listener;
    }
}
