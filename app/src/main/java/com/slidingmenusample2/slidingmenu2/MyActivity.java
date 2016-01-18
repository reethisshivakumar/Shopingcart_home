package com.slidingmenusample2.slidingmenu2;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URI;

public class MyActivity extends AppCompatActivity
        implements VegiesFragment.OnFragmentInteractionListener,DairyproductsFragment.OnFragmentInteractionListener, ProductdetailsFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {
   // Fragment fragment=null;
    Toolbar toolbar;
    TextView cartstatus;
    private VegiesFragment f1;
    private DairyproductsFragment f2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

      /*  fragment = new VegiesFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
*/
        f1 = new VegiesFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, f1).commit();
        getSupportFragmentManager().beginTransaction().show(f1).commit();
        f2 = new DairyproductsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, f2).commit();



         toolbar = (Toolbar) findViewById(R.id.toolbar);
         cartstatus  = (TextView)toolbar.findViewById(R.id.cartstatus);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(Carthelper.itemsCount!=0)
        {

        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_vegies) {
            // Handle the camera action
            //fragment = new VegiesFragment();
            getSupportFragmentManager().beginTransaction().show(f1).commit();
            getSupportFragmentManager().beginTransaction().hide(f2).commit();
        } else if (id == R.id.nav_dairyprod) {
            //fragment = new DairyproductsFragment();
            getSupportFragmentManager().beginTransaction().show(f2).commit();
            getSupportFragmentManager().beginTransaction().hide(f1).commit();
        } else if (id == R.id.nav_grocery) {

        } else if (id == R.id.nav_readycook) {

        } else if (id == R.id.nav_snacks) {

        } else if (id == R.id.nav_beverages) {
        }
        else if (id == R.id.nav_household) {
        }
        else if (id == R.id.nav_personalcare) {
        }
        else if (id == R.id.nav_logout) {
        }
        else if (id == R.id.nav_aboutus) {
        }
        else if (id == R.id.nav_contactus) {
        }
       /* if (fragment!=null)
        {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void changeToolBarText(String txt){
        cartstatus.setText(txt);
    }

}
