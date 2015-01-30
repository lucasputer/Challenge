package com.application.challenge.challenge.main.challenges;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.ChallengesItem;
import com.application.challenge.challenge.domain.ChallengesListViewAdapter;
import com.application.challenge.challenge.domain.ExpandableHeightGridView;
import com.application.challenge.challenge.domain.ScrollListener;
import com.application.challenge.challenge.domain.SquareImageGridViewAdapter;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListFragment extends android.support.v4.app.ListFragment{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);


        ArrayList<ChallengesItem> challengesList = new ArrayList<ChallengesItem>();
        challengesList.add(new ChallengesItem("Tu foto en el parque","Subi tu foto en un espacio verde","http://matylda.me/wp-content/uploads/2012/05/tumblr_m2i265QSup1qc1wico1_1280.jpg"));
        challengesList.add(new ChallengesItem("Coca-Cola con tu nombre","Subi tu foto de coca con tu nombre","https://c1.staticflickr.com/3/2882/9727337820_19ba13600d_z.jpg"));
        challengesList.add(new ChallengesItem("Tu foto reciclando","Reciclemos en la ciudad!","http://www.beansproutbubba.com.au/blog/wp-content/uploads/2013/06/971275_10151701882558799_1819287792_n.jpg"));
        challengesList.add(new ChallengesItem("Selfie con amigos","Subi tu mejor foto con amigos","http://i.imgur.com/cSo2ZP2.jpg"));
        challengesList.add(new ChallengesItem("Tu foto en el parque","Subi tu foto en un espacio verde","http://matylda.me/wp-content/uploads/2012/05/tumblr_m2i265QSup1qc1wico1_1280.jpg"));
        challengesList.add(new ChallengesItem("Coca-Cola con tu nombre","Subi tu foto de coca con tu nombre","https://c1.staticflickr.com/3/2882/9727337820_19ba13600d_z.jpg"));
        challengesList.add(new ChallengesItem("Tu foto reciclando","Reciclemos en la ciudad!","http://www.beansproutbubba.com.au/blog/wp-content/uploads/2013/06/971275_10151701882558799_1819287792_n.jpg"));
        challengesList.add(new ChallengesItem("Selfie con amigos","Subi tu mejor foto con amigos","http://i.imgur.com/cSo2ZP2.jpg"));


        setListAdapter(new ChallengesListViewAdapter(getActivity(),challengesList));

        return v;
    }


}
