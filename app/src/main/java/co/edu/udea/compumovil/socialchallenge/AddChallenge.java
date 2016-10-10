package co.edu.udea.compumovil.socialchallenge;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.AppBarLayout.LayoutParams;

import java.util.Calendar;


public class AddChallenge extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private View challengeList;
    private TextView challenge;
    private static final int REQUEST_CODE = 20;
    private static String task = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);

        challengeList = findViewById(R.id.list_challenges);

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {


    }

    public void showDatePickerDialog(View v) {
        final String TAG = "DATE_PICKER";
        FragmentManager fm = getFragmentManager();
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(fm, TAG);

    }

    public void onClickButton(View view) {

        /*DialogFragment fragTask = new TaskFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.addChallenge_layout, fragTask);
        ft.commit();*/

        Intent intent =  new Intent(getApplicationContext(), AddTask.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data.hasExtra("stringTask")){
            task = data.getExtras().getString("carryCity");

        }

        challenge = new TextView(this);
        challenge.setText(task);
        challenge.setTextColor(0x000);
        challenge.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        ((LinearLayout)challengeList).addView(challenge);

    }

    public void onClickSave(View view) {
        Snackbar.make(view, "Challenge saved", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }
/*
    @Override
    public void someEvent(String s) {

        Log.d("tagAdd", s+"recibido");

        challenge = new TextView(this);
        challenge.setText(s+"puta prueba");
        challenge.setTextColor(0x000);
        challenge.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

        ((LinearLayout)challengeList).addView(challenge);
    }*/
}
