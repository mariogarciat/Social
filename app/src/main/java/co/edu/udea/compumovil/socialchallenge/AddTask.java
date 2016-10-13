package co.edu.udea.compumovil.socialchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.socialchallenge.entities.Task;

public class AddTask extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("challenges").child("tasks");
        auth = FirebaseAuth.getInstance();

        task = new Task();
        listDays = new ArrayList<>();

        editTask = (EditText) findViewById(R.id.editText_task);
        editBegin = (EditText) findViewById(R.id.edit_beginAt);
        editFinish = (EditText) findViewById(R.id.edit_finishAt);

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
    }

    public void onTaskAdded(View view){

        stringTask = editTask.getText().toString();
        sBeginAt = editBegin.getText().toString();
        sFinishAt = editFinish.getText().toString();

        if(!"".equals(stringTask) && !"".equals(sBeginAt) && !"".equals(sFinishAt)){

            Intent intent = new Intent();
       /* task.setName(stringTask);
        task.setTimeBegin(sBeginAt);
        task.setTimeEnd(sFinishAt);
        task.setDays(listDays);
        intent.putExtra("task", task);*/
            intent.putExtra("name", stringTask);
            intent.putExtra("begin", sBeginAt);
            intent.putExtra("finish", sFinishAt);

            setResult(RESULT_OK, intent);
            super.finish();
        }else {
            Toast.makeText(getApplicationContext(), "Please enter all data", Toast.LENGTH_SHORT).show();
        }


    }
}
