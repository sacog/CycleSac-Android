package org.sacog.cyclesac;

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
        this.add(new TripComfort("Excellent", R.drawable.commute_high));
        this.add(new TripComfort("Good", R.drawable.errands_high));
        this.add(new TripComfort("Fair", R.drawable.exercise_high));
        this.add(new TripComfort("Poor", R.drawable.school_high));
        this.add(new TripComfort("Terrible", R.drawable.social_high));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.trip_comfort_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.tripComfortTextView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.tripComfortImageView);
        TripComfort tripComfort = this.getItem(position);
        textView.setText(tripComfort.getComfort());
        imageView.setImageResource(tripComfort.getImg());
        return rowView;
    }
}
