package com.slidingmenusample2.slidingmenu2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static SQLiteDatabase db  ;
    private Cursor crs;
    ListView GetAllProductListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.GetAllProductListView = (ListView)findViewById(R.id.GetAllProductListView);
       /* Cursor crs = db.rawQuery("SELECT p_id FROM cartcontents", null);
        List<String> array = new ArrayList<>();
        while (crs.moveToNext())
        {
            String pid = crs.getString(crs.getColumnIndex("p_id"));
            array.add(pid);
        }*/
        db=openOrCreateDatabase("appDB.db", Context.MODE_PRIVATE, null);
        crs = db.rawQuery("SELECT * FROM cartcontents;", null);
        this.GetAllProductListView.setAdapter(new Cartadapter(crs,this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        db=openOrCreateDatabase("appDB.db", Context.MODE_PRIVATE, null);
        Log.i("DB", "DB created for local use cartactivity");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
