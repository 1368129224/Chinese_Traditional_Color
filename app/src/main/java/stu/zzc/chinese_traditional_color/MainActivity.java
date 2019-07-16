package stu.zzc.chinese_traditional_color;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<MyColor> myColorList = new ArrayList<>();
    private SearchView mSearchView;
    SensorManager mSensorManager;
    Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        initColorList();
        flashRecyclerView();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        initSearch(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.like:
                Intent like_intent = new Intent(MainActivity.this, LikeListActivity.class);
                startActivity(like_intent);
                break;
            case R.id.about:
                Intent about_intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(about_intent);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager != null) {
            mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(sensorEventListener);
        }
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            int medumValue = 16;
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                Random rand = new Random();
                MyColor myColor = myColorList.get(rand.nextInt(526) + 1);
                Intent intent = new Intent(MainActivity.this, ColorDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", myColor.getColorname());
                bundle.putString("RGB", myColor.getColorRGB());
                bundle.putInt("darkColor", myColor.getDarkcolor());
                intent.putExtras(bundle);
                mVibrator.vibrate(30);
                MainActivity.this.startActivity(intent);
            }
        }
    };

    private void initColorList(){
        InputStream inputStream = null;
        BufferedReader br = null;
        String temp [];
        try{
            inputStream = getResources().openRawResource(R.raw.colors);
            br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String lines;
            while ((lines = br.readLine()) != null){
                sb.append(lines);
                temp = lines.split(",");
                MyColor myColor = new MyColor(temp[0], temp[2]);
                myColorList.add(myColor);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void flashRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.RView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        ColorAdapter adapter = new ColorAdapter(myColorList);
        recyclerView.setAdapter(adapter);
    }

    private void searchColor(String keyword){
        List<MyColor> resultColorList = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.RView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        for (MyColor myColor: myColorList){
            if (myColor.getColorname().contains(keyword)){
                resultColorList.add(myColor);
            }
        }
        ColorAdapter adapter = new ColorAdapter(resultColorList);
        recyclerView.setAdapter(adapter);
    }

    private void initSearch(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.searchView);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchColor(s);
                return true;
            }
        });
    }
}