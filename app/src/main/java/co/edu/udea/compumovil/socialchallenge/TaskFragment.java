package co.edu.udea.compumovil.socialchallenge;


import android.content.Context;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import co.edu.udea.compumovil.socialchallenge.entities.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends DialogFragment {

    private EditText editTask;
    private EditText editBegin;
    private EditText editFinish;
    private String stringTask;
    private String sBeginAt;
    private String sFinishAt;
    private List<String> listDays;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private Task task;



    public TaskFragment() {
        // Required empty public constructor
    }


    static TaskFragment newInstance(int num){
        TaskFragment tf = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        tf.setArguments(args);

        return tf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("challenges").child("tasks");
        auth = FirebaseAuth.getInstance();

        task = new Task();

        editTask = (EditText)view.findViewById(R.id.editText_task);
        editBegin = (EditText) view.findViewById(R.id.edit_beginAt);
        editFinish = (EditText) view.findViewById(R.id.edit_finishAt);

        Button buttonAddTask = (Button) view.findViewById(R.id.task_added);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser user = auth.getCurrentUser();

                stringTask = editTask.getText().toString();
                sBeginAt = editBegin.getText().toString();
                sFinishAt = editFinish.getText().toString();

                task.setName(stringTask);
                task.setTimeBegin(sBeginAt);
                task.setTimeEnd(sFinishAt);

                mDatabase.child(user.getUid()).push().setValue(task);
                Log.d("tag", "task added: " + task.getName());

            }
        });




        /*
        Log.d("tag", "antes del boton");
        Log.d("tag3", stringTask);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someEventListener.someEvent(stringTask);
                Log.d("tag2", stringTask+"preparado");
            }
        });*/

        return view;
    }

    public void onCheckBoxSelected(View view){
        boolean checked = ((CheckBox)view).isChecked();

        switch (view.getId()){
            case R.id.c_mon:
                if (checked){
                    listDays.add("Moday");
                }
            case R.id.c_tue:
                if (checked){
                    listDays.add("Tuesday");
                }
            case R.id.c_wed:
                if (checked){
                    listDays.add("Wednesday");
                }
            case R.id.c_thu:
                if (checked){
                    listDays.add("Thursday");
                }
            case R.id.c_fry:
                if (checked){
                    listDays.add("Friday");
                }
            case R.id.c_sat:
                if (checked){
                    listDays.add("Saturday");
                }
            case R.id.c_sun:
                if (checked){
                    listDays.add("Sunday");
                }
        }
        task.setDays(listDays);
    }
/*
    public void onTaskAdded(View view){

        FirebaseUser user = auth.getCurrentUser();

        stringTask = editTask.getText().toString();
        sBeginAt = editBegin.getText().toString();
        sFinishAt = editFinish.getText().toString();

        task.setName(stringTask);
        task.setTimeBegin(sBeginAt);
        task.setTimeEnd(sFinishAt);

        mDatabase.child(user.getUid()).push().setValue(task);
        Log.d("tag", "task added");
    }*/



}
