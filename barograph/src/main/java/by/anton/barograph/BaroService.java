package by.anton.barograph;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Антонио on 19.03.14.
 */
public class BaroService extends IntentService {
    private static final String NAME = "BaroService";
    private static final int POLL_INTERVAL = 1000 * 60 * 20; // 20 минут
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public BaroService() {
        super(NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(NAME, "Handling intent:" + intent);
    }

    public static void setServiceAlarm(Context context, boolean isOn){
        Intent intent = new Intent(context, BaroService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if (isOn){
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), POLL_INTERVAL, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
