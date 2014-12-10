package org.sacog.cyclesac;

import java.util.Map;
import java.util.Map.Entry;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FragmentUserInfo extends Fragment {

	public final static int PREF_AGE = 1;
	public final static int PREF_ZIPHOME = 2;
	public final static int PREF_ZIPWORK = 3;
	public final static int PREF_EMAIL = 4;
    public final static int PREF_FUTURESURVEY = 5;
	public final static int PREF_GENDER = 6;
	public final static int PREF_CYCLEFREQ = 7;
	public final static int PREF_ETHNICITY = 8;
	public final static int PREF_INCOME = 9;
	public final static int PREF_RIDERCONFIDENCE = 10;

	private static final String TAG = "UserPrefActivity";

	private final static int MENU_SAVE = 0;

	public FragmentUserInfo() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.activity_user_info,
				container, false);

		final Button GetStarted = (Button) rootView
				.findViewById(R.id.buttonGetStarted);
        final String rootUrl = this.getString(R.string.root_url);
		GetStarted.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(rootUrl + "/instructions-v2/"));
				startActivity(browserIntent);
			}
		});

		SharedPreferences settings = getActivity().getSharedPreferences(
				"PREFS", 0);
		Map<String, ?> prefs = settings.getAll();
		for (Entry<String, ?> p : prefs.entrySet()) {
			int key = Integer.parseInt(p.getKey());

			switch (key) {
			case PREF_AGE:
				((Spinner) rootView.findViewById(R.id.ageSpinner))
						.setSelection(((Integer) p.getValue()).intValue());
				break;
			case PREF_ETHNICITY:
				((Spinner) rootView.findViewById(R.id.ethnicitySpinner))
						.setSelection(((Integer) p.getValue()).intValue());
				break;
			case PREF_INCOME:
				((Spinner) rootView.findViewById(R.id.incomeSpinner))
						.setSelection(((Integer) p.getValue()).intValue());
				break;
			case PREF_RIDERCONFIDENCE:
				((Spinner) rootView.findViewById(R.id.ridertypeSpinner))
						.setSelection(((Integer) p.getValue()).intValue());
				break;
			case PREF_ZIPHOME:
				((EditText) rootView.findViewById(R.id.TextZipHome))
						.setText((CharSequence) p.getValue());
				break;
			case PREF_ZIPWORK:
				((EditText) rootView.findViewById(R.id.TextZipWork))
						.setText((CharSequence) p.getValue());
				break;
			case PREF_EMAIL:
				((EditText) rootView.findViewById(R.id.TextEmail))
						.setText((CharSequence) p.getValue());
				break;
			case PREF_CYCLEFREQ:
				((Spinner) rootView.findViewById(R.id.cyclefreqSpinner))
						.setSelection(((Integer) p.getValue()).intValue());
				break;
			case PREF_GENDER:
				((Spinner) rootView.findViewById(R.id.genderSpinner))
						.setSelection(((Integer) p.getValue()).intValue());
				break;
            case PREF_FUTURESURVEY:
                ((CheckBox)rootView.findViewById(R.id.futureSurveyCheckBox))
                    .setChecked(((Boolean)p.getValue()).booleanValue());
                break;
			}
		}

		final EditText edittextEmail = (EditText) rootView
				.findViewById(R.id.TextEmail);

		edittextEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);

        final Button saveSettingsButton = (Button)rootView.findViewById(R.id.saveSettingsButton);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                savePreferences();
            }
        });

		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// savePreferences();
	}

	public void savePreferences() {
		// Toast.makeText(getActivity(), "savePreferences()",
		// Toast.LENGTH_LONG).show();

		// Save user preferences. We need an Editor object to
		// make changes. All objects are from android.context.Context
		SharedPreferences settings = getActivity().getSharedPreferences(
				"PREFS", 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putInt("" + PREF_AGE,
				((Spinner) getActivity().findViewById(R.id.ageSpinner))
						.getSelectedItemPosition());
		editor.putInt("" + PREF_ETHNICITY, ((Spinner) getActivity()
				.findViewById(R.id.ethnicitySpinner)).getSelectedItemPosition());
		editor.putInt("" + PREF_INCOME,
				((Spinner) getActivity().findViewById(R.id.incomeSpinner))
						.getSelectedItemPosition());
		editor.putInt("" + PREF_RIDERCONFIDENCE, ((Spinner) getActivity()
				.findViewById(R.id.ridertypeSpinner)).getSelectedItemPosition());

		editor.putString("" + PREF_ZIPHOME, ((EditText) getActivity()
				.findViewById(R.id.TextZipHome)).getText().toString());
		editor.putString("" + PREF_ZIPWORK, ((EditText) getActivity()
				.findViewById(R.id.TextZipWork)).getText().toString());
		editor.putString("" + PREF_EMAIL, ((EditText) getActivity()
				.findViewById(R.id.TextEmail)).getText().toString());
        editor.putBoolean("" + PREF_FUTURESURVEY, ((CheckBox)getActivity()
                .findViewById(R.id.futureSurveyCheckBox)).isChecked());

		editor.putInt("" + PREF_CYCLEFREQ, ((Spinner) getActivity()
				.findViewById(R.id.cyclefreqSpinner)).getSelectedItemPosition());
		// editor.putInt("" + PREF_CYCLEFREQ, ((SeekBar)
		// findViewById(R.id.SeekCycleFreq)).getProgress());

		editor.putInt("" + PREF_GENDER,
				((Spinner) getActivity().findViewById(R.id.genderSpinner))
						.getSelectedItemPosition());
		// RadioGroup rbg = (RadioGroup) findViewById(R.id.RadioGroup01);
		// if (rbg.getCheckedRadioButtonId() == R.id.ButtonMale) {
		// editor.putInt("" + PREF_GENDER, 2);
		// //Log.v(TAG, "gender=" + 2);
		// }
		// if (rbg.getCheckedRadioButtonId() == R.id.ButtonFemale) {
		// editor.putInt("" + PREF_GENDER, 1);
		// //Log.v(TAG, "gender=" + 1);
		// }

		// Log.v(TAG,
		// "ageIndex="
		// + ((Spinner) findViewById(R.id.ageSpinner))
		// .getSelectedItemPosition());
		// Log.v(TAG,
		// "ethnicityIndex="
		// + ((Spinner) findViewById(R.id.ethnicitySpinner))
		// .getSelectedItemPosition());
		// Log.v(TAG,
		// "incomeIndex="
		// + ((Spinner) findViewById(R.id.incomeSpinner))
		// .getSelectedItemPosition());
		// Log.v(TAG,
		// "ridertypeIndex="
		// + ((Spinner) findViewById(R.id.ridertypeSpinner))
		// .getSelectedItemPosition());
		// Log.v(TAG,
		// "riderhistoryIndex="
		// + ((Spinner) findViewById(R.id.riderhistorySpinner))
		// .getSelectedItemPosition());
		// Log.v(TAG, "ziphome="
		// + ((EditText) findViewById(R.id.TextZipHome)).getText()
		// .toString());
		// Log.v(TAG, "zipwork="
		// + ((EditText) findViewById(R.id.TextZipWork)).getText()
		// .toString());
		// Log.v(TAG, "zipschool="
		// + ((EditText) findViewById(R.id.TextZipSchool)).getText()
		// .toString());
		// Log.v(TAG, "email="
		// + ((EditText) findViewById(R.id.TextEmail)).getText()
		// .toString());
		// Log.v(TAG,
		// "frequency="
		// + ((SeekBar) findViewById(R.id.SeekCycleFreq))
		// .getProgress() / 100);

		// Don't forget to commit your edits!!!
		editor.commit();
		Toast.makeText(getActivity(), "User information saved.",
				Toast.LENGTH_SHORT).show();
		// Toast.makeText(getActivity(), ""+((Spinner)
		// getActivity().findViewById(R.id.ageSpinner)).getSelectedItemPosition(),
		// Toast.LENGTH_LONG).show();
	}

	/* Creates the menu items */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		inflater.inflate(R.menu.user_info, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	/* Handles item selections */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_save_user_info:
			savePreferences();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}