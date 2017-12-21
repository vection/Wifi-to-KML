import java.util.Date;

/**
 * Class represents wifi object which include - SSID, Time, Longtitude, Lattitude, MAC, Signal and Frequency.
 * @author Aviv
 *
 */
public class Data {
	String SSID, MAC, Signal;
	String Lat, Lon, Alt,Frequency;
	Date Time;
	Point3D point;
	Point3DWeight wpoint;
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
	public Data(String SSID, Date Time, String Lon, String Lat, String Alt, String MAC, String Signal, String Frequency) {
		this.SSID = SSID;
		this.Time = Time;
		this.Lat = Lat;
		this.Lon = Lon;
		this.Alt = Alt;
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
	public long getlTime() {
		return this.Time.getTime();
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
	 * Alttitude
	 * @return
	 */
	public String getAlt() {
		return this.Alt;
	}
	/**
	 * 
	 * @return Signal.
	 */
	public String getSignal() {
		return this.Signal;
	}
	/**
	 * getData function.
	 * @param s
	 * @return
	 */
	public String getData(Data s) {
		return "MAC: "+s.getMAC();
	}
	/**
	 * Point3D get function.
	 * @param s
	 * @return
	 */
	public Point3D getPoint3D(Data s) {
		if(s.point == null) {
			s.point = new Point3D(Double.parseDouble(s.getLon()), Double.parseDouble(s.getLat()), Double.parseDouble(s.getAlt()));
		}
		return s.point;
	}
	public Point3D getPoint3D() {
		if(this.point == null) {
			this.point = new Point3D(Double.parseDouble(this.Lon), Double.parseDouble(this.Lat), Double.parseDouble(this.Alt));
		}
		return this.point;
	}
	/**
	 * Setting Point3D + Weight as one point. 
	 * @param p Point3D
	 * @param w represents weight
	 * @return
	 */
	public Point3DWeight setWPoint(Point3D p, double w) {
		this.wpoint = new Point3DWeight(p, w);
		return wpoint;
	}
	/**
	 * toString function
	 */
	public String toString() {
		return "Time: "+ this.getTime().toString()+"SSID: "+this.getSSID()+"MAC: "+this.getMAC();
	}
	/**
	 * Some setting functions.
	 * @param p
	 */
	public void setLon(double p) {
		this.Lon = Double.toString(p);
	}
	public void setLat(double p) {
		this.Lat = Double.toString(p);
	}
	public void setAlt(double p) {
		this.Alt = Double.toString(p);
	}
}
