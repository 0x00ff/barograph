package by.anton.barograph;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BaroBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BaroService.class));
    }
}
