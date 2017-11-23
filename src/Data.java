import java.util.Date;

/**
 * Class represents wifi object which include - SSID, Time, Longtitude, Lattitude, MAC, Signal and Frequency.
 * @author Aviv
 *
 */
public class Data {
	String SSID, MAC, Signal;
	String Lat, Lon, Frequency;
	Date Time;
	/**
	 * 
	 * @param SSID - the name of the wifi.
	 * @param Time - time the record has been taken.
	 * @param Lat - Lattitude coordinates.
	 * @param Lon - Longtitude coordinates.
	 * @param MAC - IP
	 * @param Signal - signal of wifi.
	 * @param Frequency - channel.
	 */
	public Data(String SSID, Date Time, String Lon, String Lat, String MAC, String Signal, String Frequency) {
		this.SSID = SSID;
		this.Time = Time;
		this.Lat = Lat;
		this.Lon = Lon;
		this.MAC = MAC;
		this.Signal = Signal;
		this.Frequency = Frequency;
	}

	
	/**
	 * @return - Frequency.
	 */
	public String getFrequency() {
		return this.Frequency;
	}
	/**
	 * 
	 * @return - SSID.
	 */
	public String getSSID() {
		return this.SSID;
	}
	
	/**
	 * 
	 * @return - Time.
	 */
	public Date getTime() {
		return this.Time;
	}
	/**
	 * 
	 * @return - Lattitude.
	 */
	public String getLat() {
		return this.Lat;
	}
	/**
	 * 
	 * @return IP.
	 */
	public String getMAC() {
		return this.MAC;
	}
	/**
	 * 
	 * @return Longtitude
	 */
	public String getLon() {
		return this.Lon;
	}
	/**
	 * 
	 * @return Signal.
	 */
	public String getSignal() {
		return this.Signal;
	}
}
