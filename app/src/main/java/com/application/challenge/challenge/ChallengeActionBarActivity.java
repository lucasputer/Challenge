package com.application.challenge.challenge;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lucas on 14/1/15.
 * Esta clase sirve para settearle la custom font al texto del actionbar
 */
public class ChallengeActionBarActivity extends FragmentActivity {

    @Override
    public void onStart(){
        super.onStart();

        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Harabara.ttf");
        int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView title = (TextView)findViewById(titleId);
        title.setTypeface(tf);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
    }

}
