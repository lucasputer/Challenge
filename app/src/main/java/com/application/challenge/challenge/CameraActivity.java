package com.application.challenge.challenge;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.SimpleCameraHost;


public class CameraActivity extends Activity implements ChallengeCameraFragment.Contract{

    private final String TAG_CAMERA_FRAGMENT = "camera_fragment";
    private CameraFragment f;
    private static final String STATE_SELECTED_NAVIGATION_ITEM=
            "selected_navigation_item";
    private static final String STATE_SINGLE_SHOT="single_shot";
    private static final String STATE_LOCK_TO_LANDSCAPE=
            "lock_to_landscape";
    private static final int CONTENT_REQUEST=1337;
    private ChallengeCameraFragment std=null;
    private ChallengeCameraFragment ffc=null;
    private ChallengeCameraFragment current=null;
    private boolean hasTwoCameras=(android.hardware.Camera.getNumberOfCameras() > 1);
    private boolean singleShot=false;
    private boolean isLockedToLandscape=false;
    private boolean usingFFC = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        current=ChallengeCameraFragment.newInstance(usingFFC);
        std = current;

        getFragmentManager().beginTransaction()
                .replace(R.id.container, current).commit();

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

    @Override
    public boolean isSingleShotMode() {
        return(singleShot);
    }

    @Override
    public void setSingleShotMode(boolean mode) {
        singleShot=mode;
    }

    public void takePicture(View v) {
        if (current != null   && !current.isSingleShotProcessing()) {
            current.takePicture();
        }
       // startActivity(new Intent(),ShareOptions.class);
    }

    public void turnCamera(View v) {
        usingFFC = !usingFFC;
        if(hasTwoCameras){
            if(ffc == null && usingFFC == true){
                ffc = ChallengeCameraFragment.newInstance(usingFFC);
            }
            if(usingFFC == true){
                std = current;
                current = ffc;
            }else{
                current = std;
            }
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.container, current).commit();

        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                current.lockToLandscape(false);
            }
        });

    }
}
