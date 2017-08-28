package com.example.briti.sensordisplay;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private TextView intensity;
    private TextView pressure;
    private TextView temperature;
    private TextView humidity;
    float x,y,z,p,t;
    float normalizedValue;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /*Method called on sensor event change*/
    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tvX = (TextView)findViewById(R.id.lightIntensity);
        TextView tvY = (TextView)findViewById(R.id.pressure);
        TextView tvZ = (TextView)findViewById(R.id.humidity);
        temperature = (TextView)findViewById(R.id.temperature);
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            //acceleration retrieved from the event and the gravity is removed
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            normalizedValue = (float) Math.sqrt(Math.pow(x,2)+Math.pow(y,2)+Math.pow(z,2));
            temperature.setText("Acceleration:"+Float.toString(normalizedValue));
        }
        else if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            tvX.setText("Light:"+Float.toString(event.values[0]));
        }
        else if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
            tvZ.setText("Proximity:"+Float.toString(event.values[0]));
        }
        else if(event.sensor.getType()==Sensor.TYPE_PRESSURE){
            tvY.setText("Pressure: "+Float.toString(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume(){
        super.onResume();
        mSensorManager.registerListener((SensorEventListener) this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener((SensorEventListener) this,mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener((SensorEventListener) this,mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener((SensorEventListener) this,mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause(){
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener)this);
    }

    public void onPlotClick(View v){
        //Starting a new Intent
        Intent nextScreen = new Intent(getApplicationContext(), PlotData.class);

        //Sending data to another Activity
        nextScreen.putExtra("x", temperature.getText().toString());

        Log.e("n", temperature.getText().toString());

        startActivity(nextScreen);
    }
}
