package com.slidingmenusample2.slidingmenu2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductdetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductdetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductdetailsFragment extends Fragment {
    TextView productid,productname,productdesc,productprice;
    ImageView pimage;
    public String Cproduct_id;
    View myview;
    private GridView GetAllProductListView;
    private JSONArray jsonArray;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductdetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductdetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductdetailsFragment newInstance(String param1, String param2) {
        ProductdetailsFragment fragment = new ProductdetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Cproduct_id = getArguments().getString("Cproduct_id");
        myview=inflater.inflate(R.layout.fragment_productdetails,container,false);
        this.productid =(TextView)myview.findViewById(R.id.textView);
        this.productname=(TextView)myview.findViewById(R.id.textView2);
        this.productdesc=(TextView)myview.findViewById(R.id.textView3);
        this.productprice =(TextView)myview.findViewById(R.id.textView4);
        this.pimage=(ImageView)myview.findViewById(R.id.imageView);
        if (Cproduct_id!=null)
        {
            new  Getproductdetails().execute(new ApiConnector());
        }


        return myview;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private class Getproductdetails extends AsyncTask<ApiConnector,Long,JSONArray>
    {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            return params[0].GetAllProductsDetails(Cproduct_id);
        }

        @Override
        protected  void onPostExecute(JSONArray jsonArray){
            try
            {
                JSONObject product = jsonArray.getJSONObject(0);
                productid.setText(product.getString("product_id"));
                productname.setText(product.getString("product_name"));
                productdesc.setText(product.getString("product_desc"));
                productprice.setText(product.getString("product_price"));
                //pimage.setImageResource(Integer.parseInt(product.getString("pimage")));

                String idofuser =  product.getString("product_id");
                String urlofimage = "http://10.0.2.2/test/details.php?id="+idofuser;
                //String urlofimage="http://appyandroid.comxa.com/details.php?id="+idofuser;
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
                        pimage.setImageBitmap(bitmap);
                    }
                }.execute(urlofimage);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
