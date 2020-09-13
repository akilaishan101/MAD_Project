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

public class food_adapter extends BaseAdapter {



    private Context context;
    private ArrayList<foods> foodlist;


    public food_adapter(Context context, ArrayList<foods> foodlist) {
        this.context = context;
        this.foodlist = foodlist;
    }


    @Override
    public int getCount() {
        return foodlist.size();
    }

    @Override
    public Object getItem(int position) {
        return foodlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder{

        ImageView food_img;
        TextView f_name,f_id,f_price,fs_id;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder hold = new ViewHolder();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.food_item,null);

        hold.f_id = (TextView) row.findViewById(R.id.fid);
        hold.f_price = (TextView) row.findViewById(R.id.fprice);
        hold.food_img = (ImageView) row.findViewById(R.id.foodimg);
        hold.f_name = (TextView) row.findViewById(R.id.fname);

        foods food = foodlist.get(position);
        hold.f_id.setText(food.getFid());
        hold.f_name.setText(food.getFname());
        hold.f_price.setText(food.getFprice());
        byte[] f_image = food.getFimage();
        Bitmap bit = BitmapFactory.decodeByteArray(f_image,0,f_image.length);
        hold.food_img.setImageBitmap(bit);


        return row;

    }
}
