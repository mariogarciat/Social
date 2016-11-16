package co.edu.udea.compumovil.socialchallenge;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 */
public class NotificationService extends IntentService {


    private String content = "";
    private int ID = 123;
    private NotificationManager notiManager;
    private Notification notification;


    public NotificationService() {
        super("NotificationService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        content = intent.getExtras().getString("content");
        notiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = getNotification(content);
        int id = ID;
        notiManager.notify(id, notification);

    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Time to this task: ");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.logo);
        return builder.build();
    }


}
