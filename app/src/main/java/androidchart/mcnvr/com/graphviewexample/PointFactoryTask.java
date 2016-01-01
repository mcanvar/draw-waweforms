package androidchart.mcnvr.com.graphviewexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mevlut on 23.12.2015.
 */
public class PointFactoryTask extends AsyncTask<String, Void, Void> {

    Context context;
    GraphView graphView;
    Integer selection;
    List<DataPoint> dataPointList;
    DataPoint[] signalValuesAsArray;
    LineGraphSeries<DataPoint> lineGraphSeries;

    ProgressDialog progressDialog;

    public PointFactoryTask( Context context, GraphView graphView, Integer selection ){
        this.context = context;
        this.graphView = graphView;
        this.selection = selection;
    }

    @Override
    protected Void doInBackground(String... params) {

        Integer time = Integer.parseInt(params[0]);
        Double amplitude = Double.parseDouble(params[1]);
        Double frequency = Double.parseDouble(params[2]);
        Double phase = Double.parseDouble(params[3]);

        if (selection == 0)
            dataPointList = wavePointFactorySine(time, amplitude, frequency, phase);
        else if (selection == 1)
            dataPointList = wavePointFactorySquare(time, amplitude, frequency, phase);
        else if (selection == 2)
            dataPointList = wavePointFactoryTriangle(time, amplitude, frequency, phase);
        else
            dataPointList = wavePointFactorySawtooth(time, amplitude, frequency, phase);

        signalValuesAsArray = dataPointList.toArray(new DataPoint[dataPointList.size()] );
        lineGraphSeries = new LineGraphSeries<>(signalValuesAsArray);

        try {
            Thread.sleep(20);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Creating...");
        progressDialog.setMessage("Preparing graph data.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                PointFactoryTask.this.cancel(true);
                Toast.makeText(context.getApplicationContext(), "Canceled!", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(Void result) {
        graphView.removeAllSeries();
        graphView.addSeries(lineGraphSeries);

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);

        progressDialog.hide();
    }

    private List<DataPoint> wavePointFactorySine(Integer time, Double amp, Double freq, Double pha) {
        Double startPoint = 0.0;
        Integer arraySize = time * 125;
        List<DataPoint> list = new ArrayList<>();

        for (int i=0; i<arraySize; i++) {
            list.add( new DataPoint(startPoint, (amp * (Math.sin(2 * Math.PI * freq * startPoint + pha )))));
            startPoint = startPoint + 0.008;

            if(isCancelled()) break;
        }
        list.add(new DataPoint(startPoint, (amp * (Math.sin(2 * Math.PI * freq * startPoint + pha)))));

        return list;
    }

    private List<DataPoint> wavePointFactorySquare(Integer time, Double amp, Double freq, Double pha) {
        Double startPoint = 0.0;
        Integer arraySize = time * 125;
        List<DataPoint> list = new ArrayList<>();
        Double temp;

        for (int i=0; i<arraySize; i++) {

            temp = amp * (Math.sin(2 * Math.PI * freq * startPoint + pha ));
            if (temp >= 0)
                list.add( new DataPoint(startPoint, 1.0 * amp ));
            else
                list.add( new DataPoint(startPoint, -1.0 * amp ));
            startPoint = startPoint + 0.008;

            if(isCancelled()) break;
        }
        return list;
    }

    private List<DataPoint> wavePointFactoryTriangle(Integer time, Double amp, Double freq, Double pha) {
        Double startPoint = 0.0;
        Integer arraySize = time * 125;
        List<DataPoint> list = new ArrayList<>();
        Double temp;

        for (int i=0; i<arraySize; i++) {

            temp = amp * (Math.sin(2 * Math.PI * freq * startPoint + pha ));
            if ( 0.99 * amp <= temp && temp <= 1 * amp )
                list.add( new DataPoint(startPoint, 1.0 * amp ));
            else if ( temp == 0 )
                list.add( new DataPoint(startPoint, 0 ));
            else if ( -0.99 * amp >= temp && temp >= -1 * amp )
                list.add( new DataPoint(startPoint, -1.0 * amp ));
            startPoint = startPoint + 0.008;

            if(isCancelled()) break;
        }
        list.add( new DataPoint( startPoint, (amp * (Math.sin(2 * Math.PI * freq * startPoint + pha )))));

        return list;
    }

    private List<DataPoint> wavePointFactorySawtooth(Integer time, Double amp, Double freq, Double pha) {
        Double startPoint = 0.0;
        Integer arraySize = time * 125;
        List<DataPoint> list = new ArrayList<>();
        Double temp;

        for (int i=0; i<arraySize; i++) {

            temp = amp * (Math.sin(2 * Math.PI * freq * startPoint + pha ));
            if ( 0.99 * amp <= temp && temp <= 1 * amp ){
                list.add( new DataPoint(startPoint, 1.0 * amp ));
                list.add( new DataPoint(startPoint, 0 ));
            }
            else if ( temp == 0 )
                list.add( new DataPoint(startPoint, 0 ));
            startPoint = startPoint + 0.008;
            if(isCancelled()) break;
        }
        list.add( new DataPoint( startPoint, amp));

        return list;
    }
}
