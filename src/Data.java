import java.util.Date;

/**
 * Class represents wifi object which include - SSID, Time, Lat, Alt, MAC, Signal and Frequency.
 * @author Aviv
 *
 */
public class Data {
	String SSID, MAC, Signal;
	String Lat, Alt, Frequency;
	Date Time;
	/**
	 * 
	 * @param SSID - the name of the wifi.
	 * @param Time - time the record has been taken.
	 * @param Lat - lat coordinates.
	 * @param Alt - alt coordinates.
	 * @param MAC - IP
	 * @param Signal - signal of wifi.
	 * @param Frequency - channel.
	 */
	public Data(String SSID, Date Time, String Lat, String Alt, String MAC, String Signal, String Frequency) {
		this.SSID = SSID;
		this.Time = Time;
		this.Lat = Lat;
		this.Alt = Alt;
		this.MAC = MAC;
		this.Signal = Signal;
		this.Frequency = Frequency;
	}

	public String getFrequency() {
		return this.Frequency;
	}
	public String getSSID() {
		return this.SSID;
	}
	
	public Date getTime() {
		return this.Time;
	}
	public String getLat() {
		return this.Lat;
	}
	
	public String getMAC() {
		return this.MAC;
	}
	public String getAlt() {
		return this.Alt;
	}
	public String getSignal() {
		return this.Signal;
	}
}
