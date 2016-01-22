package com.slidingmenusample2.slidingmenu2;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by rdm-09 on 20-Jan-16.
 */
public class Cartadapter extends BaseAdapter {
    // private List<String> array;
    // private SQLiteDatabase db;
    private Cursor crs;
    private Activity activity;
    private static LayoutInflater Inflater=null;

    public Cartadapter(Cursor crs, Activity a)
    {
        //this.array = array;
        this.crs=crs;
        /// crs = db.rawQuery("SELECT * FROM cartcontents",null);
        this.crs.moveToFirst();
        this.activity=a;
        Inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return this.crs.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductCell cell;
        if(convertView==null)
        {
            convertView = Inflater.inflate(R.layout.get_all_product_list_view_cell,null);
            cell = new ProductCell();
            cell.cimageView = (ImageView)convertView.findViewById(R.id.cimageView);
            cell.pid=(TextView)convertView.findViewById(R.id.cpid);
            cell.pname=(TextView)convertView.findViewById(R.id.cpname);
            cell.pprice=(TextView)convertView.findViewById(R.id.cpprice);
            cell.pquantity=(TextView)convertView.findViewById(R.id.cquantity);
            cell.plus=(Button)convertView.findViewById(R.id.caddproduct);
            cell.minus=(Button)convertView.findViewById(R.id.cremoveproduct);
            convertView.setTag(cell);
        }
        else
        {
            cell = (ProductCell)convertView.getTag();
        }
        crs.moveToPosition(position);
        cell.pid.setText(crs.getString(crs.getColumnIndex("p_id")));
        cell.pname.setText(crs.getString(crs.getColumnIndex("p_name")));
        cell.pprice.setText(crs.getString(crs.getColumnIndex("p_price")));
        cell.pquantity.setText(crs.getString(crs.getColumnIndex("p_quantity")));
        // if (!crs.isAfterLast())
        //crs.moveToNext();
        String idofuser = crs.getString(crs.getColumnIndex("p_id"));
        String urlofimage = "http://10.0.2.2/test/getimage.php?id="+idofuser;
        //String urlofimage="http://appyandroid.comxa.com/getimage.php?id="+idofuser;
        Log.i("URL of image", urlofimage);
        new AsyncTask<String,Void,Bitmap>()
        {

            @Override
            protected Bitmap doInBackground(String... params) {
                String url=params[0];
                Bitmap icon=null;
                try {
                    InputStream in=new java.net.URL(url).openStream();
                    icon= BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return icon;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                //super.onPostExecute(bitmap);
                cell.cimageView.setImageBitmap(bitmap);
            }
        }.execute(urlofimage);

        return convertView;
    }
    private class ProductCell{
        private TextView pid,pname,pquantity,pprice;
        private ImageView cimageView;
        private Button plus,minus;
    }
}
