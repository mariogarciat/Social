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
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends DialogFragment {

    private EditText textTask;
    private String stringTask;

    public TaskFragment() {
        // Required empty public constructor
    }

    public interface onSomeEventListener{
        public void someEvent(String s);
    }
    onSomeEventListener someEventListener;

    static TaskFragment newInstance(int num){
        TaskFragment tf = new TaskFragment();
        Bundle args = new Bundle();
        args.putInt("num", num);
        tf.setArguments(args);

        return tf;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            someEventListener = (onSomeEventListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, null);
        Log.d("tag", "antes del boton");
        textTask = (EditText)view.findViewById(R.id.editText_task);/*
        stringTask = textTask.getText().toString();

        Log.d("tag", "antes del boton");
        Log.d("tag3", stringTask);
        Button button = (Button) view.findViewById(R.id.task_added);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someEventListener.someEvent(stringTask);
                Log.d("tag2", stringTask+"preparado");
            }
        });*/

        return view;
    }

    public void onTaskAdded(View view){


        stringTask = textTask.getText().toString();
        Log.d("tag3", stringTask);

        someEventListener.someEvent(stringTask);
        Log.d("tag2", stringTask+"preparado");
    }



}
