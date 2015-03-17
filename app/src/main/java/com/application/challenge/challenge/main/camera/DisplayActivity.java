package com.application.challenge.challenge.main.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.adapter.ChallengesListViewAdapter;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.main.MainActivity;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.greenrobot.event.EventBus;


public class DisplayActivity extends Activity {

    static byte[] imageToShow=null;
    private Bitmap pictureBitmap = null;
    private Bitmap thumbnailBitmap = null;
    private EditText subtitleText;
    private Button shareButton;
    private EditText searchChallenge;

    private PhotoObject photo;

    private ChallengeObject settedChallenge = null;
    private ChallengesListViewAdapter challengesListViewAdapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (imageToShow == null) {
            Toast.makeText(this, R.string.no_image, Toast.LENGTH_LONG).show();
            finish();
        }else{
            setContentView(R.layout.activity_display);


            try{
                settedChallenge = EventBus.getDefault().getStickyEvent(ChallengeObject.class);
            }catch(Exception e){
                e.printStackTrace();
            }

            subtitleText = (EditText)findViewById(R.id.etxt_display_comment);
            shareButton = (Button)findViewById(R.id.btn_display_share);
            shareButton.setEnabled(true);

            lv = (ListView)findViewById(android.R.id.list);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //lv.setSelector(getResources().getDrawable(R.drawable.challenges_list_view_selector));


            ParseImageView iv= (ParseImageView) this.findViewById(R.id.img_display_preview);
            BitmapFactory.Options opts=new BitmapFactory.Options();

            opts.inPurgeable=true;
            opts.inInputShareable=true;
            opts.inMutable=false;
            opts.inSampleSize=2;


            pictureBitmap = BitmapFactory.decodeByteArray(imageToShow, 0,imageToShow.length,opts);
            int dimension = getSquareCropDimensionForBitmap(pictureBitmap);
            pictureBitmap = ThumbnailUtils.extractThumbnail(pictureBitmap, dimension, dimension);
            thumbnailBitmap = Bitmap.createScaledBitmap(pictureBitmap, 200, 200
                    * pictureBitmap.getHeight() / pictureBitmap.getWidth(), false);

            iv.setImageBitmap(pictureBitmap);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);



            if(settedChallenge == null){

                ParseQuery<ChallengeObject> query = ParseHelper.getChallengeListQuery();
                query.findInBackground(new FindCallback<ChallengeObject>() {
                    @Override
                    public void done(List<ChallengeObject> challengeObjects, ParseException e) {
                        challengesListViewAdapter = new ChallengesListViewAdapter(getApplicationContext(), challengeObjects );
                        lv.setAdapter(challengesListViewAdapter);
                    }
                });

                searchChallenge = (EditText) findViewById(R.id.etxt_display_search);
                searchChallenge.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        challengesListViewAdapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

            }


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setSelected(true);
                    settedChallenge = challengesListViewAdapter.getItem(position);
                }
            });

        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void sharePicture(View v){

        shareButton.setEnabled(false);
        shareButton.setBackgroundColor(getResources().getColor(R.color.grey));

        PhotoObject phObj = new PhotoObject();
        phObj.setUser(ParseUser.getCurrentUser());

        if(settedChallenge != null){
            phObj.setChallenge(settedChallenge);
            try{
                phObj.setChallenge(settedChallenge.fetch());
            }catch(Exception e){
                e.printStackTrace();
                phObj.setChallenge(null);
            }
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] scaledData = bos.toByteArray();
        phObj.setThumbnail(new ParseFile("photo_thumbnail.jpg", scaledData));

        bos = new ByteArrayOutputStream();
        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        scaledData = bos.toByteArray();
        phObj.setPhoto(new ParseFile("photo.jpg", scaledData));

        if(subtitleText != null){
            phObj.setSubtitle(subtitleText.getText().toString());
        }

        ParseACL acl = new ParseACL();
        acl.setPublicWriteAccess(true);
        acl.setPublicReadAccess(true);
        phObj.setACL(acl);

        phObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "La foto no pudo ser subida. Por favor, intentelo nuevamente más tarde.",Toast.LENGTH_LONG).show();
                    shareButton.setEnabled(true);
                    shareButton.setBackgroundColor(getResources().getColor(R.color.orange));
                }
            }
        });
    }

    public int getSquareCropDimensionForBitmap(Bitmap bitmap)
    {
        int dimension;
        //If the bitmap is wider than it is tall
        //use the height as the square crop dimension
        if (bitmap.getWidth() >= bitmap.getHeight())
        {
            dimension = bitmap.getHeight();
        }
        //If the bitmap is taller than it is wide
        //use the width as the square crop dimension
        else
        {
            dimension = bitmap.getWidth();
        }

        return dimension;
    }
}
