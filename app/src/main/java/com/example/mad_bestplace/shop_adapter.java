package com.example.mad_bestplace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

public class shop_adapter extends BaseAdapter {


    private Context context;
    private ArrayList<shops> shoplist;


    public shop_adapter(Context context, ArrayList<shops> shoplist) {
        this.context = context;
        this.shoplist = shoplist;
    }

    @Override
    public int getCount() {
        return shoplist.size();
    }

    @Override
    public Object getItem(int position) {
        return shoplist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{

        ImageView shop_img;
        TextView sh_name,sh_tel,sh_address,sh_dis;

    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.feed_items,null);

            holder.sh_name = (TextView) row.findViewById(R.id.shop_name);
            holder.sh_address = (TextView) row.findViewById(R.id.shop_address);
            holder.sh_tel = (TextView) row.findViewById(R.id.shop_dis);
            holder.shop_img = (ImageView) row.findViewById(R.id.shopimage);
            holder.sh_dis = (TextView) row.findViewById(R.id.dis);

        shops shop = shoplist.get(position);
        holder.sh_name.setText(shop.getSname());
        holder.sh_address.setText(shop.getSaddress());
        holder.sh_tel.setText(shop.getScompany());
        holder.sh_dis.setText(shop.getSdis());
        byte[] sh_image = shop.getSimage();
        Bitmap bit = BitmapFactory.decodeByteArray(sh_image,0,sh_image.length);
        holder.shop_img.setImageBitmap(bit);


        return row;
    }
}
