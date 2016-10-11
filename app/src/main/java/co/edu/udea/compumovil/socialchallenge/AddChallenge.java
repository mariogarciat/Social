package co.edu.udea.compumovil.socialchallenge;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.socialchallenge.entities.Challenge;
import co.edu.udea.compumovil.socialchallenge.entities.Task;


public class AddChallenge extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private DatabaseReference mDatabase;
    private TextView challengeName;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("challenges");
        auth = FirebaseAuth.getInstance();


    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {


    }

    public void onClickButton(View view) {

        if (auth.getCurrentUser() != null) {
            challengeName = (TextView) findViewById(R.id.challenge_name_edit);

            String name = challengeName.getText().toString();
            FirebaseUser user = auth.getCurrentUser();
            Challenge challenge = new Challenge();
            List<Task> task = new ArrayList<>();
            task.add(new Task("task 1"));
            task.add(new Task("task 2"));
            task.add(new Task("task 3"));
            challenge.setTitle(name);
            challenge.setTasks(task);
            mDatabase.child(user.getUid()).push().setValue(challenge);
            Toast.makeText(this, "Challenge added" + user.getUid(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    public void onClickSave(View view) {
        Snackbar.make(view, "Challenge saved", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }
}
