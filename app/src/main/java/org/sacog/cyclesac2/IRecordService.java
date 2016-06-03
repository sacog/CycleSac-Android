package org.sacog.cyclesac2;


public interface IRecordService {
	public int getState();

	public void startRecording(TripData trip);

	public void cancelRecording();

	public long finishRecording(); // returns trip-id

	public long getCurrentTrip(); // returns trip-id

	public void pauseRecording();

	public void resumeRecording();

	public void reset();

	public void setListener(FragmentMainInput mia);
}
