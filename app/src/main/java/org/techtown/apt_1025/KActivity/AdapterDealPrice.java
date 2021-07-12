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

public class AdapterDealPrice extends RecyclerView.Adapter<AdapterDealPrice.ViewHolder> {
    private Context context;
    ArrayList<SerialApartInfo.ApartPrice> arrayList;
    View v;
    public AdapterDealPrice(Context applicationContext, ArrayList<SerialApartInfo.ApartPrice> priceArrayList) {
        context=applicationContext;
        arrayList=priceArrayList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kang_deal_price_list, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String pri = String.valueOf(arrayList.get(position).price);
        pri = pri.substring(0,pri.length()-2);
        pri = pri.substring(0,pri.length()-3)+","+pri.substring(pri.length()-3,pri.length());
        holder.tv_deal_price_list.setText(pri+" 만 원");
        String year, month, date;
        date=arrayList.get(position).upload_day;
        year=date.substring(0, 4);
        month=date.substring(4);
        holder.tv_deal_year_list.setText(year+"년 "+month+"월");
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_deal_year_list;
        TextView tv_deal_price_list;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_deal_year_list=itemView.findViewById(R.id.tv_deal_year_list);
            tv_deal_price_list=itemView.findViewById(R.id.tv_deal_price_list);
        }
    }
}
