package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.main.camera.CameraActivity;
import com.application.challenge.challenge.main.challenges.ChallengesListActivity;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.codec.binary.StringUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesListViewAdapter  extends BaseAdapter implements Filterable{

    private List<ChallengeObject> data;
    private List<ChallengeObject> filteredData;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();

    public ChallengesListViewAdapter(Context context, List<ChallengeObject> data){
        this.data = data;
        this.filteredData=data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public ChallengeObject getItem(int position) {
        return filteredData.get(position);
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //((ListView)parent).recycle(convertView, position);

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

        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView subtitleTextView;
        ParseImageView challengeImage;
    }


    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<ChallengeObject> list = data;

            int count = list.size();
            final ArrayList<ChallengeObject> nlist = new ArrayList<ChallengeObject>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<ChallengeObject>) results.values;
            notifyDataSetChanged();
        }

    }

}
