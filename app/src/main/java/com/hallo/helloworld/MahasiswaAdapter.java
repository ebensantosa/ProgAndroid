package com.hallo.helloworld;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hallo.HelloWorld.R;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.RecyclerViewHolder> {
    private static ArrayList<Mahasiswa> mMahasiswaList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView1;
        private TextView mTextView2;
        private TextView mTextView3;
        private Context context;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.noMhs);
            mTextView2 = itemView.findViewById(R.id.nimMhsTxt);
            mTextView3 = itemView.findViewById(R.id.nohpMhsTxt);
            context = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MahasiswaActivity.class);
                    String mhs = mMahasiswaList.get(getAdapterPosition()).getNim();
                    intent.putExtra("STATE", "Edit");
                    intent.putExtra("DOC", "mhs"+mhs);
                    context.startActivity(intent);
                }
            });
        }

        public TextView getNamaMahasiswa(){
            return mTextView1;
        }

        public TextView getNimMahasiswa(){
            return mTextView2;
        }

        public TextView getNohpMahasiswa(){
            return mTextView3;
        }
    }

    public MahasiswaAdapter(ArrayList<Mahasiswa> mhsList) {
        mMahasiswaList = mhsList;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.mahasiswa_item;
    }

    @NonNull
    @Override
    public MahasiswaAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MahasiswaAdapter.RecyclerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.RecyclerViewHolder holder, int position) {
        Mahasiswa mhs = mMahasiswaList.get(position);
        holder.getNamaMahasiswa().setText(mhs.getNama());
        holder.getNimMahasiswa().setText(mhs.getNim());
        holder.getNohpMahasiswa().setText(mhs.getPhone());
    }

    @Override
    public int getItemCount() {
        return this.mMahasiswaList.size();
    }

}