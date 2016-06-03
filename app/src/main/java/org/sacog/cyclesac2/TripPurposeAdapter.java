package org.sacog.cyclesac2;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TripPurposeAdapter extends ArrayAdapter<TripPurposeAdapter.TripPurpose> {
    public class TripPurpose {
        private String purpose;
        private String description;
        private @DrawableRes int img;

        public String getPurpose() {
            return purpose;
        }

        public String getDescription() {
            return description;
        }

        public @DrawableRes int getImg() {
            return img;
        }

        public TripPurpose(String purpose, String description, @DrawableRes int img) {
            this.purpose = purpose;
            this.description = description;
            this.img = img;
        }
    }

	private final Context context;

	public TripPurposeAdapter(Context context) {
		super(context, R.layout.trip_purpose_list_item);

        this.context = context;
        this.add(new TripPurpose(
                "Commute",
                "Biking between home and your primary work location.",
                R.drawable.commute_high
        ));
        this.add(new TripPurpose(
                "School",
                "Biking to or from school or college.",
                R.drawable.school_high
        ));
        this.add(new TripPurpose(
                "Work-Related",
                "Biking to or from a business-related meeting, function, or work-related errand for your job.",
                R.drawable.workrel_high
        ));
        this.add(new TripPurpose(
                "Exercise",
                "Biking for exercise or for fun.",
                R.drawable.exercise_high
        ));
        this.add(new TripPurpose(
                "Social",
                "Biking to or from a social activity (e.g. to a friend's house, the park, a restaurant, the movies).",
                R.drawable.social_high
        ));
        this.add(new TripPurpose(
                "Errand",
                "Biking to attend to personal business (e.g. grocery shopping, banking, doctor's visit, going to the gym).",
                R.drawable.errands_high
        ));
        this.add(new TripPurpose(
                "Other",
                "If none of the other reasons apply to this trip, enter trip comments after saving your trip to tell us more.",
                R.drawable.other_high
        ));
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
            convertView = inflater.inflate(R.layout.trip_purpose_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.TextViewTripPurpose);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.ImageViewTripPurpose);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        TripPurpose purpose = this.getItem(position);
		viewHolder.text.setText(purpose.getPurpose());
        viewHolder.image.setImageResource(purpose.getImg());
		return convertView;
	}
}