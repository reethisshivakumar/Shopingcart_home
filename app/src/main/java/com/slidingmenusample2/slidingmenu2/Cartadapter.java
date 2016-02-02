package com.slidingmenusample2.slidingmenu2;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rdm-09 on 20-Jan-16.
 */
public class Cartadapter extends BaseAdapter {
    private ArrayList<Cartdata> clist;
    private Activity activity;
    private static LayoutInflater Inflater=null;

    public Cartadapter(Activity a,ArrayList<Cartdata> list)
    {
        this.activity=a;
        this.clist=list;
        Inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
       return this.clist.size();
    }

    @Override
    public Cartdata getItem(int position) {
        return clist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         final ProductCell cell;
        if(convertView==null)
        {
            convertView = Inflater.inflate(R.layout.get_all_product_list_view_cell,parent,false);
            cell = new ProductCell(convertView);
            convertView.setTag(cell);
            //cell.minus.setTag(cell);
        }
        else
        {
            cell=(ProductCell)convertView.getTag();
        }
        final Cartdata currentdata = getItem(position);
        cell.pid.setText(currentdata.cid);
        cell.pname.setText(currentdata.cname);
        //cell.pimageView.setImageBitmap();
        cell.pprice.setText(currentdata.cost+"");
        cell.pquantity.setText(currentdata.cquantity+"");
        String idofuser = currentdata.imageid;//crs.getString(crs.getColumnIndex("p_id"));
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
               cell.pimageView.setImageBitmap(bitmap);
            }
        }.execute(urlofimage);

        cell.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = Integer.parseInt(cell.pquantity.getText().toString());
                quant=quant+1;
                cell.pquantity.setText(quant+"");
                ContentValues values = new ContentValues();
                values.put("p_quantity",quant);
                String where = "p_id= ?";
                String[] whereargs = new String[]{cell.pid.getText().toString()};
                CartActivity.db.update("cartcontents", values, where, whereargs);
            }
        });
        cell.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant = Integer.parseInt(cell.pquantity.getText().toString());
                quant=quant-1;
                if(quant>0) {
                    cell.pquantity.setText(quant + "");
                    ContentValues values = new ContentValues();
                    values.put("p_quantity", quant);
                    String where = "p_id= ?";
                    String[] whereargs = new String[]{cell.pid.getText().toString()};
                    CartActivity.db.update("cartcontents", values, where, whereargs);
                    //crs = CartActivity.db.rawQuery("SELECT * FROM cartcontents;", null);

                }
                else
                {
                    /*v.setVisibility(View.GONE);
                    notifyDataSetChanged();*/
                    clist.remove(position);
                    notifyDataSetChanged();
                    String where = "p_id= ?";
                    String[] whereargs = new String[]{cell.pid.getText().toString()};
                    CartActivity.db.delete("cartcontents", where, whereargs);
                    // int positionToRemove = (int)v.getTag(); //get the position of the view to delete stored in the tag
                   /* crs.remove(positionToRemove);
                    removeItem(positionToRemove);*/
                    //crs = CartActivity.db.rawQuery("SELECT * FROM cartcontents;", null);
                }
            }
        });

        return convertView;
    }
    private class ProductCell{
        private TextView pid,pname,pquantity,pprice;
        private ImageView pimageView;
        private Button plus,minus;
        public ProductCell(View item)
        {
            pid = (TextView)item.findViewById(R.id.cpid);
            pname=(TextView)item.findViewById(R.id.cpname);
            pquantity=(TextView)item.findViewById(R.id.cquantity);
            pprice = (TextView)item.findViewById(R.id.cpprice);
            pimageView= (ImageView) item.findViewById(R.id.cimageView);
            plus=(Button)item.findViewById(R.id.caddproduct);
            minus=(Button)item.findViewById(R.id.cremoveproduct);
        }
    }
    // private List<String> array;
    // private SQLiteDatabase db;
 /*   private Cursor crs;
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
        cell.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
    }*/
}
