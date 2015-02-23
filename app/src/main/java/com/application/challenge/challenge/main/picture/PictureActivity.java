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
    private ParseHelper parseHelper;

    TextView username;
    CircularImageView thumbnail;
    SquareImageView picture;
    TextView likes;
    Button likeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        phObj = EventBus.getDefault().getStickyEvent(PhotoObject.class);
        parseHelper = new ParseHelper();

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

        if(parseHelper.getLikedState(phObj)){
            likeButton.setBackground(getResources().getDrawable(R.drawable.btn_picture_heart_liked));
        }

        loadPicture();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture, menu);
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

    private void loadPicture(){
        parseHelper.loadPicture(this, username, thumbnail, picture, likes, parseHelper.getPhotoObjectFromPF(phObj));
    }

    public void likePicture(View v){
        parseHelper.likePicture(this, phObj, likes,likeButton );
    }

    public void returnToMain(View v){
        finish();
    }
}
