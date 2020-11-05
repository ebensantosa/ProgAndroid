package com.hallo.helloworld;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.HelloWorld.R;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    String[] titleFilm;
    String[] sinopsisFilm;
    int[] image;
    Context context;
    public RecyclerViewAdapter(Context ct, String[] film, String[] sinopsis, int[] img){
        context = ct;
        titleFilm = film;
        sinopsisFilm = sinopsis;
        image = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.barisfilm,parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(titleFilm[position]);
        holder.sinopsis.setText(sinopsisFilm[position]);
        holder.img.setImageResource(image[position]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.sinopsis.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.title.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    @Override
    public int getItemCount() {
        return titleFilm.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,sinopsis;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            sinopsis = itemView.findViewById(R.id.sinopsis);
            img = itemView.findViewById(R.id.filmIcon);

        }
    }
}
