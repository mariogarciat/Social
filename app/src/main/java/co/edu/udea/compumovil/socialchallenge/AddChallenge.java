package co.edu.udea.compumovil.socialchallenge;


import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;


public class AddChallenge extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_challenge);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        TextView d = (TextView) findViewById(R.id.time_spent_tv);
        d.setText( getString(R.string.time_message) + day + "/" + month + "/" + year);

        // Build the time spent  and set to the challenge
        Calendar finalDate = Calendar.getInstance();
        finalDate.set(Calendar.YEAR, year);
        finalDate.set(Calendar.MONTH, month);
        finalDate.set(Calendar.DAY_OF_MONTH, day);

        Calendar actualDate = Calendar.getInstance();


    }

    public void showDatePickerDialog(View v) {
        final String TAG = "DATE_PICKER";
        FragmentManager fm = getFragmentManager();
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(fm,TAG);

    }

    public void onClickButton(View view) {
        Snackbar.make(view,"Task will be added",Snackbar.LENGTH_LONG).setAction("Action",null).show();

    }

    public void onClickSave(View view) {
        Snackbar.make(view,"Challenge saved",Snackbar.LENGTH_LONG).setAction("Action",null).show();

    }
}
