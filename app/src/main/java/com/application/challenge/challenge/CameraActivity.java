package com.application.challenge.challenge;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.SimpleCameraHost;


public class CameraActivity extends Activity {

    private final String TAG_CAMERA_FRAGMENT = "camera_fragment";
    private CameraFragment f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        f = new CameraFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.camera_fragment, f, TAG_CAMERA_FRAGMENT)
                .commit();

        f.setHost(new SimpleCameraHost(this));

        Toast.makeText(getApplicationContext(),String.valueOf(f.getHost().getCameraId()),Toast.LENGTH_LONG).show();
//        f.restartPreview();

        findViewById(R.id.btn_camera_shutter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
                Toast.makeText(getApplicationContext(),"Hola",Toast.LENGTH_LONG).show();

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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

    private void takePicture() {
        CameraFragment f = (CameraFragment) getFragmentManager().findFragmentByTag(TAG_CAMERA_FRAGMENT);
        if (f != null && f.isVisible()) {
            f.takePicture();
        }
    }
}
