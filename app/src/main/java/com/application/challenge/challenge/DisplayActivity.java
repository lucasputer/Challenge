package com.application.challenge.challenge;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


public class DisplayActivity extends Activity {

    static byte[] imageToShow=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (imageToShow == null) {
            Toast.makeText(this, R.string.no_image, Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            ImageView iv=new ImageView(this);
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

            iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            setContentView(iv);
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
