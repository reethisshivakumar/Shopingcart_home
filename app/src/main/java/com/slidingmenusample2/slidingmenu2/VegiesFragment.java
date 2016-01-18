package com.slidingmenusample2.slidingmenu2;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RemoteViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VegiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VegiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VegiesFragment extends Fragment {
    View myview;
    Button aprod;
    TextView cartstatus;
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

    public VegiesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VegiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VegiesFragment newInstance(String param1, String param2) {
        VegiesFragment fragment = new VegiesFragment();
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
        myview=inflater.inflate(R.layout.fragment_vegies, container, false);
        this.GetAllProductListView=(GridView)myview.findViewById(R.id.mygrid);
        new GetAllProductTask().execute(new ApiConnector());

        /*aprod = (Button)myview.findViewById(R.id.addproduct);
        aprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), v.getTag().toString(), Toast.LENGTH_LONG);
            }
        });*/
       /* this.GetAllProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tagname = view.getTag(position).toString();
                Toast.makeText(getActivity(),tagname,Toast.LENGTH_LONG);
            }
        });*/

        this.GetAllProductListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*try {
                    JSONObject productClicked = jsonArray.getJSONObject(position);
                    Intent showdetails = new Intent(getActivity(), Productdetailsfragment.class);
                    showdetails.putExtra("Cproduct_id", productClicked.getString("product_id"));
                    startActivity(showdetails);
                    ProductdetailsFragment pdf = new ProductdetailsFragment();
                    Bundle args = new Bundle();
                    args.putString("Cproduct_id", productClicked.getString("product_id"));
                    pdf.setArguments(args);
                    getFragmentManager().beginTransaction().add(R.id.container, pdf).commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                long viewid= view.getId();
                switch (view.getId())
                {
                    case R.id.addproduct:
                       // cartstatus = (TextView)myview.findViewById(R.id.cartstatus);

                        Carthelper.itemsCount = Carthelper.itemsCount+1;
                        ((MyActivity)getActivity()).changeToolBarText(Carthelper.itemsCount+"");
                        //cartstatus.setText(Carthelper.itemsCount+"");
                        //Toast.makeText(getActivity(),"Grid number "+position+" add to cart clicked",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.removeprod:
                        cartstatus = (TextView)myview.findViewById(R.id.cartstatus);
                        Carthelper.itemsCount = Carthelper.itemsCount-1;
                        if (Carthelper.itemsCount==0)
                        {
                            cartstatus.setText("");
                        }
                        else {
                            cartstatus.setText(Carthelper.itemsCount+"");
                        }
                        //Toast.makeText(getActivity(),"Grid number "+position+" remove from cart clicked",Toast.LENGTH_LONG).show();
                        break;
                }
               /* if (viewid==R.id.addproduct)
                {
                   // int a=0;
                    //view.findViewById(R.id.addproduct)
                  //  RemoteViews remoteViews = new RemoteViews(getActivity(),R.layout.fragment_vegies);
                    Toast.makeText(getActivity(),"Grid number "+position+" add to cart clicked",Toast.LENGTH_LONG).show();
                }*/
            }
        });
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
    public void setListAdapter(JSONArray jsonArray){
        this.jsonArray = jsonArray;
        this.GetAllProductListView.setAdapter(new GetAllProductListViewAdapter(jsonArray,getActivity()));
    }
    private class GetAllProductTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].GetAllProducts();
        }

        @Override
        protected  void onPostExecute(JSONArray jsonArray){
            setListAdapter(jsonArray);
        }
    }
}
