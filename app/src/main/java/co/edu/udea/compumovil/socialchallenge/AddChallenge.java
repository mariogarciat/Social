package co.edu.udea.compumovil.socialchallenge;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import co.edu.udea.compumovil.socialchallenge.adapters.TaskAdapter;
import co.edu.udea.compumovil.socialchallenge.entities.Challenge;
import co.edu.udea.compumovil.socialchallenge.entities.Task;


public class AddChallenge extends AppCompatActivity  {


    private DatabaseReference mDatabase;
    private TextView challengeName;
    private FirebaseAuth auth;
    private ListView listTasks;
    private static final int REQUEST_CODE = 10;
    private String taskName;
    private String beginTask;
    private String finishTask;
    private List<String> taskDays;
    private List<Task> taskList;
    private Task task;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        listTasks = (ListView) findViewById(R.id.task_list);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("challenges")
        .child(auth.getCurrentUser().getUid());
        mDatabase.keepSynced(true);


        taskList = new ArrayList<>();
        taskDays = new ArrayList<>();
        this.listTasks.setAdapter(new TaskAdapter(this, taskList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_challenge, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.button_save) {

            taskDays = task.getDays();
            Log.d("tag", "for() days"+ taskDays.get(1));
            for(int i = 0; i < taskDays.size(); i++){
                Log.d("tag", "position "+i);
                String stringTask = taskDays.get(i);
                if(taskDays.contains("Monday")){
                    setAlarmDay(2, getNotification(stringTask));
                    Log.d("tag", "alarm set on monday: "+taskDays.get(i));
                }
                else if(taskDays.contains("Tuesday")){
                    setAlarmDay(3, getNotification(stringTask));
                    Log.d("tag", "alarm set on monday"+taskDays.get(i));
                }
                else if(taskDays.contains("Wednesday")){
                    setAlarmDay(4, getNotification(stringTask));
                    Log.d("tag", "alarm set on monday"+taskDays.get(i));
                }
                else if(taskDays.contains("Thursday")){
                    setAlarmDay(5, getNotification(stringTask));
                    Log.d("tag", "alarm set on monday"+taskDays.get(i));
                }
                else if(taskDays.contains("Friday")){
                    setAlarmDay(6, getNotification(stringTask));
                    Log.d("tag", "alarm set on monday"+taskDays.get(i));
                }
                else if(taskDays.contains("Saturday")){
                    setAlarmDay(7, getNotification(stringTask));
                    Log.d("tag", "alarm set on monday"+taskDays.get(i));
                }
                else if(taskDays.contains("Sunday")){
                    setAlarmDay(1, getNotification(stringTask));
                    Log.d("tag", "alarm set on monday"+taskDays.get(i));
                }
            }

            challengeName = (TextView) findViewById(R.id.challenge_name_edit);
            String name = challengeName.getText().toString();
            if (auth.getCurrentUser() != null && !TextUtils.isEmpty(name)) {

                Challenge challenge = new Challenge();
                challenge.setTitle(name);
                challenge.setTasks(taskList);

                mDatabase.push().setValue(challenge);
                Toast.makeText(this, "Challenge added", Toast.LENGTH_LONG).show();
                this.finish();
            }else {

                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_LONG).show();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onClickAddTask(View view) {

        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null){
            if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
                if(data.hasExtra("task")){
                    task = (Task) data.getSerializableExtra("task");
                    Log.d("tag", "task " + task.getName() +" received");
                }
                /*taskList.add(task);
                if(data.hasExtra("name")){
                    taskName = data.getExtras().getString("name");
                    Log.d("tag", "name received" + data.getExtras().toString());
                }
                if(data.hasExtra("begin")){
                    beginTask = data.getExtras().getString("begin");
                    Log.d("tag", "begin received" + data.getExtras().toString());
                }
                if(data.hasExtra("finish")){
                    finishTask = data.getExtras().getString("finish");
                    Log.d("tag", "finish received" + data.getExtras().toString());
                }*/
            }
        }else{
            Toast.makeText(getApplicationContext(), "data es nulo", Toast.LENGTH_SHORT).show();
        }
        /*Task task = new Task();
        task.setName(taskName);
        Log.d("tag", "name setted");
        task.setTimeBegin(beginTask);
        Log.d("tag", "begin setted");
        task.setTimeEnd(finishTask);
        Log.d("tag", "finish setted");*/
        taskList.add(task);
        Log.d("tag", "task added");
        listTasks.setAdapter(new TaskAdapter(this,taskList));
    }

    public void setAlarmDay(int day, Notification notification){
        String[] hoursMinutes;
        hoursMinutes = task.getTimeBegin().split(":");

        int hours = Integer.parseInt(hoursMinutes[0]);
        int minutes = Integer.parseInt(hoursMinutes[1]);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,day);
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE, minutes);

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1);
        intent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 154, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 7 * 60 * 60 * 1000, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Time to this task: ");
        builder.setContentText(content);
        //builder.setSmallIcon(R.drawable.ic_launcher);
        return builder.build();
    }

}
