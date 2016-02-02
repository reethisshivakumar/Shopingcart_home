package com.slidingmenusample2.slidingmenu2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by rdm-09 on 02-Jan-16.
 */
public class GetAllProductListViewAdapter extends BaseAdapter {

    //public int tot;
   // public SQLiteDatabase db ;

    private JSONArray dataarray;
    private Activity activity;
    private static LayoutInflater Inflater=null;
    public GetAllProductListViewAdapter(JSONArray jsonArray,Activity a){
        this.dataarray=jsonArray;
        this.activity=a;
        Inflater = (LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return this.dataarray.length();
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ProductCell cell;
        if(convertView==null)
        {
            convertView = Inflater.inflate(R.layout.gridcell,null);
            cell = new ProductCell();
            cell.pid=(TextView)convertView.findViewById(R.id.vpid);
            cell.pname=(TextView)convertView.findViewById(R.id.vpname);
            cell.pdesc=(TextView)convertView.findViewById(R.id.vpdesc);
            cell.pprice=(TextView)convertView.findViewById(R.id.vpprice);
            cell.prodimg=(ImageView)convertView.findViewById(R.id.vimageView);
            cell.addcart = (Button)convertView.findViewById(R.id.addproduct);
            cell.removecart = (Button)convertView.findViewById(R.id.removeprod);
            cell.count=0;

          //  cell.addcart.setTag(position+1,cell.pid);

            convertView.setTag(cell);
        }
        else
        {
            cell = (ProductCell)convertView.getTag();
        }
        try{
            final JSONObject jsonObject = this.dataarray.getJSONObject(position);
            cell.pid.setText(jsonObject.getString("product_id"));
            cell.pname.setText(jsonObject.getString("product_name"));
            cell.pdesc.setText(jsonObject.getString("product_desc"));
            cell.pprice.setText(jsonObject.getString("product_price"));
            cell.tot = jsonObject.getInt("product_price");
            cell.addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ProductCell holder = (ProductCell)((View)v.getParent()).getTag();
                   // if (cell.count!=0){

                    if (cell.count ==0)
                    {
                        //String query = null;
                        try {
                           // query = "INSERT INTO cartcontents(p_id,p_name,p_price,p_quantity) VALUES('"+jsonObject.getString("product_id")+"','"+jsonObject.getString("product_name")+"','"+jsonObject.getString("product_price")+"',0);";
                            ContentValues contentValues  = new ContentValues();
                            contentValues.put("p_id",jsonObject.getString("product_id"));
                            contentValues.put("p_name",jsonObject.getString("product_name"));
                            contentValues.put("p_price",jsonObject.getString("product_price"));
                            contentValues.put("p_quantity",0);
                            MyActivity.db.insertWithOnConflict("cartcontents","p_id", contentValues, SQLiteDatabase.CONFLICT_REPLACE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       //MyActivity.db.execSQL(query);
                    }
                    cell.count = cell.count+1;
                    ContentValues values = new ContentValues();
                    values.put("p_quantity",cell.count);
                    try {
                        String where = "p_id= ?";
                        String[] whereargs = new String[]{jsonObject.getString("product_id")};
                        MyActivity.db.update("cartcontents", values, where, whereargs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cell.addcart.setBackgroundResource(R.mipmap.adderback);
                    cell.addcart.setText("+" + cell.count);
                    cell.removecart.setVisibility(View.VISIBLE);
                        Carthelper.itemsCount = Carthelper.itemsCount+1;
                        Carthelper.grandTotal = Carthelper.grandTotal+ cell.tot ;
                                ((GridView) parent).performItemClick(v, position, 0);
                   /* ((MyActivity)getActivity()).findViewById(R.id.cartstatus);
                    cartstatus =(TextView)convertView.findViewById(R.id.cartstatus);*/

                   // ((MyActivity)getActivity()).changeToolBarText();
                       // cartstatus = (TextView)convertView.findViewById(R.id.cartstatus);
                   // }
                }
            });
            /*if (cell.count!=0)
            {*/

                cell.removecart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cell.count = cell.count - 1;
                        if (cell.count<=0) {
                            cell.removecart.setVisibility(View.INVISIBLE);
                            cell.addcart.setText("");
                            cell.addcart.setBackgroundResource(R.mipmap.adder);
                            try {
                                String where = "p_id= ?";
                                String[] whereargs = new String[]{jsonObject.getString("product_id")};
                                MyActivity.db.delete("cartcontents", where, whereargs);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            cell.addcart.setText("+" + cell.count);
                            ContentValues values = new ContentValues();
                            values.put("p_quantity",cell.count);
                            try {
                                String where = "p_id= ?";
                                String[] whereargs = new String[]{jsonObject.getString("product_id")};
                                MyActivity.db.update("cartcontents", values, where, whereargs);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Carthelper.itemsCount = Carthelper.itemsCount-1;
                        Carthelper.grandTotal = Carthelper.grandTotal- cell.tot;
                        ((GridView) parent).performItemClick(v, position, 0);
                    }
                });
           // }


            //cell.addcart.setTag(position+1, jsonObject.getString("product_id"));
            //cell.prodimg.setImageResource(R.mipmap.ic_launcher);
            String idofuser = jsonObject.getString("product_id");
            String urlofimage = "http://10.0.2.2/test/getimage.php?id="+idofuser;
            //String urlofimage="http://appyandroid.comxa.com/getimage.php?id="+idofuser;
            Log.i("URL of image",urlofimage);
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
                    cell.prodimg.setImageBitmap(bitmap);
                }
            }.execute(urlofimage);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return convertView;
    }
    private class ProductCell{
        private TextView pid,pname,pdesc,pprice;
        private ImageView prodimg;
        private Button addcart,removecart;
        private int count;
        private int tot;

    }
}
