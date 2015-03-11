package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.codec.binary.StringUtils;

import java.util.List;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListViewCustomAdapter extends BaseAdapter {

    private List<ChallengeObject> data;
    private Context context;

    public ChallengesListViewCustomAdapter(Context context,List<ChallengeObject> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ChallengeObject getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ChallengeObject item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.element_challenge_row, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.challenge_title);
            holder.titleTextView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
            holder.subtitleTextView = (TextView) convertView.findViewById(R.id.challenge_subtitle);
            holder.challengeImage = (ParseImageView) convertView.findViewById(R.id.circled_challenge_image);
//            holder.bAction1 = (Button) convertView.findViewById(R.id.btn_challenge_heart);
//            holder.bAction2 = (Button) convertView.findViewById(R.id.btn_challenge_participate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView)parent).recycle(convertView, position);

        if(item.getFirstPhoto() != null){
            ParseFile photoFile = item.getFirstPhoto().getParseFile("thumbnail");
            if (photoFile != null) {
                holder.challengeImage.setParseFile(photoFile);
                holder.challengeImage.loadInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        // nothing to do
                    }
                });
            }
        }

        holder.titleTextView.setText(item.getName());
        holder.subtitleTextView.setText(item.getDescription());



//        holder.bAction1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"hola",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        holder.bAction2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"chau",Toast.LENGTH_LONG).show();
//            }
//        });
//
        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView subtitleTextView;
        ParseImageView challengeImage;
        Button bAction1;
        Button bAction2;
    }

}
