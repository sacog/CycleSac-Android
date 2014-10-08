package edu.gatech.ppl.cycleatlanta;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class SavedTripsAdapter extends SimpleCursorAdapter {
	private final Context context;
	private final String[] from;
	private final int[] to;
	Cursor cursor;

	public SavedTripsAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, R.layout.saved_trips_list_item, c, from, to, flags);
		this.context = context;
		this.from = from;
		this.to = to;
		this.cursor = c;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.saved_trips_list_item, parent,
				false);
		TextView textViewStart = (TextView) rowView
				.findViewById(R.id.TextViewStart);
		TextView textViewPurpose = (TextView) rowView
				.findViewById(R.id.TextViewPurpose);
		TextView textViewInfo = (TextView) rowView
				.findViewById(R.id.TextViewInfo);
		ImageView imageTripPurpose = (ImageView) rowView
				.findViewById(R.id.ImageTripPurpose);
		TextView textViewCO2 = (TextView) rowView
				.findViewById(R.id.TextViewCO2);
		TextView textViewCalory = (TextView) rowView
				.findViewById(R.id.TextViewCalory);

		cursor.moveToPosition(position);

		SimpleDateFormat sdfStart = new SimpleDateFormat("MMMM d, y  HH:mm");
		// sdfStart.setTimeZone(TimeZone.getTimeZone("UTC"));
		Double startTime = cursor.getDouble(cursor.getColumnIndex("start"));
		String start = sdfStart.format(startTime);

		textViewStart.setText(start);
		textViewPurpose
				.setText(cursor.getString(cursor.getColumnIndex("purp")));

		SimpleDateFormat sdfDuration = new SimpleDateFormat("HH:mm:ss");
		sdfDuration.setTimeZone(TimeZone.getTimeZone("UTC"));
		Double endTime = cursor.getDouble(cursor.getColumnIndex("endtime"));
		String duration = sdfDuration.format(endTime - startTime);
		Log.v("Jason", "Duration: " + duration);

		textViewInfo.setText(duration);

		Double CO2 = cursor.getFloat(cursor.getColumnIndex("distance")) * 0.0006212 * 0.93;
		DecimalFormat df = new DecimalFormat("0.#");
		String CO2String = df.format(CO2);
		textViewCO2.setText("CO2 Saved: " + CO2String + " lbs");

		Double calory = cursor.getFloat(cursor.getColumnIndex("distance")) * 0.0006212 * 49 - 1.69;
		String caloryString = df.format(calory);
		if (calory <= 0) {
			textViewCalory.setText("Calories Burned: " + 0 + " kcal");
		} else {
			textViewCalory
					.setText("Calories Burned: " + caloryString + " kcal");
		}

		int status = cursor.getInt(cursor.getColumnIndex("status"));
		Log.v("Jason", "Status: " + status);
		
		if (status == 0){
			//textViewPurpose.setText("In Progress");
			rowView.setVisibility(View.GONE);
			rowView = inflater.inflate(R.layout.saved_trips_list_item_null, parent,
					false);
		}
		else {
			rowView.setVisibility(View.VISIBLE);
		}

		if (status == 2) {
			if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"Commute")) {
				imageTripPurpose.setImageResource(R.drawable.commute_high);
			} else if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"School")) {
				imageTripPurpose.setImageResource(R.drawable.school_high);
			} else if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"Work-Related")) {
				imageTripPurpose.setImageResource(R.drawable.workrel_high);
			} else if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"Exercise")) {
				imageTripPurpose.setImageResource(R.drawable.exercise_high);
			} else if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"Social")) {
				imageTripPurpose.setImageResource(R.drawable.social_high);
			} else if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"Shopping")) {
				imageTripPurpose.setImageResource(R.drawable.shopping_high);
			} else if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"Errand")) {
				imageTripPurpose.setImageResource(R.drawable.errands_high);
			} else if (cursor.getString(cursor.getColumnIndex("purp")).equals(
					"Other")) {
				imageTripPurpose.setImageResource(R.drawable.other_high);
			}
		} else if (status == 1) {
			imageTripPurpose.setImageResource(R.drawable.failedupload_high);
		}
		return rowView;
	}
}