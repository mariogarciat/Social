package co.edu.udea.compumovil.socialchallenge;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    protected void onStart() {
        super.onStart();



    }



    public  static class MessageViewHolder
            extends RecyclerView.ViewHolder {

        TextView mText;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public void onClickAddTask(View view) {

        /*TaskFragment fragTask = new TaskFragment();
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentManager fm = getFragmentManager();
        //ft.add(R.id.addChallenge_layout, fragTask);
        //ft.commit();
        fragTask.show(fm, "tagfrag");*/

        Intent intent = new Intent(getApplicationContext(), AddTask.class);
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

    public void onClickSaveChallenge(View view){

        if (auth.getCurrentUser() != null) {
            challengeName = (TextView) findViewById(R.id.challenge_name_edit);
            FirebaseUser user = auth.getCurrentUser();
            Challenge challenge = new Challenge();
            String name = challengeName.getText().toString();

            challenge.setTitle(name);
            challenge.setTasks(taskList);
            /*List<Task> task = new ArrayList<>();
            task.add(new Task("task 1"));
            task.add(new Task("task 2"));
            task.add(new Task("task 3"));
            challenge.setTasks(task);*/

            mDatabase.push().setValue(challenge);
            Toast.makeText(this, "Challenge added" + user.getUid(), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickSave(View view) {
        Snackbar.make(view, "Challenge saved", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }
}
