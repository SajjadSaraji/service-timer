package ir.geekart.sleepservice;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.content.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by sajjad on 12/15/15.
 */
public class TimerForService {

    private static SimpleDateFormat Time_Format = new SimpleDateFormat("HH:mm:ss");
    private static Long countDown_Time;
    private static TextView textView;
    private static Date Time_Remaining;
    private static Intent ServiceToStop;
    private static CountDownTimer countDownTimer;
    private Context context;
    //


    /**
     * @param context pass your activity context to modify service
     * @param textView pass your text view to see countdown timer
     * @param countDown_Time pass a long as countdown timer
     * @param intent pass your service intent to stop after countdown finished
     */
    public TimerForService(Context context,TextView textView, Long countDown_Time, Intent intent){
        try {
            Time_Remaining  = Time_Format.parse("00:00:00");
            setTextView(textView);
            setCountDown_Time(countDown_Time);
            setServiceToStop(intent);
            setContext(context);
            countDownTimer = new CountDownTimer(getCountDown_Time(),100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished-hours*3600*1000);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished-
                            (minutes*60*1000 + hours*3600*1000));
                    Time_Remaining.setHours((int) hours);
                    Time_Remaining.setMinutes((int)minutes);
                    Time_Remaining.setSeconds((int) seconds);
                    getTextView().setText(Time_Format.format(Time_Remaining));
                }

                @Override
                public void onFinish() {

                    getTextView().setText("");
                    getContext().stopService(getServiceToStop());
                }
            };
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    public  void Start (){
        countDownTimer.start();
    }

    public void Stop(){
        countDownTimer.cancel();
    }


    /*
    Getter & Setters
     */
    public static void setTextView(TextView textView) {
        TimerForService.textView = textView;
    }

    public void setServiceToStop(Intent serviceToStop) {
        ServiceToStop = serviceToStop;
    }

    public void setCountDown_Time(Long countDown_Time) {
        this.countDown_Time = countDown_Time;
    }

    public Context getContext() {
        return context;
    }

    public static TextView getTextView() {
        return textView;
    }

    public static Long getCountDown_Time() {
        return countDown_Time;
    }

    public static Intent getServiceToStop() {
        return ServiceToStop;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
