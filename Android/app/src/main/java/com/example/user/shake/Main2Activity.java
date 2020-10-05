package com.example.user.shake;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;


    ListView listView = null;
    private String userName,userID;
    TextView navTitle;
    TextView navContext;
    public static Context mContext;
    private ArrayList<BikeInfo> bikeList;
    private int markerClickFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Save User Information
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        //Toast.makeText(getApplicationContext(),userName,Toast.LENGTH_SHORT).show();
        mContext=this;

        navTitle = findViewById(R.id.textNavTitle);
        navContext = findViewById(R.id.textNavContext);



        //navTitle.setText(userID);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.mapMain);
        mapFragment.getMapAsync(this);

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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
        getMenuInflater().inflate(R.menu.main2, menu);
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

        if (id == R.id.itemRent) {
            Intent intent = new Intent(Main2Activity.this, RentActivity.class);
            Main2Activity.this.startActivity(intent);
        } else if (id == R.id.itemRegister) {
            Intent intent2 = new Intent(Main2Activity.this, BikeRegisterActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }
        else if (id == R.id.itemInfo) {
            Intent intent2 = new Intent(Main2Activity.this, InfoActivity.class);
            intent2.putExtra("userId", userID);
            Main2Activity.this.startActivity(intent2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        PhpConnect task = new PhpConnect();
        ArrayList<String> bikeLatLng = new ArrayList<>();
        bikeList = new ArrayList<>();
        MarkerOptions markerOptions = new MarkerOptions();
        int bikeCost = 0;
        double bikeLatitude = 0, bikeLongitude = 0;
        String bikeOwner = "", bikeType = "", bikeImgUrl = "", bikeCode = "";
        String bikeLockId = "", bikeModelName = "", bikeAddInfo = "";

        mMap = map;

        try {
            bikeLatLng = task.execute("http://13.125.229.179/getBikeInfo.php").get();
        }catch (InterruptedException e){
            //bikeLatLng = "fail connect";
            e.printStackTrace();
        }catch (ExecutionException e){
            //bikeLatLng = "fail connect";
            e.printStackTrace();
        }

        for (int i = 0; i < bikeLatLng.size(); i += 10){
            bikeOwner = bikeLatLng.get(i);
            bikeCode = bikeLatLng.get(i + 1);
            bikeLatitude = Double.parseDouble(bikeLatLng.get(i + 2));
            bikeLongitude = Double.parseDouble(bikeLatLng.get(i + 3));
            bikeCost = Integer.parseInt(bikeLatLng.get(i + 4));
            bikeImgUrl = bikeLatLng.get(i + 5);
            bikeLockId = bikeLatLng.get(i + 6);
            bikeModelName = bikeLatLng.get(i + 7);
            bikeType = bikeLatLng.get(i + 8);
            bikeAddInfo = bikeLatLng.get(i + 9);

            BikeInfo bike = new BikeInfo(bikeOwner, bikeCode, bikeLatitude, bikeLongitude, bikeCost, bikeImgUrl, bikeLockId, bikeModelName, bikeType, bikeAddInfo);
            bikeList.add(bike);
            LatLng bikeLocation = new LatLng(bikeLatitude, bikeLongitude);
            simpleAddMarker(map, markerOptions, bikeLocation, bikeOwner, "자전거 종류: " + bikeType);
        }

        LatLng SEOUL = new LatLng(37.506, 126.958);
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(13));

        mMap.setOnMarkerClickListener(this);
    }

    private void simpleAddMarker(final GoogleMap map, MarkerOptions markerOptions, LatLng pos, String title, String context) {
        markerOptions.position(pos);
        markerOptions.title(title);
        markerOptions.snippet(context);
        map.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String mOwner = marker.getTitle();
        for (int i = 0; i < bikeList.size(); ++i){
            if ((bikeList.get(i).getBikeOwner().equals(mOwner)) && (marker.getPosition().latitude == bikeList.get(i).getBikeLatitude())){
                if (markerClickFlag == i){
                    Intent intent = new Intent(Main2Activity.this, RentActivity.class);
                    intent.putExtra("borrower", userID);
                    intent.putExtra("bikecode", bikeList.get(i).getBikeCode());
                    startActivity(intent);
                }else{
                    markerClickFlag = i;
                }
                break;
            }
        }
        return false;
    }

    public String[] getInfo(){
        String[] temp = new String[2];
        temp[0]=userID;
        temp[1]=userName;
        return temp;

    }
}
