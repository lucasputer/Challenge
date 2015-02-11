package com.application.challenge.challenge.main.camera;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.ChallengesItem;
import com.application.challenge.challenge.domain.ChallengesListViewAdapter;
import com.application.challenge.challenge.domain.parse.PhotoObject;
import com.application.challenge.challenge.main.MainActivity;
import com.application.challenge.challenge.main.commons.application.ChallengeApplication;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class DisplayActivity extends ListActivity {

    static byte[] imageToShow=null;
    private Bitmap pictureBitmap = null;
    private Bitmap thumbnailBitmap = null;
    private EditText subtitleText;
    private Button shareButton;

    private PhotoObject photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (imageToShow == null) {
            Toast.makeText(this, R.string.no_image, Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            setContentView(R.layout.activity_display);

            subtitleText = (EditText)findViewById(R.id.etxt_display_comment);
            shareButton = (Button)findViewById(R.id.btn_display_share);
            shareButton.setEnabled(true);

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

            ArrayList<ChallengesItem> challengesList = new ArrayList<ChallengesItem>();
            challengesList.add(new ChallengesItem("Tu foto en el parque","Subi tu foto en un espacio verde","http://matylda.me/wp-content/uploads/2012/05/tumblr_m2i265QSup1qc1wico1_1280.jpg"));
            challengesList.add(new ChallengesItem("Coca-Cola con tu nombre","Subi tu foto de coca con tu nombre","https://c1.staticflickr.com/3/2882/9727337820_19ba13600d_z.jpg"));
            challengesList.add(new ChallengesItem("Tu foto reciclando","Reciclemos en la ciudad!","http://www.beansproutbubba.com.au/blog/wp-content/uploads/2013/06/971275_10151701882558799_1819287792_n.jpg"));
            challengesList.add(new ChallengesItem("Selfie con amigos","Subi tu mejor foto con amigos","http://i.imgur.com/cSo2ZP2.jpg"));
            challengesList.add(new ChallengesItem("Tu foto en el parque","Subi tu foto en un espacio verde","http://matylda.me/wp-content/uploads/2012/05/tumblr_m2i265QSup1qc1wico1_1280.jpg"));
            challengesList.add(new ChallengesItem("Coca-Cola con tu nombre","Subi tu foto de coca con tu nombre","https://c1.staticflickr.com/3/2882/9727337820_19ba13600d_z.jpg"));
            challengesList.add(new ChallengesItem("Tu foto reciclando","Reciclemos en la ciudad!","http://www.beansproutbubba.com.au/blog/wp-content/uploads/2013/06/971275_10151701882558799_1819287792_n.jpg"));
            challengesList.add(new ChallengesItem("Selfie con amigos","Subi tu mejor foto con amigos","http://i.imgur.com/cSo2ZP2.jpg"));


            setListAdapter(new ChallengesListViewAdapter(this,challengesList));

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
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

    public void sharePicture(View v){

        shareButton.setEnabled(false);

        PhotoObject phObj = new PhotoObject();
        phObj.setUser(ParseUser.getCurrentUser());

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

        phObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "La foto no pudo ser subida. Por favor, intentelo nuevamente",Toast.LENGTH_LONG).show();
                    shareButton.setEnabled(true);
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
