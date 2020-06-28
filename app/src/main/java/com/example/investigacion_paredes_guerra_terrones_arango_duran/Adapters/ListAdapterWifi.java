package com.example.investigacion_paredes_guerra_terrones_arango_duran.Adapters;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.investigacion_paredes_guerra_terrones_arango_duran.R;

import java.util.List;

public class ListAdapterWifi extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<ScanResult> wifi_list;

    public ListAdapterWifi(Context context, List<ScanResult> wifi_list) {
        this.context = context;
        this.wifi_list = wifi_list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wifi_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View view = convertView;

        if(view == null){
            view = inflater.inflate(R.layout.listview_wifi,null);
            holder = new Holder();

            holder.wDetails = (TextView)view.findViewById(R.id.txt_WifiName);
            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }
        return null;
    }

    class Holder{
        TextView wDetails;
    }
}
