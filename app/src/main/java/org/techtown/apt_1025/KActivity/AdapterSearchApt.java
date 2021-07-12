package org.techtown.apt_1025.KActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.techtown.apt_1025.KNetwork.SerialApartInfo;
import org.techtown.apt_1025.R;

import java.util.List;

public class AdapterSearchApt extends BaseAdapter {
    private Context context;
    private List<SerialApartInfo.SearchApartList> list;
    private LayoutInflater inflate;
    private ViewHolder viewHolder = new ViewHolder();

    public AdapterSearchApt(Context context, List<SerialApartInfo.SearchApartList> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.kang_apart_search_list,null);

            viewHolder = new ViewHolder();
            viewHolder.name =  convertView.findViewById(R.id.search_apart_list_name);
            viewHolder.addr =  convertView.findViewById(R.id.search_apart_list_addr);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        viewHolder.name.setText(list.get(position).apt_name);
        viewHolder.addr.setText(list.get(position).apt_location);

        return convertView;
    }
    static class ViewHolder{
        public TextView name;
        public TextView addr;

    }
}
