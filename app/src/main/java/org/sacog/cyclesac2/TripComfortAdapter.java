package org.sacog.cyclesac2;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TripComfortAdapter extends ArrayAdapter<TripComfortAdapter.TripComfort> {
    public class TripComfort {
        private String comfort;
        @DrawableRes
        private int img;

        public String getComfort() {
            return comfort;
        }

        public @DrawableRes int getImg() {
            return img;
        }

        public TripComfort(String comfort, @DrawableRes int img) {
            this.comfort = comfort;
            this.img = img;
        }
    }

    private Context context;

    public TripComfortAdapter(Context context) {
        super(context, R.layout.trip_comfort_list_item);

        this.context = context;
        this.add(new TripComfort("Excellent - no stress!", R.drawable.excellent));
        this.add(new TripComfort("Good - low stress", R.drawable.good));
        this.add(new TripComfort("Fair - some stress", R.drawable.fair));
        this.add(new TripComfort("Poor - pretty stressful", R.drawable.poor));
        this.add(new TripComfort("Terrible - high stress!", R.drawable.terrible));
    }

    private static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.trip_comfort_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.tripComfortTextView);
            viewHolder.image= (ImageView) convertView.findViewById(R.id.tripComfortImageView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        TripComfort tripComfort = this.getItem(position);
        viewHolder.text.setText(tripComfort.getComfort());
        viewHolder.image.setImageResource(tripComfort.getImg());
        return convertView;
    }
}
