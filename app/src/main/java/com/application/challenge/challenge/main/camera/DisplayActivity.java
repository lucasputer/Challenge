package com.application.challenge.challenge.main.camera;

import android.app.Activity;
import android.app.ListActivity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.ChallengesItem;
import com.application.challenge.challenge.domain.ChallengesListViewAdapter;

import java.util.ArrayList;


public class DisplayActivity extends ListActivity {

    static byte[] imageToShow=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (imageToShow == null) {
            Toast.makeText(this, R.string.no_image, Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            setContentView(R.layout.activity_display);
            ImageView iv= (ImageView) this.findViewById(R.id.img_display_preview);
            BitmapFactory.Options opts=new BitmapFactory.Options();

            opts.inPurgeable=true;
            opts.inInputShareable=true;
            opts.inMutable=false;
            opts.inSampleSize=2;

            iv.setImageBitmap(BitmapFactory.decodeByteArray(imageToShow,
                    0,
                    imageToShow.length,
                    opts));
            imageToShow=null;

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
}
