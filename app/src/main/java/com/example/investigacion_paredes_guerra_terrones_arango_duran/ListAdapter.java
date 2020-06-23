package com.example.investigacion_paredes_guerra_terrones_arango_duran;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ScanResult> wifiList;

    public ListAdapter(Context context, List<ScanResult> wifiList){

        this.context=context;
        this.wifiList=wifiList;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wifiList.size();
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
        View view=convertView;

        if(view==null){

            view=inflater.inflate(R.layout.listview_item, null);
            holder=new Holder();
            holder.tvDetails=(TextView)view.findViewById(R.id.txtWifi);
            view.setTag(holder);
        }else{
            holder=(Holder)view.getTag();
        }

        holder.tvDetails.setText(wifiList.get(position).SSID);
        return view;
    }

    class Holder{
        TextView tvDetails;
    }
}
