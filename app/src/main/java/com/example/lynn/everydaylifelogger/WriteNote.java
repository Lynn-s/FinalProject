package com.example.lynn.everydaylifelogger;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WriteNote extends AppCompatActivity {
    ArrayList<String> picker_arr;//분류 선택

    private GoogleMap map;
    int check = 0;
    Geocoder gc;//주소 변환

    TextView logView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //데이터베이스 open
        final DBhandler handler = new DBhandler(getApplicationContext(), "ScheduleList.db", null, 1);

        Locale.setDefault(Locale.KOREA);
        logView = (TextView) findViewById(R.id.log);
        logView.setText("GPS 가 잡혀야 좌표가 구해짐");


        gc = new Geocoder(getApplicationContext(), Locale.getDefault());
        picker_arr = new ArrayList<String>();
        picker_arr.add("식사");
        picker_arr.add("운동");
        picker_arr.add("학습");
        picker_arr.add("문화");
        picker_arr.add("기타");
        final String[] temp = new String[1];
        // 스피너 속성과 선택값 얻어오기
        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item, picker_arr);

        final Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // 사용자가 선택한 값은 arg2에서 나온다.
                String select_item = String.valueOf(picker_arr.get(arg2));
                temp[0] = select_item;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        LocationListener locationListener = new LocationListener() {
            Marker seoul;//
            public void onLocationChanged(Location location) {
                String addressString = "No address found";
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                try{
                    List<Address> addresses;
                    addresses = gc.getFromLocation(lat,lng, 1);
                    StringBuilder sb = new StringBuilder();
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        for (int i=0;i < address.getMaxAddressLineIndex();i++)
                        sb.append(address.getAddressLine(i)).append("\n");
                        //주소 가져오기
                        //sb.append(address.getCountryName()).append(" ");
                        sb.append(address.getAdminArea()).append(" ");
                        sb.append(address.getLocality()).append(" ");
                        sb.append(address.getThoroughfare()).append(" ");
                        sb.append(address.getFeatureName()).append(" ");

                        addressString = sb.toString();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }

                if(lat>0&&lng>0&&check==0) {
                    check=1;
                    LatLng SEOUL = new LatLng(lat, lng);

                    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    seoul = map.addMarker(new MarkerOptions().position(SEOUL).title("내 위치"));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15));
                    map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
                }
                else if(check==1) {
                    seoul.remove();
                    LatLng SEOUL = new LatLng(lat, lng);

                    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    seoul = map.addMarker(new MarkerOptions().position(SEOUL).title("내 위치"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

                }
                logView.setText("latitude: " + lat + ", longitude: " + lng + "\n주소 : " + addressString);

                final EditText et = (EditText) findViewById(R.id.editText);
                et.setHint(addressString);//edittext에 주소 보여줌
            }


            public void onStatusChanged(String provider, int status, Bundle extras) {
                logView.setText("onStatusChanged");
            }

            public void onProviderEnabled(String provider) {
                logView.setText("onProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                logView.setText("onProviderDisabled");
            }
        };

        final EditText et = (EditText) findViewById(R.id.editText);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.insert("insert into ScheduleList values(null, '" + temp[0] + "', '" + et.getText().toString() + "');");
                Toast.makeText(WriteNote.this,"저장 완료",Toast.LENGTH_SHORT).show();
            }
        };
        Button bt_save = (Button) findViewById(R.id.bt_save);//저장
        bt_save.setOnClickListener(listener);


        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLatitude();
            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
            logView.setText("longtitude=" + lng + ", latitude=" + lat);
        }

    }


}