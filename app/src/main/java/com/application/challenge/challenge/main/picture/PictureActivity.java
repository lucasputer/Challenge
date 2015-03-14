package com.application.challenge.challenge.main.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.CircularImageView;
import com.application.challenge.challenge.domain.custom.SquareImageView;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import de.greenrobot.event.EventBus;

public class PictureActivity extends Activity {

    private String objectId;
    private PhotoObject phObj;


    TextView username;
    CircularImageView thumbnail;
    SquareImageView picture;
    TextView likes;
    Button likeButton;
    TextView subtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phObj = EventBus.getDefault().getStickyEvent(PhotoObject.class);

        getActionBar().setDisplayHomeAsUpEnabled(true);


        setContentView(R.layout.activity_picture);

    }

    @Override
    public void onResume(){
        super.onResume();



        username = (TextView) this.findViewById(R.id.picture_username);
        thumbnail = (CircularImageView) this.findViewById(R.id.circled_picture_profile_image);
        picture = (SquareImageView) this.findViewById(R.id.picture_image);
        likes = (TextView) this.findViewById(R.id.picture_heart_amount);
        likeButton = (Button) this.findViewById(R.id.btn_picture_heart);
        subtitle = (TextView) this.findViewById(R.id.picture_subtitle);

        if(ParseHelper.getLikedState(phObj)){
            likeButton.setBackground(getResources().getDrawable(R.drawable.btn_picture_heart_liked));
        }

        loadPicture();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPicture(){
        ParseHelper.loadPicture(this, username, thumbnail, picture, likes, subtitle, phObj);
    }

    public void likePicture(View v){
        ParseHelper.likePicture(this, phObj, likes,likeButton );
    }

    public void returnToMain(View v){
        finish();
    }
}
