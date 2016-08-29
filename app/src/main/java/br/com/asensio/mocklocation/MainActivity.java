package br.com.asensio.mocklocation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private TextView latitudeField;
    private TextView longitudeField;
    private TextView latitudeNetworkField;
    private TextView longitudeNetworkField;
    private TextView latitudePassiveField;
    private TextView longitudePassiveField;
    private LocationManager locationManager;
    private String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitudeField = (TextView) findViewById(R.id.textView2);
        longitudeField = (TextView) findViewById(R.id.textView);
        latitudeNetworkField = (TextView) findViewById(R.id.txvLatNetwork);
        longitudeNetworkField = (TextView) findViewById(R.id.txvLongNetwork);
        latitudePassiveField = (TextView) findViewById(R.id.txvLatPassive);
        longitudePassiveField = (TextView) findViewById(R.id.txvLonPassive);

        //Get location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        //provider = LocationManager.NETWORK_PROVIDER;

        Location location = locationManager.getLastKnownLocation(provider);


        if (location != null) {
            System.out.println("Provider " + provider + "has been selected.");
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }


    }

    public void listProvider(View view){

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        for(String prv : locationManager.getAllProviders()) {
            Toast.makeText(getApplicationContext(), prv, Toast.LENGTH_LONG).show();
        }
    }

    public void getPosicaoGPS(View view){
        //Location location = locationManager.getProvider("GPS");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,400,1,this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        if (location != null) {
            System.out.println("Provider " + provider + "has been selected.");
            onLocationChanged(location);
        } else {
            latitudeField.setText("Location not available");
            longitudeField.setText("Location not available");
        }
    }

    public void getPosicaoNetwork(View view){
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,400,1,this);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            System.out.println("Provider " + provider + "has been selected.");
            onLocationChanged(location);
        } else {
            latitudeNetworkField.setText("Location not available");
            longitudeNetworkField.setText("Location not available");
        }

    }

    public void getPosicaoPassive(View view){
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,400,1,this);
        Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        if (location != null) {
            System.out.println("Provider " + provider + "has been selected.");
            onLocationChanged(location);
        } else {
            latitudePassiveField.setText("Location not available");
            longitudePassiveField.setText("Location not available");
        }
    }

    public void getDebugMode (View view){
        if (BuildConfig.DEBUG){
            Toast.makeText(this,"Debug Mode: ON.",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Debug Mode: OFF.",Toast.LENGTH_LONG).show();
        }
    }

    public void getMockLocation(View view){
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            if (location.isFromMockProvider()) {
                Toast.makeText(this, "Mock Location: ON.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Mock Location: OFF.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"GPS not available.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(provider, 400, 1, this);

    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

            double lat = (location.getLatitude());
            double lon = (location.getLongitude());

            if (location.getProvider().equals("gps")) {
                latitudeField.setText(String.valueOf(lat));
                longitudeField.setText(String.valueOf(lon));
            } else if (location.getProvider().equals("network")) {
                latitudeNetworkField.setText(String.valueOf(lat));
                longitudeNetworkField.setText(String.valueOf(lon));
            } else {
                latitudePassiveField.setText(String.valueOf(lat));
                longitudePassiveField.setText(String.valueOf(lon));
            }


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        //Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(this,"Disabled provider " + provider, Toast.LENGTH_SHORT).show();

    }



}
