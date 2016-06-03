package org.sacog.cyclesac2;

class CyclePoint {
	public float accuracy;
	public double altitude;
	public float speed;
	public double time;
	int latitude;
	int longitude;

	public CyclePoint(int lat, int lgt, double currentTime) {
		this.latitude = lat;
		this.longitude = lgt;
		this.time = currentTime;
	}

	public CyclePoint(int lat, int lgt, double currentTime, float accuracy) {
		this.latitude = lat;
		this.longitude = lgt;
		this.time = currentTime;
		this.accuracy = accuracy;
	}

	public CyclePoint(int lat, int lgt, double currentTime, float accuracy,
			double altitude, float speed) {
		this.latitude = lat;
		this.longitude = lgt;
		this.time = currentTime;
		this.accuracy = accuracy;
		this.altitude = altitude;
		this.speed = speed;
	}
}
