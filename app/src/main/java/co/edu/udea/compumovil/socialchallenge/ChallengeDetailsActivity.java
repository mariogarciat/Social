package co.edu.udea.compumovil.socialchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.edu.udea.compumovil.socialchallenge.bl.ExperienceController;
import co.edu.udea.compumovil.socialchallenge.entities.Challenge;
import co.edu.udea.compumovil.socialchallenge.entities.Task;

public class ChallengeDetailsActivity extends AppCompatActivity {

    private RecyclerView listChallenges;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabase;
    private DatabaseReference userExp;
    private static final String TAG = "ChallengeDetailsTAG";
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
            userDatabase = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(auth.getCurrentUser().getUid());
            userExp = userDatabase.child("exp");
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
                    protected void populateViewHolder(MessageViewHolder viewHolder, Task model, final int position) {


                        if(!model.isFinished()) {
                            viewHolder.mText.setText(model.getName());
                            viewHolder.mFrom.setText(model.getTimeBegin());
                            viewHolder.mTo.setText(model.getTimeEnd());
                            viewHolder.mCheck.setChecked(false);

                            viewHolder.mCheck.setVisibility(View.VISIBLE);
                            viewHolder.mText.setVisibility(View.VISIBLE);
                            viewHolder.mFrom.setVisibility(View.VISIBLE);
                            viewHolder.mTo.setVisibility(View.VISIBLE);
                            viewHolder.tvFrom.setVisibility(View.VISIBLE);
                            viewHolder.tvTo.setVisibility(View.VISIBLE);
                        } else {

                            viewHolder.mCheck.setVisibility(View.GONE);
                            viewHolder.mText.setVisibility(View.GONE);
                            viewHolder.mFrom.setVisibility(View.GONE);
                            viewHolder.mTo.setVisibility(View.GONE);
                            viewHolder.tvFrom.setVisibility(View.GONE);
                            viewHolder.tvTo.setVisibility(View.GONE);
                        }
                        viewHolder.mCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(b) {
                                    Log.d(TAG, "Task finish:" + getRef(position));
                                    getRef(position).child("finished").setValue(true);

                                    userExp.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int exp = dataSnapshot.getValue(Integer.class);
                                            int newExp = exp + 100;
                                            userExp.setValue(newExp);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                                }else {
                                    getRef(position).child("finished").setValue(false);
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
        TextView tvTo;
        TextView tvFrom;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text1);
            mCheck = (CheckBox) itemView.findViewById(R.id.check1);
            mFrom = (TextView) itemView.findViewById(R.id.text_from);
            mTo = (TextView) itemView.findViewById(R.id.text_to);
            tvFrom = (TextView) itemView.findViewById(R.id.tvFrom);
            tvTo = (TextView) itemView.findViewById(R.id.tv_to);
        }


    }

 }
