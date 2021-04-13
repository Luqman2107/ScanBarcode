package com.dung.gedung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dung.gedung.Model.ModelPemberitahuan;
import com.dung.gedung.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPemberitahuan extends RecyclerView.Adapter<AdapterPemberitahuan.MyViewHolder> {
    private List<ModelPemberitahuan> item;
    private Context context;
    private OnHistoryClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tgl_pemesanan, status, deskripsi;
        CircleImageView foto;

        public MyViewHolder(View itemView, final OnHistoryClickListener listener) {
            super(itemView);
            tgl_pemesanan = itemView.findViewById(R.id.text_tanggal_pemberitahuan);
            deskripsi = itemView.findViewById(R.id.deskripsi_status_pemberitahuan);
            status = itemView.findViewById(R.id.text_status_pemberitahuan);
            foto = itemView.findViewById(R.id.foto_status_pemberitahuan);

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

    public AdapterPemberitahuan(Context context, List<ModelPemberitahuan> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public AdapterPemberitahuan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pemberitahuan, parent, false);
        AdapterPemberitahuan.MyViewHolder myViewHolder = new AdapterPemberitahuan.MyViewHolder(layout, listener);
        return myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelPemberitahuan me = item.get(position);
        holder.tgl_pemesanan.setText(me.getTgl_pemesanan());
        holder.deskripsi.setText(me.getDeskripsi());
//        holder.status.setText(me.getStatus());
        if (me.getStatus().equals("1")) {
            holder.status.setText("Menunggu Verifikasi");
            holder.foto.setImageResource(R.drawable.iconstatusmenunggu);
        } else if (me.getStatus().equals("2")){
            holder.status.setText("Sudah di Verifikasi");
            holder.foto.setImageResource(R.drawable.iconstatusterima);
        } else if (me.getStatus().equals("3")) {
            holder.status.setText("Selesai");
            holder.foto.setImageResource(R.drawable.iconstatusselesai);
        } else if (me.getStatus().equals("4")) {
            holder.status.setText("Ditolak");
            holder.foto.setImageResource(R.drawable.iconstatustolak);
        }
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
