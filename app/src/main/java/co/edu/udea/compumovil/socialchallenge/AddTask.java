package co.edu.udea.compumovil.socialchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddTask extends AppCompatActivity {

    private EditText textTask;
    private String stringTask = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);



    }

    public void onTaskAdded(View view){

        textTask = (EditText)findViewById(R.id.editText_task);
        stringTask = textTask.getText().toString();

        Intent intentCity = new Intent();

        intentCity.putExtra("stringTask",stringTask);
        setResult(RESULT_OK, intentCity);

    }
}
