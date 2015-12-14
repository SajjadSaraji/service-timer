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

public class MainActivity extends AppCompatActivity {
    Button startButton, stopButton;
    TextView Status_true, Status_false;
    static Context context;
    boolean isPlaying;
    Intent streamService;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        Status_true = (TextView)findViewById(R.id.Service_Status_true);
        Status_false = (TextView)findViewById(R.id.Service_Status_false);

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        getPrefs();
        streamService = new Intent(MainActivity.this, StreamService.class);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startService(streamService);
                startButton.setEnabled(false);
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
                Status_true.setText("");
                Status_false.setText("False");
            }
        });
    }

    public void getPrefs() {
        isPlaying = prefs.getBoolean("isPlaying", false);
        if (isPlaying) startButton.setEnabled(false);
    }

}