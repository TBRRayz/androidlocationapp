package com.example.bryan.bigapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by bryan on 01-4-2017.
 */
public class Activity_second extends AppCompatActivity {

    private Button but2;
    private Button but3;
    private Button but4;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private ImageView imageView1;
    private EditText text1;
    private EditText text2;

    Bitmap bitmap;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    // de oncreat functie
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        but2 = (Button) findViewById(R.id.but2);
        but3 = (Button) findViewById(R.id.but3);
        but4 = (Button) findViewById(R.id.but4);
        text1 = (EditText) findViewById(R.id.Text1);
        text2 = (EditText) findViewById(R.id.text2);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        // onclick functie voor de save gps button
        but3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                saveInfo();
                setLastGPS();
            }
        });
        //onclick functie voor de show this gps button
        but4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                getImage(text2.getText().toString());
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //functies van de locatie listener
        locationListener = new LocationListener() {
            @Override
            //hier worden de cordinaten vanuit de gps in de een string
            public void onLocationChanged(Location location) {
                text1.setText("\n" + location.getLongitude() + ", " + location.getLatitude());
                Double lengte = location.getLongitude();
                Double breed = location.getLatitude();



                getImage(text1.getText().toString());


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

            configureButton();
    }

    @Override
    //functie voor het meerdere keren controlleren van de permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configureButton();
                break;
            default:
                break;
        }
    }

    void configureButton(){
        // hier word eerste gecheckt of er permission is om de locatie te gebruiken
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // deze code word niet uitgevoerd als er geen permisie is
        // als je op de button click word de locatie doorgestuurd naar de locatiemanager waar je er verder mee kan
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);


            }
        });
    }
    //het aanroepen van de internet class om een plaatje op te halen
    public void getImage(String a){

        internetClass task = new internetClass(this);

        task.execute(a);

    }
    //met deze functie word het plaatje op het scherm gezet
    public void setImage(Bitmap bit){

        imageView1.setImageBitmap(bit);
        bitmap = bit;

    }
    // functie om de gesavede gps to showen
    public void setLastGPS(){
        SharedPreferences pref = getSharedPreferences("imageinfo", Context.MODE_APPEND);
        String text = pref.getString("image", "");
        text2.setText(text);
        //imageView1.setImageBitmap(bitmap);
    }
    //functie om een gps op te slaan
    public void saveInfo(){
        SharedPreferences pref = getSharedPreferences("imageinfo", Context.MODE_APPEND);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("image",text1.getText().toString());
        editor.apply();

    }



}
