package com.application.challenge.challenge;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.List;


public class LoginActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();
       // if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
        if ((currentUser != null)) {
            //TODO
            //Aca hay que mandar a la proxima activity
            Toast.makeText(this, "te recuerdo el usuario " + currentUser.getUsername(), Toast.LENGTH_LONG).show();
        }

        setContentView(R.layout.activity_login);

//        Typeface tf = Typeface.createFromAsset(getAssets(),
//                "fonts/HarabaraMaisDemo.otf");
//        TextView title = (TextView) findViewById(R.id.txt_challenge_login);
//        title.setTypeface(tf);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private RelativeLayout twitterLoginButton;
        private RelativeLayout facebookLoginButton;
        private Button regularLoginButton;
        private EditText emailInput;
        private EditText passwordInput;

         ProgressDialog progressDialog;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_login, container, false);

            facebookLoginButton = (RelativeLayout) rootView.findViewById(R.id.btn_facebook_login);
            facebookLoginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    progressDialog = ProgressDialog.show(getActivity(), "", "Logging in with Facebook", true);
                    List<String> permissions = Arrays.asList("public_profile", "user_friends", "user_about_me",
                            "user_relationships", "user_birthday", "user_location");
                    ParseFacebookUtils.logIn(permissions, getActivity(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException err) {
                            progressDialog.dismiss();
                            if (user == null) {
                                Toast.makeText(getActivity(), "Uh oh. The user cancelled the Facebook login.", Toast.LENGTH_LONG).show();

                            } else if (user.isNew()) {

                                Toast.makeText(getActivity(), "User signed up and logged in through Facebook!", Toast.LENGTH_LONG).show();

                                //showUserDetailsActivity();
                            } else {

                                Toast.makeText(getActivity(), "User logged in through Facebook!", Toast.LENGTH_LONG).show();

                                //showUserDetailsActivity();
                            }
                        }
                    });

                }
            });

            twitterLoginButton = (RelativeLayout) rootView.findViewById(R.id.btn_twitter_login);
            twitterLoginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    progressDialog = ProgressDialog.show(getActivity(), "", "Logging in with Twitter", true);
                    ParseTwitterUtils.logIn(rootView.getContext(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException err) {
                            progressDialog.dismiss();
                            if (user == null) {
                                Toast.makeText(getActivity(), "Uh oh. The user cancelled the Twitter login.", Toast.LENGTH_LONG).show();
                            } else if (user.isNew()) {
                                Toast.makeText(getActivity(), "User signed up and logged in through Twitter", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "User logged in through Twitter!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            });


            regularLoginButton = (Button) rootView.findViewById(R.id.btn_regular_login);
            regularLoginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    emailInput = (EditText) rootView.findViewById(R.id.edit_text_email);
                    passwordInput = (EditText) rootView.findViewById(R.id.edit_text_password);

                    //TODO
                    //validaciones de email y conrtasena

                    progressDialog = ProgressDialog.show(getActivity(), "", "Signing up...", true);


                    ParseUser user = new ParseUser();
                    user.setUsername(emailInput.getText().toString());
                    user.setPassword(passwordInput.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            progressDialog.dismiss();
                            if (e == null) {
                                Toast.makeText(getActivity(), "User signed up", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Sign up failed", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            });




            return rootView;
        }
    }
}
