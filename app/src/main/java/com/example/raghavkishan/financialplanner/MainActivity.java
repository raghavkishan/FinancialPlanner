package com.example.raghavkishan.financialplanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.raghavkishan.financialplanner.LogIn.googleApiClient;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private FirebaseAuth firebaseAuth;

    private static final String TAG = "MainActivity Activity";

    private String personName,personGivenName,personFamilyName,personEmail,personId;
    private Uri personPhoto;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    Log.d(TAG,"Inside onNavigation of the menu");
                    onClickProfileOption(personName,personGivenName,personFamilyName,personEmail,personId,personPhoto);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        personName = getIntent().getStringExtra("personName");
        personGivenName = getIntent().getStringExtra("personGivenName");
        personFamilyName = getIntent().getStringExtra("personFamilyName");
        personEmail = getIntent().getStringExtra("personEmail");
        personId = getIntent().getStringExtra("personId");
        personPhoto = Uri.parse(getIntent().getStringExtra("personPhoto"));

        onClickProfileOption(personName,personGivenName,personFamilyName,personEmail,personId,personPhoto);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signOut:
                SignOut();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickSignOut(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Toast.makeText(this,"you have logged out successfully",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SignOut();
    }

    private void SignOut() {
        googleApiClient.connect();
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override

            public void onConnected(@Nullable Bundle bundle) {

                FirebaseAuth.getInstance().signOut();
                if(googleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Log.d(TAG, "User Logged out");
                                Intent intent = new Intent(MainActivity.this, LogIn.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(),"you have logged out successfully",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });

        /*Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });*/
    }


    public void onClickProfileOption(String personName,String personGivenName,String personFamilyName,
                                      String personEmail,String personId,Uri personPhoto){

        Bundle args = new Bundle();
        args.putString("personName",personName);
        args.putString("personGivenName",personGivenName);
        args.putString("personFamilyName",personFamilyName);
        args.putString("personEmail",personEmail);
        args.putString("personId",personId);
        args.putString("personPhoto",personPhoto.toString());

        Log.d(TAG,"Inside onClickProfileOption");

        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        Profile profile_fragment = new Profile();
        profile_fragment.setArguments(args);
        fragmentTransaction.replace(R.id.mainactivity_frame_layout, profile_fragment);
        fragmentTransaction.commit();
    }

}
