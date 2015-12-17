package ir.geekart.sleepservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    Button startButton, stopButton;
    TextView Status_true, Status_false,countdown_txt;
    private TimePicker timePicker;
    static Context context;
    boolean isPlaying;
    Intent streamService;
    SharedPreferences prefs;
    long time;
    TimerForService TFS;
    private int hours,minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Casting View Items
         */
        streamService = new Intent(MainActivity.this, ExampleService.class);
        context = this;
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        Status_true = (TextView)findViewById(R.id.Service_Status_true);
        Status_false = (TextView)findViewById(R.id.Service_Status_false);
        countdown_txt = (TextView)findViewById(R.id.countdown_txt);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        getPrefs();



        /*
        Declare Countdown
         */
        timePicker.setIs24HourView(true);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hours = hourOfDay;
                minutes = minute;

            }
        });




        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startService(streamService);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                time = TimeUnit.HOURS.toMillis(Long.valueOf(String.valueOf(hours)))+
                        TimeUnit.MINUTES.toMillis(Long.valueOf(String.valueOf(minutes)));
                Toast.makeText(MainActivity.this,String.valueOf(time), Toast.LENGTH_SHORT).show();
                TFS = new TimerForService(getApplicationContext(),countdown_txt
                        ,time,streamService);
                TFS.Start();
                Status_true.setText("True");
                Status_false.setText("");
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                stopService(streamService);
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                Status_true.setText("");
                Status_false.setText("False");
                TFS.Stop();
            }
        });
    }

    public void getPrefs() {
        isPlaying = prefs.getBoolean("isPlaying", false);
        if (isPlaying) startButton.setEnabled(false);
    }

}