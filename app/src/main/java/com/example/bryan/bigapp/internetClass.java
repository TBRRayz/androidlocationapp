package com.example.bryan.bigapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by bryan on 10-4-2017.
 */

public class internetClass extends AsyncTask<String, Void, Void> {


    Bitmap bit;

    private WeakReference<Activity_second> secondWeakReference;
    //verwijzen naar de second activity om de resultaten te kunnen terug sturen
    public internetClass(Activity_second secondActivity) {

        secondWeakReference = new WeakReference<Activity_second>(secondActivity);
    }

    @Override
    // de functie om een plaatje van de google street view api op te halen
    protected Void doInBackground(String... a) {

        try{
        bit = BitmapFactory.decodeStream((InputStream) new URL("https://maps.googleapis.com/maps/api/streetview?size=400x200&location="+a[0]+"&heading=151.78&pitch=-0.76&key=AIzaSyCOdIsjDUOgGB2SdUzVArZActjXtuMtbKk\n").getContent());
        }catch (Exception e){e.printStackTrace();}

        return null;
    }

    // functie om het plaatje naar de second activity te sturen
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        secondWeakReference.get().setImage(bit);

    }
}
