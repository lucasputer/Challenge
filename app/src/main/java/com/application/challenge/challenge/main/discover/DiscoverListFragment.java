package com.application.challenge.challenge.main.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.challenge.challenge.domain.adapter.DiscoverListViewAdapter;
import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.DiscoverItem;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class DiscoverListFragment extends android.support.v4.app.ListFragment{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);


        ArrayList<DiscoverItem> discoverList = new ArrayList<DiscoverItem>();
        discoverList.add(new DiscoverItem("lucasputer","http://matylda.me/wp-content/uploads/2012/05/tumblr_m2i265QSup1qc1wico1_1280.jpg",
                "https://c1.staticflickr.com/3/2882/9727337820_19ba13600d_z.jpg","http://www.beansproutbubba.com.au/blog/wp-content/uploads/2013/06/971275_10151701882558799_1819287792_n.jpg","http://i.imgur.com/cSo2ZP2.jpg"));

        discoverList.add(new DiscoverItem("holu","http://matylda.me/wp-content/uploads/2012/05/tumblr_m2i265QSup1qc1wico1_1280.jpg",
                "https://c1.staticflickr.com/3/2882/9727337820_19ba13600d_z.jpg","http://www.beansproutbubba.com.au/blog/wp-content/uploads/2013/06/971275_10151701882558799_1819287792_n.jpg","http://i.imgur.com/cSo2ZP2.jpg"));

        discoverList.add(new DiscoverItem("besis","http://matylda.me/wp-content/uploads/2012/05/tumblr_m2i265QSup1qc1wico1_1280.jpg",
                "https://c1.staticflickr.com/3/2882/9727337820_19ba13600d_z.jpg","http://www.beansproutbubba.com.au/blog/wp-content/uploads/2013/06/971275_10151701882558799_1819287792_n.jpg","http://i.imgur.com/cSo2ZP2.jpg"));


        setListAdapter(new DiscoverListViewAdapter(getActivity(), discoverList));

        return v;
    }


}
