package co.edu.udea.compumovil.socialchallenge;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    private NotificationManager notiManager;
    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
/*
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        int icon = R.drawable.icon;
        CharSequence tickerText = "Hello you have to take medicine I am Nitin Sharma";
        long when = System.currentTimeMillis();


        CharSequence contentTitle = "My notification";
        CharSequence contentText = "Hello World!";


        final int NOTIF_ID = 1234;
        NotificationManager notofManager = (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, Alset.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0, notificationIntent, 0);
        Notification notification = new Notification(icon, tickerText,when );
       // notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        notification.flags = Notification.FLAG_INSISTENT;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notofManager.notify(NOTIF_ID,notification);*/

        notiManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notiManager.notify(id, notification);

    }
}
