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

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class ProfileListFragment extends android.support.v4.app.ListFragment {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);


        ArrayList<String> urls = new ArrayList<String>();
        urls.add("http://www.blogycar.es/wp-content/uploads/2012/03/instagram_perro.jpg");
        urls.add("http://jackiegloves.com/wp-content/files/2013/03/instagram-blog-2.jpg");
        urls.add("http://farm5.static.flickr.com/4150/5450624517_534abcf8e4_m.jpg");
        urls.add("https://s-media-cache-ak0.pinimg.com/736x/34/4d/04/344d045fdca50440aabf368bc1688fee.jpg");
        urls.add("http://farm4.static.flickr.com/3677/9519750899_8ba1b78210_m.jpg");
        urls.add("http://www.clasesdeperiodismo.com/wp-content/uploads/2012/07/instagram-sky.jpg");
        urls.add("http://thetravellette.com/wp-content/uploads/2013/05/Times-square.jpg");
        urls.add("http://matylda.me/wp-content/uploads/2012/05/tumblr_m37zlq2ySY1qc1wico1_1280.jpg");
        urls.add("http://matylda.me/wp-content/uploads/2012/05/tumblr_m37mddGOQi1qc1wico1_1280.jpg");
        urls.add("http://40.media.tumblr.com/31d7f5a51fb8b1f40bcbadf6197d4ca4/tumblr_mox2wptnmM1r1thfzo4_1280.jpg");

        //setListAdapter(new PictureListViewAdapter(getActivity(), urls, "lucasputer", "http://farm5.static.flickr.com/4150/5450624517_534abcf8e4_m.jpg"));

        ListView lv = (ListView) v.findViewById(android.R.id.list);

        lv.setAdapter(new PictureListViewAdapter(getActivity(), urls, "lucasputer", "http://farm5.static.flickr.com/4150/5450624517_534abcf8e4_m.jpg"));


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
