package com.github.dvdme.ForecastIOLib;

public class FIOHourly {

	FIODataBlock hourly;

	public FIOHourly(ForecastIO fio){

		init(fio);

	}

	private void init(ForecastIO fio){

		if(fio.hasHourly()){
			this.hourly = new FIODataBlock(fio.getHourly());
			this.hourly.setTimezone(fio.getTimezone());
		}
		else {
			this.hourly = null;
		}
	}

	/**
	 * Returns the data point for the given day in the hourly report
	 * @param hour the hour to get
	 * @return return the data point with the report
	 */
	public FIODataPoint getHour(int hour){

		return this.hourly == null ? null : this.hourly.datapoint(hour);  
	}

	/**
	 * Returns the hours in the hourly report
	 * @return integer with the number of hours. Returns -1 if there is not data in the report.
	 */
	public int hours(){
		return this.hourly == null ? -1 : this.hourly.datablockSize();
	}

	/**
	 * Returns forecast summary
	 * @return forecast summary or null
	 */
	public String getSummary() {
		return hourly == null ? null : hourly.summary();
	}

	/**
	 * Returns forecast icon
	 * @return forecast icon or null
	 */
	public String getIcon() {
		return hourly == null ? null : hourly.icon();
	}

}
