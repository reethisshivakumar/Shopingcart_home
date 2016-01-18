package com.slidingmenusample2.slidingmenu2;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by rdm-09 on 02-Jan-16.
 */
public class ApiConnector {
    public JSONArray GetAllProducts(){
        String url="http://10.0.2.2/test/asdf.php";
        //String url="http://appyandroid.comxa.com/asdf.php";

        HttpEntity httpEntity=null;
        try
        {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            httpEntity= httpResponse.getEntity();

        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray=null;
        if(httpEntity!=null)
        {
            try {
                String entityresponse = EntityUtils.toString(httpEntity);
                Log.e("Entity response:",entityresponse);
                jsonArray = new JSONArray(entityresponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
    public JSONArray GetAllProductsDetails(String cid){
       String url="http://10.0.2.2/test/pdetails.php?id="+cid;
        //String url="http://appyandroid.comxa.com/pdetails.php?id="+cid;
        HttpEntity httpEntity=null;
        try
        {
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            httpEntity=httpResponse.getEntity();

        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray=null;
        if(httpEntity!=null)
        {
            try {
                String entityresponse = EntityUtils.toString(httpEntity);
                Log.e("Entity response:",entityresponse);
                jsonArray = new JSONArray(entityresponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}
