package androidchart.mcnvr.com.graphviewexample;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;

public class MainActivity extends AppCompatActivity {

    GraphView graphView;
    EditText editTextAmplitude;
    TextView editTextFrequency;
    TextView editTextTime;
    TextView editTextPhase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphView = (GraphView) findViewById(R.id.graph);
        editTextAmplitude = (EditText)findViewById(R.id.editTextAmplitude);
        editTextFrequency = (EditText)findViewById(R.id.editTextFrequency);
        editTextTime = (EditText)findViewById(R.id.editTextTime);
        editTextPhase = (EditText)findViewById(R.id.editTextPhase);
    }

    public void onButtonClick(View v) {

        String time = editTextTime.getText().toString();
        String amplitude = editTextAmplitude.getText().toString();
        String frequency = editTextFrequency.getText().toString();
        String phase = editTextPhase.getText().toString();
        PointFactoryTask pointFactoryTask;

        switch (v.getId()){
            case R.id.buttonPlotSine:
                pointFactoryTask = new PointFactoryTask(MainActivity.this, graphView, 0);
                pointFactoryTask.execute(time, amplitude, frequency, phase);

                hide_keyboard_from(this, v);
                break;
            case R.id.buttonPlotSquare:
                pointFactoryTask = new PointFactoryTask(MainActivity.this, graphView, 1);
                pointFactoryTask.execute(time, amplitude, frequency, phase);

                hide_keyboard_from(this, v);
                break;
            case R.id.buttonPlotTriangle:
                pointFactoryTask = new PointFactoryTask(MainActivity.this, graphView, 2);
                pointFactoryTask.execute(time, amplitude, frequency, phase);

                hide_keyboard_from(this, v);
                break;
            case R.id.buttonPlotSawtooth:
                pointFactoryTask = new PointFactoryTask(MainActivity.this, graphView, 3);
                pointFactoryTask.execute(time, amplitude, frequency, phase);

                hide_keyboard_from(this, v);
                break;
        }
    }

    //hiding keyboard after change. (http://stackoverflow.com/a/17789187/2873992)
    public static void hide_keyboard_from(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
