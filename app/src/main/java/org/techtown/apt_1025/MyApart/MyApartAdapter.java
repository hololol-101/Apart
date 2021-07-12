package org.techtown.apt_1025.MyApart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.techtown.apt_1025.KActivity.ChangeLikes;
import org.techtown.apt_1025.KActivity.DeleteStore;
import org.techtown.apt_1025.KActivity.DetailInfoActivity;
import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.R;

import java.util.List;

public class MyApartAdapter extends RecyclerView.Adapter<MyApartAdapter.ViewHolder> {
    private Context context;
    private List<SerialApartInfo.ApartRecommend> apartList;
    private int u_id;
    private OnItemClickListener onItemClickListener;
    View v;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }
    public MyApartAdapter(Context context, List<SerialApartInfo.ApartRecommend> apartList, int u_id) {
        this.context = context;
        this.apartList = apartList;
        this.u_id = u_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        MyApartAdapter.ViewHolder evh = new MyApartAdapter.ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SerialApartInfo.ApartRecommend d=apartList.get(position);
        Log.d("위치" , d.apt_location);
        holder.total_address.setText(d.apt_location);
        holder.apart_name.setText(d.apt_name);
        String ar = d.area;
        for(int i = 0; i<ar.length();i++){
            if(ar.charAt(i)==46){
                if((ar.length()-i)>2)
                    ar = ar.substring(0,i+3);
            }
        }
        holder.area.setText(ar+"m2");
        holder.year_built.setText(d.builtyear);
        if(d.scale.equals("0"))
            holder.scale.setText("알 수 없음");
        else
            holder.scale.setText(d.scale.split("\n")[1]);
        holder.a_id=d.a_id;
        holder.store=d.store;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return apartList.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        onItemClickListener=itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView total_address, area, scale,year_built,apart_name, deleteButton;
        int a_id, store;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            apart_name = (TextView) itemView.findViewById(R.id.apart_name);
            total_address=itemView.findViewById(R.id.total_address);
            area = (TextView)itemView.findViewById(R.id.area);
            scale= (TextView)itemView.findViewById(R.id.scale);
            year_built = (TextView) itemView.findViewById(R.id.year_built);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtra("u_id", u_id); intent.putExtra("a_id", a_id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onDeleteClick(position);
                        }
                    }
                }

            });
        }
    }
}