package androidchart.mcnvr.com.graphviewexample;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    EditText editTextAmplitude;
    TextView editTextFrequency;
    TextView editTextTime;
    TextView editTextPhase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAmplitude = (EditText)findViewById(R.id.editTextAmplitude);
        editTextFrequency = (EditText)findViewById(R.id.editTextFrequency);
        editTextTime = (EditText)findViewById(R.id.editTextTime);
        editTextPhase = (EditText)findViewById(R.id.editTextPhase);
    }

    public void calculateGDataSine (View view) {

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        List<DataPoint> list = new ArrayList<DataPoint>();
        double startPoint = 0.0;
        double endPoint = Double.parseDouble(editTextTime.getText().toString());
        while(startPoint <= endPoint ) {
            list.add(new DataPoint( startPoint, ( Double.parseDouble( editTextAmplitude.getText().toString() ) *
                    (Math.sin(2 * Math.PI * Double.parseDouble( editTextFrequency.getText().toString() ) * startPoint +
                            Double.parseDouble( editTextPhase.getText().toString() ) )))));
            startPoint = startPoint + 0.01;
        }
        DataPoint[] signalValuesAsArray = list.toArray( new DataPoint[ list.size() ] );
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(signalValuesAsArray);
        graph.addSeries(series);

        graph.getViewport().setScalable(true);
        hide_keyboard_from(this, view);
    }

    public void calculateGDataSquare (View view2) {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        List<DataPoint> list2 = new ArrayList<DataPoint>();
        double startPoint2 = 0.0;
        double endPoint2 = Double.parseDouble(editTextTime.getText().toString());

        double coordYValue = 0;
        while(startPoint2 <= endPoint2 ) {
            coordYValue = Double.parseDouble( editTextAmplitude.getText().toString() ) *
                    (Math.sin(2 * Math.PI * Double.parseDouble( editTextFrequency.getText().toString() ) * startPoint2 +
                            Double.parseDouble( editTextPhase.getText().toString() ) ));

            if( coordYValue >= 0 )
                list2.add(new DataPoint( startPoint2, 1.0  * Double.parseDouble( editTextAmplitude.getText().toString() ) ) );
            else
                list2.add(new DataPoint( startPoint2, -1.0 * Double.parseDouble( editTextAmplitude.getText().toString() ) ) );
            startPoint2 = startPoint2 + 0.01;
        }

        DataPoint[] signalValuesAsArray = list2.toArray( new DataPoint[ list2.size() ] );
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(signalValuesAsArray);
        graph.addSeries(series);

        graph.getViewport().setScalable(true);
        hide_keyboard_from(this, view2);
    }

    public void calculateGDataTriangle (View view3) {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.removeAllSeries();
        List<DataPoint> list3 = new ArrayList<DataPoint>();
        double startPoint3 = 0.0;
        double endPoint3 = Double.parseDouble(editTextTime.getText().toString());
        double coordYValue = 0;
        while(startPoint3 <= endPoint3 ) {
            coordYValue = Double.parseDouble( editTextAmplitude.getText().toString() ) *
                    (Math.sin(2 * Math.PI * Double.parseDouble( editTextFrequency.getText().toString() ) * startPoint3 +
                            Double.parseDouble( editTextPhase.getText().toString() ) ));

            if( 0.99 * Double.parseDouble( editTextAmplitude.getText().toString() ) <= coordYValue && coordYValue <= 1 * Double.parseDouble( editTextAmplitude.getText().toString() ) )
                list3.add(new DataPoint( startPoint3, 1.0 * Double.parseDouble( editTextAmplitude.getText().toString() ) ) );
            else if( coordYValue == 0 )
                list3.add(new DataPoint( startPoint3, 0.0 ) );
            else if( -0.99 * Double.parseDouble( editTextAmplitude.getText().toString() ) >= coordYValue && coordYValue >= -1 * Double.parseDouble( editTextAmplitude.getText().toString() ) )
                list3.add(new DataPoint( startPoint3, -1.0 * Double.parseDouble( editTextAmplitude.getText().toString() ) ) );
            startPoint3 = startPoint3 + 0.01;
        }

        DataPoint[] signalValuesAsArray = list3.toArray( new DataPoint[ list3.size() ] );
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(signalValuesAsArray);
        graph.addSeries(series);

        graph.getViewport().setScalable(true);
        hide_keyboard_from(this, view3);
    }

    //hiding keyboard after change (http://stackoverflow.com/a/17789187/2873992)
    public static void hide_keyboard_from(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
