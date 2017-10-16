package com.example.juansevillano.testingproductos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juan Sevillano on 14/07/2017.
 */

public class ItemProductoAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Ingrediente> items;

    public ItemProductoAdapter(Activity activity, ArrayList<Ingrediente> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(items.get(position).getid_ingrediente());
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        View vi=contentView;

        if(contentView == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.list_item_layout, null);
        }

        Ingrediente item = items.get(position);

        ImageView image = (ImageView) vi.findViewById(R.id.imageIngrediente);
        int imageResource = activity.getResources()
                .getIdentifier(item.getruta_imagen(), null,
                        activity.getPackageName());
        image.setImageDrawable(activity.getResources().getDrawable(
                imageResource));

        TextView nombre = (TextView) vi.findViewById(R.id.texto_ingrediente);
        nombre.setText(item.getingrediente());

        TextView tipo = (TextView) vi.findViewById(R.id.texto_trastorno);
        tipo.setText(item.gettipo_ingrediente());

        TextView veracidad = (TextView) vi.findViewById(R.id.texto_verificado);
        veracidad.setText(item.getverificado());

        return vi;
    }
}
