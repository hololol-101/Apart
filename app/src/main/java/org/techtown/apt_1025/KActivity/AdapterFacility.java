package org.techtown.apt_1025.KActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.R;

import java.util.ArrayList;

public class AdapterFacility extends RecyclerView.Adapter<AdapterFacility.ViewHolder>{
    private Context context;
    private View v;
    ArrayList<SerialApartInfo.ApartFacility> arrayList;


    public AdapterFacility(Context context, ArrayList<SerialApartInfo.ApartFacility> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kang_facility_list, parent, false);
        AdapterFacility.ViewHolder evh = new AdapterFacility.ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dis = arrayList.get(position).distance;
        for(int i = 0; i<dis.length();i++){
            if(dis.charAt(i)==46) {
                dis = dis.substring(0, i);
                break;
            }
        }
        holder.tv_facility__distance_list.setText(dis+"m");
        holder.tv_facility_list.setText(arrayList.get(position).facility);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_facility_list,tv_facility__distance_list;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_facility_list=itemView.findViewById(R.id.tv_facility_list);
            tv_facility__distance_list=itemView.findViewById(R.id.tv_facility__distance_list);

        }
    }
}
