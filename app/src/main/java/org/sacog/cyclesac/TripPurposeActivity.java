package org.sacog.cyclesac;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TripPurposeActivity extends Activity {
	String purpose = "";
    String comfort = "";
	private MenuItem saveMenuItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_purpose);
        final TripPurposeActivity self = this;

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		final ListView tripPurposeListView = (ListView) findViewById(R.id.listViewTripPurpose);

		final TripPurposeAdapter tripPurposeAdapter = new TripPurposeAdapter(this);
		tripPurposeListView.setAdapter(tripPurposeAdapter);
		// set default
		tripPurposeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private View oldSelection = null;

            public void clearSelection() {
                if (oldSelection != null) {
                    oldSelection.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clearSelection();
                oldSelection = view;
                view.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                TripPurposeAdapter.TripPurpose tripPurpose = tripPurposeAdapter.getItem(position);
                purpose = tripPurpose.getPurpose();
                TextView tripPurposeDesc = (TextView) findViewById(R.id.textViewTripPurposeDesc);
                tripPurposeDesc.setText(Html.fromHtml(tripPurpose.getDescription()));
                saveMenuItem.setEnabled(true);
            }
        });

        ListView tripComfortListView = (ListView)findViewById(R.id.listViewTripComfort);
        final TripComfortAdapter tripComfortAdapter = new TripComfortAdapter(this);
        tripComfortListView.setAdapter(tripComfortAdapter);
        tripComfortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private View oldSelection = null;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (oldSelection != null) {
                    oldSelection.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                view.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                oldSelection = view;
                self.comfort = tripComfortAdapter.getItem(position).getComfort();
            }
        });

		// Don't pop up the soft keyboard until user clicks!
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	void cancelRecording() {
		Intent rService = new Intent(this, RecordingService.class);
		ServiceConnection sc = new ServiceConnection() {
			public void onServiceDisconnected(ComponentName name) {
			}

			public void onServiceConnected(ComponentName name, IBinder service) {
				IRecordService rs = (IRecordService) service;
				rs.cancelRecording();
				unbindService(this);
			}
		};
		// This should block until the onServiceConnected (above) completes.
		bindService(rService, sc, Context.BIND_AUTO_CREATE);
	}

	/* Creates the menu items */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.trip_purpose, menu);
		saveMenuItem = menu.getItem(1);
		saveMenuItem.setEnabled(false);
		return super.onCreateOptionsMenu(menu);
	}

	/* Handles item selections */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_cancel_trip_purpose:
			Toast.makeText(getBaseContext(), "Trip discarded.", Toast.LENGTH_SHORT).show();

			cancelRecording();

			Intent i = new Intent(TripPurposeActivity.this, TabsConfig.class);
			i.putExtra("keepme", true);
			startActivity(i);
			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			TripPurposeActivity.this.finish();
			return true;
		case R.id.action_save_trip_purpose:
			// move to next view
			// send purpose with intent
			Intent intentToTripDetail = new Intent(TripPurposeActivity.this, TripDetailActivity.class);
			intentToTripDetail.putExtra("purpose", purpose);
            intentToTripDetail.putExtra("comfort", comfort);
			startActivity(intentToTripDetail);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			TripPurposeActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// 2.0 and above
	@Override
	public void onBackPressed() {
		Toast.makeText(getBaseContext(), "Trip discarded.", Toast.LENGTH_SHORT).show();

		cancelRecording();

		Intent i = new Intent(TripPurposeActivity.this, TabsConfig.class);
		i.putExtra("keepme", true);
		startActivity(i);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		TripPurposeActivity.this.finish();
	}

	// Before 2.0
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Toast.makeText(getBaseContext(), "Trip discarded.", Toast.LENGTH_SHORT).show();

			cancelRecording();

			Intent i = new Intent(TripPurposeActivity.this, TabsConfig.class);
			i.putExtra("keepme", true);
			startActivity(i);
			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			TripPurposeActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
