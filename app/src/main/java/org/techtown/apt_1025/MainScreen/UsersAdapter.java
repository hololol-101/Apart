package org.techtown.apt_1025.MainScreen;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.techtown.apt_1025.R;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<org.techtown.apt_1025.MainScreen.UsersAdapter.CustomViewHolder> {

    private ArrayList<org.techtown.apt_1025.MainScreen.PersonalData> mList = null;
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<PersonalData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView id;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
        }
    }


    @Override
    public org.techtown.apt_1025.MainScreen.UsersAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        org.techtown.apt_1025.MainScreen.UsersAdapter.CustomViewHolder viewHolder = new org.techtown.apt_1025.MainScreen.UsersAdapter.CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull org.techtown.apt_1025.MainScreen.UsersAdapter.CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getMember_news_title());
        //viewholder.name.setText(mList.get(position).getMember_url());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}