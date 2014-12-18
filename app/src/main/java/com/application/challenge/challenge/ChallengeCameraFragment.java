package com.application.challenge.challenge;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;
import android.widget.Toast;

import com.commonsware.cwac.camera.CameraFragment;
import com.commonsware.cwac.camera.CameraHost;
import com.commonsware.cwac.camera.CameraUtils;
import com.commonsware.cwac.camera.SimpleCameraHost;
import com.commonsware.cwac.camera.PictureTransaction;

import at.markushi.ui.CircleButton;

public class ChallengeCameraFragment extends CameraFragment {
    private static final String KEY_USE_FFC= "com.commonsware.cwac.camera.demo.USE_FFC";
    private MenuItem singleShotItem=null;
    private MenuItem autoFocusItem=null;
    private MenuItem takePictureItem=null;
    private MenuItem flashItem=null;
    private MenuItem recordItem=null;
    private MenuItem stopRecordItem=null;
    private MenuItem mirrorFFC=null;
    private boolean singleShotProcessing=false;
    private CircleButton shutterButton=null;
    private Button changeCameraButton=null;
    private long lastFaceToast=0L;
    String flashMode=null;

    static ChallengeCameraFragment newInstance(boolean useFFC) {
        ChallengeCameraFragment f=new ChallengeCameraFragment();
        Bundle args=new Bundle();

        args.putBoolean(KEY_USE_FFC, useFFC);
        f.setArguments(args);

        return(f);
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        setHasOptionsMenu(true);

        SimpleCameraHost.Builder builder=
                new SimpleCameraHost.Builder(new ChallengeCameraHost(getActivity()));

        setHost(builder.useFullBleedPreview(true).build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View cameraView=
                super.onCreateView(inflater, container, savedInstanceState);
        View results=inflater.inflate(R.layout.fragment_camera, container, false);

        ((ViewGroup)results.findViewById(R.id.camera)).addView(cameraView);


        shutterButton=(CircleButton)results.findViewById(R.id.btn_camera_shutter);
        changeCameraButton=(Button)results.findViewById(R.id.btn_camera_change);
        shutterButton.setColor(Color.parseColor("#ed5800"));

        setRecordingItemVisibility();

        return(results);
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.camera, menu);
//            takePictureItem= menu.findItem(R.id.camera);
//        singleShotItem=menu.findItem(R.id.single_shot);
//        singleShotItem.setChecked(getContract().isSingleShotMode());
//        autoFocusItem=menu.findItem(R.id.autofocus);
//        flashItem=menu.findItem(R.id.flash);
//        recordItem=menu.findItem(R.id.record);
//        stopRecordItem=menu.findItem(R.id.stop);
//        mirrorFFC=menu.findItem(R.id.mirror_ffc);

        if (isRecording()) {
            recordItem.setVisible(false);
            stopRecordItem.setVisible(true);
            takePictureItem.setVisible(false);
        }

        setRecordingItemVisibility();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.camera:
//                takeSimplePicture();
//
//                return(true);
//
//            case R.id.record:
//                try {
//                    record();
//                    getActivity().invalidateOptionsMenu();
//                }
//                catch (Exception e) {
//                    Log.e(getClass().getSimpleName(),
//                            "Exception trying to record", e);
//                    Toast.makeText(getActivity(), e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//
//                return(true);
//
//            case R.id.stop:
//                try {
//                    stopRecording();
//                    getActivity().invalidateOptionsMenu();
//                }
//                catch (Exception e) {
//                    Log.e(getClass().getSimpleName(),
//                            "Exception trying to stop recording", e);
//                    Toast.makeText(getActivity(), e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//
//                return(true);
//
//            case R.id.autofocus:
//                takePictureItem.setEnabled(false);
//                autoFocus();
//
//                return(true);
//
//            case R.id.single_shot:
//                item.setChecked(!item.isChecked());
//                getContract().setSingleShotMode(item.isChecked());
//
//                return(true);
//
//            case R.id.show_zoom:
//                item.setChecked(!item.isChecked());
//                zoom.setVisibility(item.isChecked() ? View.VISIBLE : View.GONE);
//
//                return(true);
//
//            case R.id.flash:
//            case R.id.mirror_ffc:
//                item.setChecked(!item.isChecked());
//
//                return(true);
//        }

        return(super.onOptionsItemSelected(item));
    }

    boolean isSingleShotProcessing() {
        return(singleShotProcessing);
    }



    void setRecordingItemVisibility() {
        if (recordItem != null) {
            if (getDisplayOrientation() != 0
                    && getDisplayOrientation() != 180) {
                recordItem.setVisible(false);
            }
        }
    }

    Contract getContract() {
        return((Contract)getActivity());
    }

    void takeSimplePicture() {
        if (singleShotItem!=null && singleShotItem.isChecked()) {
            singleShotProcessing=true;
            takePictureItem.setEnabled(false);
        }

        PictureTransaction xact=new PictureTransaction(getHost());

        if (flashItem!=null && flashItem.isChecked()) {
            xact.flashMode(flashMode);
        }

        takePicture(xact);
    }

    interface Contract {
        boolean isSingleShotMode();

        void setSingleShotMode(boolean mode);
    }

    class ChallengeCameraHost extends SimpleCameraHost implements
            Camera.FaceDetectionListener {
        boolean supportsFaces=false;

        public ChallengeCameraHost(Context _ctxt) {
            super(_ctxt);
        }

        @Override
        public boolean useFrontFacingCamera() {
            if (getArguments() == null) {
                return(false);
            }

            return(getArguments().getBoolean(KEY_USE_FFC));
        }

        @Override
        public boolean useSingleShotMode() {
            return true;
        }

        @Override
        public void saveImage(PictureTransaction xact, byte[] image) {
            if (useSingleShotMode()) {
                singleShotProcessing=false;

               DisplayActivity.imageToShow=image;
                startActivity(new Intent(getActivity(), DisplayActivity.class));
            }
            else {
                super.saveImage(xact, image);
            }
        }

        @Override
        public void autoFocusAvailable() {
            if (autoFocusItem != null) {
                autoFocusItem.setEnabled(true);

                if (supportsFaces)
                    startFaceDetection();
            }
        }

        @Override
        public void autoFocusUnavailable() {
            if (autoFocusItem != null) {
                stopFaceDetection();

                if (supportsFaces)
                    autoFocusItem.setEnabled(false);
            }
        }

        @Override
        public void onCameraFail(CameraHost.FailureReason reason) {
            super.onCameraFail(reason);

            Toast.makeText(getActivity(),
                    "Sorry, but you cannot use the camera now!",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public Camera.Parameters adjustPreviewParameters(Camera.Parameters parameters) {
            flashMode=
                    CameraUtils.findBestFlashModeMatch(parameters,
                            Camera.Parameters.FLASH_MODE_RED_EYE,
                            Camera.Parameters.FLASH_MODE_AUTO,
                            Camera.Parameters.FLASH_MODE_ON);



            if (parameters.getMaxNumDetectedFaces() > 0) {
                supportsFaces=true;
            }
            else {
                Toast.makeText(getActivity(),
                        "Face detection not available for this camera",
                        Toast.LENGTH_LONG).show();
            }

            return(super.adjustPreviewParameters(parameters));
        }

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            if (faces.length > 0) {
                long now= SystemClock.elapsedRealtime();

                if (now > lastFaceToast + 10000) {
                    Toast.makeText(getActivity(), "I see your face!",
                            Toast.LENGTH_LONG).show();
                    lastFaceToast=now;
                }
            }
        }

        @Override
        @TargetApi(16)
        public void onAutoFocus(boolean success, Camera camera) {
            super.onAutoFocus(success, camera);

            takePictureItem.setEnabled(true);
        }

        @Override
        public boolean mirrorFFC() {
            return false;
        }

        @Override
        public Camera.ShutterCallback getShutterCallback(){
            return new Camera.ShutterCallback() {
                @Override
                public void onShutter() {
                }
            };
        }

    }
}

