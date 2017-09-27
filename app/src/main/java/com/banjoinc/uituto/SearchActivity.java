package com.banjoinc.uituto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {
    Button btnGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnGPS =(Button)findViewById(R.id.gpsBtn);
        encontrarUbicacion();
    }

    private void encontrarUbicacion() {
        LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }
        Location mLastLocation = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(mLastLocation!=null){
            btnGPS.setText(mLastLocation.getLatitude() + " - "+mLastLocation.getLongitude());
        } else{
            new AlertDialog.Builder(this)
                    .setTitle(this.getResources().getString(R.string.result))
                    .setMessage(R.string.gps_not_found)
                    .setNegativeButton(getResources().getString(R.string.ok), null)
                    .create().show();
        }
    }
}
