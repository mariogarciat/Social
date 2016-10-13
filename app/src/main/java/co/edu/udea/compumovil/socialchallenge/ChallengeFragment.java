package co.edu.udea.compumovil.socialchallenge;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.socialchallenge.entities.Challenge;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView listChallenges;
    private List<Challenge> challenges;
    private DatabaseReference mDatabase;
    FirebaseAuth auth;

    public ChallengeFragment() {
        // Required empty public constructor
    }
    public static ChallengeFragment newInstance(int sectionNumber) {
        ChallengeFragment fragment = new ChallengeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        listChallenges = (RecyclerView) view.findViewById(R.id.challenge_list);
        listChallenges.setHasFixedSize(true);
        listChallenges.setLayoutManager(new LinearLayoutManager(getContext()));
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("challenges")
        .child(auth.getCurrentUser().getUid());
        mDatabase.keepSynced(true);


        Button button = (Button) view.findViewById(R.id.add_challenge_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getContext(), AddChallenge.class);
                startActivity(intent);
            }
        });

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Challenge, MessageViewHolder> adapter =
                new FirebaseRecyclerAdapter<Challenge, MessageViewHolder>(

                        Challenge.class,
                        android.R.layout.two_line_list_item,
                        MessageViewHolder.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(MessageViewHolder viewHolder, Challenge model, int position) {
                        viewHolder.mText.setText(model.getTitle());
                    }
                };

        listChallenges.setAdapter(adapter);
    }

    public  static class MessageViewHolder
        extends RecyclerView.ViewHolder {

        TextView mText;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
