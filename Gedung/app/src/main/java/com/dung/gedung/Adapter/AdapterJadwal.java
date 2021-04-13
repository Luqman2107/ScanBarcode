package com.dung.gedung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dung.gedung.Model.ModelJadwal;
import com.dung.gedung.R;

import java.util.List;

public class AdapterJadwal extends RecyclerView.Adapter<AdapterJadwal.MyViewHolder>{
    private List<ModelJadwal> item;
    private Context context;

    public AdapterJadwal(Context context, List<ModelJadwal> item){
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jadwal, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(layout);
        return myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelJadwal me = item.get(position);
        holder.status.setText(me.getStatus());
        if (me.getTanggal_mulai().equals("1")) {
            holder.tgl_sewa.setText("Disewa");
        }
    }

    public int getItemCount() {
        return item.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView status, tgl_sewa;
        public MyViewHolder(View itemView){
            super(itemView);
            status = itemView.findViewById(R.id.text_status_jadwal);
            tgl_sewa = itemView.findViewById(R.id.text_tanggal_jadwal);
        }
    }
}
