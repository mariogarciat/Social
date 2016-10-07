package co.edu.udea.compumovil.socialchallenge;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    // Auth info
    private FirebaseAuth auth;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(int sectionNumber) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        TextView email = (TextView) view.findViewById(R.id.tv_email);
        ImageView profileImage = (ImageView) view.findViewById(R.id.profile_image);
        TextView profileName = (TextView) view.findViewById(R.id.tv_profile_name);

        Glide.with(this)
                .load(user.getPhotoUrl())
                .fitCenter()
                .into(profileImage);

        profileName.setText(getString(R.string.profile_welcome)+user.getDisplayName());
        email.setText(getString(R.string.profile_email) + auth.getCurrentUser().getEmail());
        ProgressBar experienceBar = (ProgressBar) view.findViewById(R.id.experience_bar);
        experienceBar.setProgress(50);

        Button signOut = (Button) view.findViewById(R.id.sign_out_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }


}
