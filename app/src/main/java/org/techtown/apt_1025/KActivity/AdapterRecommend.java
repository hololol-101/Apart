package org.techtown.apt_1025.KActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdapterRecommend extends RecyclerView.Adapter<AdapterRecommend.ViewHolder>{
    private Context context;
    private View v;
    private int u_id;
    ArrayList<SerialApartInfo.ApartRecommend> arrayList;
    public AdapterRecommend(Context context, ArrayList<SerialApartInfo.ApartRecommend> arrayList, int u_id) {
        this.context = context;
        this.arrayList=arrayList;
        this.u_id=u_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kang_apart_summary_list, parent, false);
        AdapterRecommend.ViewHolder evh = new AdapterRecommend.ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SerialApartInfo.ApartRecommend d=arrayList.get(position);
        holder.tv_sum_apart_name.setText(d.apt_name);
        holder.tv_sum_location.setText(d.apt_location);
        holder.tv_sum_built_year.setText(d.builtyear);
        String pri = String.valueOf(d.lastPrice);
        pri = pri.substring(0,pri.length()-2);
        pri = pri.substring(0,pri.length()-3)+","+pri.substring(pri.length()-3,pri.length());
        holder.tv_sum_deal_price.setText(pri+" 만 원");
        holder.a_id=d.a_id;
        holder.store=d.store;
        holder.tv_sum_area.setText(String.valueOf(d.area));
        try {
            holder.btn.setChecked(new IsChecked().execute(holder.a_id, u_id).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sum_apart_name, tv_sum_location, tv_sum_built_year, tv_sum_deal_price, tv_sum_area;
        ToggleButton btn;
        int a_id, store;
        boolean b;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sum_apart_name = itemView.findViewById(R.id.tv_sum_apart_name);
            tv_sum_location = itemView.findViewById(R.id.tv_sum_location);
            tv_sum_built_year = itemView.findViewById(R.id.tv_sum_built_year);
            tv_sum_deal_price = itemView.findViewById(R.id.tv_sum_deal_price);
            tv_sum_area = itemView.findViewById(R.id.tv_sum_area);
            btn = itemView.findViewById(R.id.btn_sum_favorite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailInfoActivity.class);
                    intent.putExtra("a_id", a_id);
                    intent.putExtra("u_id", u_id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);

                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn.isChecked() == true) {
                        store++;
                        new ChangeLikes().execute(String.valueOf(a_id), String.valueOf(store));
                        new InsertStore().execute(String.valueOf(a_id), String.valueOf(u_id), tv_sum_location.getText().toString());
                    } else {
                        store--;
                        new ChangeLikes().execute(String.valueOf(a_id), String.valueOf(store));
                        new DeleteStore().execute(String.valueOf(a_id), String.valueOf(u_id), tv_sum_location.getText().toString());
                    }
                }
            });

        }
    }
}
