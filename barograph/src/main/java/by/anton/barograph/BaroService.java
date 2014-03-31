package by.anton.barograph;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class BaroService extends IntentService implements SensorEventListener{
    private static final String NAME = "BaroService";
    private static final int POLL_INTERVAL = 1000 * 5/*60 * 20*/; // 20 минут
    private SensorManager sensorManager;
    private Sensor pressure;


    public BaroService() {
        super(NAME);
    }


    public static void setServiceAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = CreateIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pendingIntent);
    }

    public static void cancelServiceAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = CreateIntent(context);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    private static PendingIntent CreateIntent(Context context) {
        return PendingIntent.getService(context, 0, new Intent(context, BaroService.class), 0);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        if (pressure == null) { return; }

        sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        sensorManager.unregisterListener(this, pressure);

        Log.i(NAME, "[" + event.timestamp + "] pressure is " + event.values[0] + " (" + event.accuracy + ")");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Does not matter for now
    }
}
