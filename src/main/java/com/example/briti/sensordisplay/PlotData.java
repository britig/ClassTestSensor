package com.example.briti.sensordisplay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Briti on 28-Aug-17.
 */

public class PlotData extends Activity {
    /** Called when the activity is first created. */
    long timestamp;
    private LineGraphSeries<DataPoint> mSeries;
    //shows unfiltered accelerometer data
    private GraphView graph;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plot_data);
        Intent data = getIntent();
        // Receiving the Data
        String name = data.getStringExtra("x");
        graph = (GraphView) findViewById(R.id.graph);
        Viewport gViewport =  graph.getViewport();
        gViewport.setXAxisBoundsManual(true);
        gViewport.setMinX(0);
        gViewport.setScrollable(true);
        gViewport.setMinY(0);
        gViewport.setScalable(true);
        // activate horizontal and vertical zooming and scrolling
        gViewport.setScalableY(true);
        // activate vertical scrolling
        gViewport.setScrollableY(true);
        mSeries =  new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        //mseries features
        mSeries.setTitle("Acceleration curve");
        mSeries.setColor(Color.GREEN);
        mSeries.setDrawDataPoints(true);
        mSeries.setDataPointsRadius(10);
        mSeries.setThickness(8);
        graph.addSeries(mSeries);
    }
}
