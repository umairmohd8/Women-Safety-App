package com.aar.example.tapholdupbutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class EmergencyActivity extends AppCompatActivity implements View.OnClickListener{
    FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTextView, longitTextView;
    int PERMISSION_ID = 44;
    int SMS_PERMISSION = 1;
    Location location;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_activity);
        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);
        btn = findViewById(R.id.button);
        btn.setOnClickListener((View.OnClickListener) this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        //sendSms();

        Log.i("test","base1");




        //Toast.makeText(EmergencyActivity.this, "wrong passcode",
          //      Toast.LENGTH_LONG).show();

    }



    public void sendSms(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED && location != null){
            System.out.println(location.getLatitude() + " " + location.getLongitude());
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("8979695656", null, "https://www.google.com/maps/place/" + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()), null, null);
        }
        else {
            Log.i("test","loc null");

        }
    }
    @Override
    public void onClick(View view) {
    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED && location != null){
        System.out.println(location.getLatitude() + " " + location.getLongitude());
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("8979695656", null, "https://www.google.com/maps/place/" + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()), null, null);
    }
        else {
        Log.i("test","loc null");

    }
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @SuppressLint({"SetTextI18n", "UnlocalizedSms"})
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
        }
    };
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, PERMISSION_ID);
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}
