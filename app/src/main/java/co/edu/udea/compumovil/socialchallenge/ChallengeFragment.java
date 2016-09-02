package co.edu.udea.compumovil.socialchallenge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.socialchallenge.entities.Challenge;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChallengeFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listChallenges;
    private List<Challenge> challenges;


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
        listChallenges = (ListView) view.findViewById(R.id.challenge_list);
        challenges = Challenge.listAll(Challenge.class);
        listChallenges.setAdapter( new ArrayAdapter<>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,challenges));

        return view;

    }




}
