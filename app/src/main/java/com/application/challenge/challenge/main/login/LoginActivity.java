package com.application.challenge.challenge.main.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.helper.FacebookLoginHelper;
import com.application.challenge.challenge.domain.helper.TwitterLoginHelper;
import com.application.challenge.challenge.main.MainActivity;
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
        if ((currentUser != null) && currentUser.getObjectId()!=null) {
            startActivity(new Intent(this, MainActivity.class));
        }

        setContentView(R.layout.activity_login);

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

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Harabara.ttf");
            TextView title = (TextView) rootView.findViewById(R.id.txt_challenge_login);
            title.setTypeface(tf);

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
                            if (user != null) {

                                FacebookLoginHelper.setMissingData();

                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }else{
                                Toast.makeText(getActivity(),"Hubo un error. Por favor, intentelo nuevamente",Toast.LENGTH_LONG).show();
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
                            if (user != null) {

                                TwitterLoginHelper.setMissingData();

                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }else{
                                Toast.makeText(getActivity(),"Hubo un error. Por favor, intentelo nuevamente",Toast.LENGTH_LONG).show();
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
                                startActivity(new Intent(getActivity(), MainActivity.class));

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
