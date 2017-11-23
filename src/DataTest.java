import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;


/**
 * JUnit testing - Data class, few random test functions.
 * @author Aviv
 *
 */
public class DataTest {

	@Test
	public void testData() {
		Data p = new Data("SSID", null, null, null, null, null, null);
		Data p2 = new Data("SSID", null, null, null, null, null, null);
		if(p==p2)
		     fail("Somthing wrong here");
	}

	@Test
	public void testGetFrequency() {
		Data p = new Data("SSID", null, null, null, null, null, "-9999");
		Data p2 = new Data("SSID", null, null, null, null, null, "-9990");
		if(p2.getFrequency()==p.getFrequency())
			fail("Somthing wrong with getFerquency.");
	}

	@Test
	public void testGetSSID() {
		Data p = new Data("SSID", null, null, null, null, null, "-9999");
		Data p2 = new Data("SSID", null, null, null, null, null, "-9990");
		if(p2.getSSID()==p.getSSID()) {}
		else { fail("Somthing wrong with getSSID."); }
	}

	@Test
	public void testGetTime() {
		Date time = new Date();
		Date time2 = new Date();
		Data p = new Data("SSID", time, null, null, null, null, "-9999");
		Data p2 = new Data("SSID", time2, null, null, null, null, "-9990");
		if(p2.getTime()==p.getTime()) {fail("Somthing wrong with the time.");}
	}

	@Test
	public void testGetLat() {
		Data p = new Data("SSID", null, null, "55.73422", null, null, "-9999");
		Data p2 = new Data("SSID", null, null, "66.6432232", null, null, "-9990");
		if(p2.getLat()==p.getLat()) {fail("Somthing wrong with getLattitude"); }
	}

	@Test
	public void testGetMAC() {
		Data p = new Data("SSID", null, null, "55.73422", "55.34.3.22", null, "-9999");
		Data p2 = new Data("SSID", null, null, "66.6432232", "55.34.3.21", null, "-9990");
		if(p2.getMAC()==p.getMAC()) {fail("Somthing wrong with "); }
	}

	@Test
	public void testGetLon() {
		Data p = new Data("SSID", null, "23123.232", "55.73422", null, null, "-9999");
		Data p2 = new Data("SSID", null, "23123.23222", "66.6432232", null, null, "-9990");
		if(p2.getLon()==p.getLon()) {fail("Somthing wrong with getLongtitude"); }
	}

	@Test
	public void testGetSignal() {
		Data p = new Data("SSID", null, "23123.232", "55.73422", null, "88", "-9999");
		Data p2 = new Data("SSID", null, "23123.23222", "66.6432232", null, "88", "-9990");
		if(p2.getSignal()==p.getSignal()) {}
		else { fail("Somthing wrong with getSignal"); }
	}

}
