package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.Interactions;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.FollowActivityObject;
import com.application.challenge.challenge.main.camera.CameraActivity;
import com.application.challenge.challenge.main.challenges.ChallengesListActivity;
import com.application.challenge.challenge.main.profile.ProfileActivity;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by lucas on 16/1/15.
 */
public class UserListViewAdapter extends BaseAdapter {

    private List<FollowActivityObject> data;
    private Context context;
    private String type;


    public UserListViewAdapter(Context context, List<FollowActivityObject> data, String type){
        this.data = data;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public FollowActivityObject getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FollowActivityObject item = getItem(position);
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.element_user_row, parent, false);
            holder = new ViewHolder();
            holder.username = (TextView) convertView.findViewById(R.id.user_username);
            holder.profilePicture = (ParseImageView) convertView.findViewById(R.id.circled_picture_profile_image);
            holder.firstAndLastName = (TextView) convertView.findViewById(R.id.user_first_and_last_name);
            holder.follow = (Button) convertView.findViewById(R.id.btn_discover_follow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseUser user = new ParseUser();
        if(type.equals(Interactions.FOLLOWINGS.toString())){
            user = item.getToUser();
        }else if(type.equals(Interactions.FOLLOWERS.toString())){
            user= item.getFromUser();
        }

        if(user.get("displayPictureThumbnail") != null){
            ParseFile photoFile = user.getParseFile("displayPictureThumbnail");
            if (photoFile != null) {
                holder.profilePicture.setParseFile(photoFile);
                holder.profilePicture.loadInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        // nothing to do
                    }
                });
            }
        }

        holder.username.setText(user.get("displayName").toString());

        String name = "";
        if(user.get("firstName") != null){
            name = user.get("firstName").toString() + " ";
        }
        if(user.get("lastName") != null){
            name = name + user.get("lastName").toString();
        }
        holder.firstAndLastName.setText(name);

        ParseHelper.enableButtonIfIsNotFollowing(user, holder.follow);

        final ParseUser user2 = user;

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseHelper.followUser(user2,holder.follow);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                EventBus.getDefault().postSticky(user2);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView username;
        ParseImageView profilePicture;
        TextView firstAndLastName;
        Button follow;
    }

}
