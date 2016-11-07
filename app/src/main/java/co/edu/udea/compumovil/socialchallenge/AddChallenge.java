package co.edu.udea.compumovil.socialchallenge;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


            if (auth.getCurrentUser() != null) {
                challengeName = (TextView) findViewById(R.id.challenge_name_edit);
                Challenge challenge = new Challenge();
                String name = challengeName.getText().toString();

                challenge.setTitle(name);
                challenge.setTasks(taskList);

                mDatabase.push().setValue(challenge);
                Toast.makeText(this, "Challenge added", Toast.LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();



    }



    public void onClickAddTask(View view) {

        /*TaskFragment fragTask = new TaskFragment();
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentManager fm = getFragmentManager();
        //ft.add(R.id.addChallenge_layout, fragTask);
        //ft.commit();
        fragTask.show(fm, "tagfrag");*/

        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data != null){
            if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
                /*if(data.hasExtra("task")){
                    task = (Task) data.getParcelableExtra("extra");
                    Log.d("tag", "task received");
                }
                taskList.add(task);*/
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
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "data es nulo", Toast.LENGTH_SHORT).show();
        }

        Task task = new Task();

        task.setName(taskName);
        Log.d("tag", "name setted");
        task.setTimeBegin(beginTask);
        Log.d("tag", "begin setted");
        task.setTimeEnd(finishTask);
        Log.d("tag", "finish setted");
        task.setFinished(false);
        Log.d("tag", "boolean setted");
        taskList.add(task);
        Log.d("tag", "task added");
        listTasks.setAdapter(new TaskAdapter(this,taskList));
    }


}
