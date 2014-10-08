package org.sacog.cyclesac;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;

public class NoteData {
	long noteid;
	double startTime = 0;
	Location noteLocation = new Location("");
	int notetype;
	String notefancystart, notedetails, noteimageurl;
	byte[] noteimagedata;
	int notestatus;

	DbAdapter mDb;

	// CyclePoint pt;
	int latitude, longitude;
	float accuracy, speed;
	double altitude;

	public static int STATUS_INCOMPLETE = 0;
	public static int STATUS_COMPLETE = 1;
	public static int STATUS_SENT = 2;

	public static NoteData createNote(Context c) {
		NoteData t = new NoteData(c.getApplicationContext(), 0);
		t.createNoteInDatabase(c);
		t.initializeData();
		return t;
	}

	public static NoteData fetchNote(Context c, long noteid) {
		NoteData t = new NoteData(c.getApplicationContext(), noteid);
		t.populateDetails();
		return t;
	}

	public NoteData(Context ctx, long noteid) {
		Context context = ctx.getApplicationContext();
		this.noteid = noteid;
		mDb = new DbAdapter(context);
	}

	void initializeData() {
		startTime = System.currentTimeMillis();
		notetype = -1;
		notefancystart = notedetails = "";

		latitude = 0;
		longitude = 0;
		accuracy = 0;
		altitude = 0;
		speed = 0;

		// updateNote();
	}

	// Get lat/long extremes, etc, from note record
	void populateDetails() {
		mDb.openReadOnly();

		Cursor noteDetails = mDb.fetchNote(noteid);

		startTime = noteDetails.getDouble(noteDetails
				.getColumnIndex("noterecorded"));
		notefancystart = noteDetails.getString(noteDetails
				.getColumnIndex("notefancystart"));
		latitude = noteDetails.getInt(noteDetails.getColumnIndex("notelat"));
		longitude = noteDetails.getInt(noteDetails.getColumnIndex("notelgt"));
		accuracy = noteDetails.getFloat(noteDetails.getColumnIndex("noteacc"));
		altitude = noteDetails.getDouble(noteDetails.getColumnIndex("notealt"));
		speed = noteDetails.getFloat(noteDetails.getColumnIndex("notespeed"));
		notetype = noteDetails.getInt(noteDetails.getColumnIndex("notetype"));
		notedetails = noteDetails.getString(noteDetails
				.getColumnIndex("notedetails"));
		notedetails = noteDetails.getString(noteDetails
				.getColumnIndex("notedetails"));
		notestatus = noteDetails.getInt(noteDetails
				.getColumnIndex("notestatus"));
		noteimageurl = noteDetails.getString(noteDetails
				.getColumnIndex("noteimageurl"));
		noteimagedata = noteDetails.getBlob(noteDetails
				.getColumnIndex("noteimagedata"));

		noteDetails.close();

		mDb.close();
	}

	void createNoteInDatabase(Context c) {
		mDb.open();
		noteid = mDb.createNote();
		mDb.close();
	}

	void dropNote() {
		mDb.open();
		mDb.deleteNote(noteid);
		mDb.close();
	}

	// from MainInput, add time and location point
	boolean addPointNow(Location loc, double currentTime) {
		int lat = (int) (loc.getLatitude() * 1E6);
		int lgt = (int) (loc.getLongitude() * 1E6);

		float accuracy = loc.getAccuracy();
		double altitude = loc.getAltitude();
		float speed = loc.getSpeed();

		// pt = new CyclePoint(lat, lgt, currentTime, accuracy, altitude,
		// speed);

		startTime = currentTime;

		mDb.open();
		boolean rtn = mDb.updateNote(noteid, currentTime, "", -1, "", "", null,
				lat, lgt, accuracy, altitude, speed);
		mDb.close();

		return rtn;
	}

	public boolean updateNoteStatus(int noteStatus) {
		boolean rtn;
		mDb.open();
		rtn = mDb.updateNoteStatus(noteid, noteStatus);
		mDb.close();
		return rtn;
	}

	public boolean getStatus(int noteStatus) {
		boolean rtn;
		mDb.open();
		rtn = mDb.updateNoteStatus(noteid, noteStatus);
		mDb.close();
		return rtn;
	}

	public void updateNote() {
		updateNote(-1, "", "", "", null);
	}

	public void updateNote(int notetype, String notefancystart,
			String notedetails, String noteimgurl, byte[] noteimgdata) {
		// Save the note details to the phone database. W00t!
		mDb.open();
		mDb.updateNote(noteid, startTime, notefancystart, notetype,
				notedetails, noteimgurl, noteimgdata, latitude, longitude,
				accuracy, altitude, speed);
		mDb.close();
	}
}
