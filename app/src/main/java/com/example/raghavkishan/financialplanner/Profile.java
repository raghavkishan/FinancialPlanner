package com.example.raghavkishan.financialplanner;


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private String personName,personGivenName,personFamilyName,personEmail,personId;

    private Uri personPhoto;

    private ImageView profileImageView;

    private TextView nameTextView,emailTextView;

    private static final String TAG = "Profile Fragment";

    View profileLayoutView;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileLayoutView = inflater.inflate(R.layout.fragment_profile, container, false);

        return profileLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments()!=null){
            personName = getArguments().getString("personName");
            personGivenName = getArguments().getString("personGivenName");
            personFamilyName = getArguments().getString("personFamilyName");
            personEmail = getArguments().getString("personEmail");
            personId = getArguments().getString("personId");
            personPhoto = Uri.parse(getArguments().getString("personPhoto"));
        }

        displayProfileInfo();

    }

    private void displayProfileInfo(){

        profileImageView = (ImageView) profileLayoutView.findViewById(R.id.profileImageView);
        nameTextView = (TextView) profileLayoutView.findViewById(R.id.nameTextView);
        emailTextView = (TextView) profileLayoutView.findViewById(R.id.emailTextView);

        Picasso.with(getContext()).load(personPhoto).into(profileImageView);
        nameTextView.setText(personName);
        emailTextView.setText(personEmail);

    }
}
