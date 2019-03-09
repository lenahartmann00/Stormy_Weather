package com.lenahartmann00.stormyweather.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenahartmann00.stormyweather.databinding.ActivityMainBinding;
import com.lenahartmann00.stormyweather.R;
import com.lenahartmann00.stormyweather.model.CurrentWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private double latitude = 37.8267;
    private double longitude = -122.4233;

    private CurrentWeather currentWeather;

    private ImageView iconImageView;
    TextView cityTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCurrentLocationData();
        getForecast(longitude, latitude);
        Log.i(TAG, "Latitude: " + latitude + " Longtitude: " + longitude);
    }


    private void getCurrentLocationData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else{
            updateCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateCurrentLocation();
                } else {
                    Toast.makeText(this, "To use the app properly, please give permission to access your location", Toast.LENGTH_LONG);
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    private void updateCurrentLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        setLongitudeAndLatitude(lastKnownLocation);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                setLongitudeAndLatitude(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) locationListener);
        locationManager.removeUpdates(locationListener);
    }

    private void updateCity(){
        try {

            cityTextView = findViewById(R.id.txt_city);
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List <Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                cityTextView.setText(city);
            }else {
                cityTextView.setText(getString(R.string.textView_location));
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException occured: ", e);
        }
    }

    private void setLongitudeAndLatitude(Location location) {
        if(location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d(TAG, "Latitude: "+latitude+" Longtitude: "+longitude);
        }
    }

    public void refreshOnClick(View view){
        Toast.makeText(this, "Refreshing data", Toast.LENGTH_SHORT).show();
        getForecast(longitude, latitude);
    }

    private void getForecast(double longitude, double latitude){
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        String apiKey ="82f98db283cc7166db051654bf6ab651";

        String forecastUrl = "https://api.darksky.net/forecast/"
                +apiKey+"/"+latitude+","+longitude;
        TextView darkSky = findViewById(R.id.txt_dark_sky);
        darkSky.setMovementMethod(LinkMovementMethod.getInstance());

        iconImageView = findViewById(R.id.img_icon);

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "IOException caught: ", e);
                    alterUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            currentWeather = getCurrentDetails(jsonData);

                            final CurrentWeather displayWeather = new CurrentWeather(
                                    currentWeather.getLocationLabel(),
                                    currentWeather.getIcon(),
                                    currentWeather.getTime(),
                                    currentWeather.getTemperature(),
                                    currentWeather.getHumidity(),
                                    currentWeather.getPrecipChance(),
                                    currentWeather.getSummary(),
                                    currentWeather.getTimezone()
                            );

                            binding.setWeather(displayWeather);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = getResources().getDrawable(displayWeather.getIconId());
                                    iconImageView.setImageDrawable(drawable);
                                    updateCity();
                                }
                            });
                        } else{
                            alterUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception caught: ", e);
                    }
                }
            });
        }
    }


    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject currently = forecast.getJSONObject("currently");
        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setLocationLabel("Alcatraz Island, CA");
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setTimezone(forecast.getString("timezone"));
        //Convert Fahrenheit to Celsius
        double tempCelsius = ((currently.getDouble("temperature"))-32)*5/9;
        currentWeather.setTemperature(tempCelsius);

        return currentWeather;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            isAvailable = false;
            Toast.makeText(this, getString(R.string.network_unavailable_message), Toast.LENGTH_LONG).show();
        }
        return isAvailable;
    }

    private void alterUserAboutError() {
        AlterDialogFragment dialog = new AlterDialogFragment();
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }
}
