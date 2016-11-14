package co.edu.udea.compumovil.socialchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.udea.compumovil.socialchallenge.entities.Challenge;
import co.edu.udea.compumovil.socialchallenge.entities.Task;

public class ChallengeDetailsActivity extends AppCompatActivity {

    private RecyclerView listChallenges;
    private DatabaseReference mDatabase;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String challengeID = extras.getString("challengeID");
            listChallenges = (RecyclerView) this.findViewById(R.id.challenge_list);
            listChallenges.setHasFixedSize(true);
            listChallenges.setLayoutManager(new LinearLayoutManager(this));
            auth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("challenges")
                    .child(auth.getCurrentUser().getUid()).child(challengeID).child("tasks");
            mDatabase.keepSynced(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Task, MessageViewHolder> adapter =
                new FirebaseRecyclerAdapter<Task, MessageViewHolder>(

                        Task.class,
                        R.layout.layout_list_tasks,
                        MessageViewHolder.class,
                        mDatabase
                ) {

                    @Override
                    protected void populateViewHolder(final MessageViewHolder viewHolder, final Task model, int position) {
                        viewHolder.mText.setText(model.getName());
                        viewHolder.mFrom.setText(model.getTimeBegin());
                        viewHolder.mTo.setText(model.getTimeEnd());

                        /*viewHolder.mCheck.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewHolder.mLayout.setBackground(getResources().getDrawable(R.drawable.text_view_background_finish));
                                Toast.makeText(getApplicationContext(), "Task "+model.getName()+" completed", Toast.LENGTH_SHORT).show();
                            }
                        });*/
                        viewHolder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(buttonView.isChecked()){
                                    viewHolder.mLayout.setBackground(getResources().getDrawable(R.drawable.text_view_background_finish));
                                    viewHolder.mCheck.setEnabled(false);
                                    model.setFinished(true);
                                    Toast.makeText(getApplicationContext(), "Task "+model.getName()+" completed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                };

        listChallenges.setAdapter(adapter);
    }

    public  static class MessageViewHolder
            extends RecyclerView.ViewHolder {

        TextView mText;
        CheckBox mCheck;
        TextView mFrom;
        TextView mTo;
        LinearLayout mLayout;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layoutTask);
            mText = (TextView) itemView.findViewById(R.id.text1);
            mCheck = (CheckBox) itemView.findViewById(R.id.check1);
            mFrom = (TextView) itemView.findViewById(R.id.text_from);
            mTo = (TextView) itemView.findViewById(R.id.text_to);
        }


    }
}
