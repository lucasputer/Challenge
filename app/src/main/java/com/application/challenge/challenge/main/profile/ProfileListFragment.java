package com.application.challenge.challenge.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.application.challenge.challenge.DiscoverListViewAdapter;
import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.DiscoverItem;
import com.application.challenge.challenge.domain.PictureListViewAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class ProfileListFragment extends android.support.v4.app.ListFragment {

    PictureListViewAdapter pictureListViewAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pictureListViewAdapter = new PictureListViewAdapter(getActivity(), ParseUser.getCurrentUser());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);


        ListView lv = (ListView) v.findViewById(android.R.id.list);

        lv.setAdapter(pictureListViewAdapter);
        pictureListViewAdapter.loadObjects();


        //TODO: arreglar esto para que ande bien
        setListViewHeightBasedOnChildren(lv);

        return v;

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
