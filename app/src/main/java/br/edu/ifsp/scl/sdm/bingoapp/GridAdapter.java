package br.edu.ifsp.scl.sdm.bingoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private int icons[];
    private ArrayList<Integer> numeros;
    private Context context;
    private LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<Integer> numeros/*, int icons[]*/){

        this.context = context;
        this.numeros = numeros;
        //this.icons = icons;

    }

    @Override
    public int getCount() {
        return numeros.size();
    }

    @Override
    public Object getItem(int position) {
        return numeros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView = convertView;

        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.numero_view_layout, null);
        }

        //ImageView icon = (ImageView) gridView.findViewById(R.id.imgImageCustom);
        TextView numero = (TextView) gridView.findViewById(R.id.txtNumberCustom);

        //icon.setImageResource(icons[position]);
        numero.setText(numeros.get(position).toString());

        return gridView;
    }
}
