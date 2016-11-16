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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.udea.compumovil.socialchallenge.entities.Challenge;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView listChallenges;
    private DatabaseReference mDatabase;
    private DatabaseReference userDatabaseReference;
    FirebaseAuth auth;
    public ActivityFragment() {
        // Required empty public constructor
    }

    public static ActivityFragment newInstance(int sectionNumber) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        listChallenges = (RecyclerView) view.findViewById(R.id.challenge_list);
        listChallenges.setLayoutManager(new LinearLayoutManager(getContext()));
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("activity");
        mDatabase.keepSynced(true);
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        final FirebaseRecyclerAdapter<Challenge, MessageViewHolder> adapter =
                new FirebaseRecyclerAdapter<Challenge, MessageViewHolder>(

                        Challenge.class,
                        R.layout.layout_list_challenges,
                        MessageViewHolder.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(MessageViewHolder viewHolder,
                                                      final Challenge model, final int position) {
                        viewHolder.mText.setText(model.getTitle());

                        Glide.with(getContext())
                                .load(auth.getCurrentUser().getPhotoUrl())
                                .fitCenter()
                                .into(viewHolder.mImageView);

                        viewHolder.mText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent = new Intent(getContext(),ChallengeDetailsActivity.class);
                                intent.putExtra("challengeID", getRef(position).getKey());
                                Log.d("MyApp",getRef(position).getKey());
                                startActivity(intent);
                            }
                        });
                    }
                };

        listChallenges.setAdapter(adapter);
    }

    public  static class MessageViewHolder
            extends RecyclerView.ViewHolder {

        TextView mText;
        ImageView mImageView;


        public MessageViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text1);
            mImageView = (ImageView) itemView.findViewById(R.id.profile_image);

        }


    }

}
