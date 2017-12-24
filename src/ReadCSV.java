import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import java.util.Date;
;
/**
 *  Class represents reading from CSV file and writing all data into string array. 
 * @author Aviv
 *
 */
public class ReadCSV {	
	/**
	 * ReadFromCSV - gets "getfile" string (file location) and writing all data sorted by colums to array.
	 * for example - details[3] would be all "SSID" column in one string.
	 * @param getfile
	 * @return String array.
	 */

	public static String[] ReadFromCSV(String getfile) {
		String[] details = new String[9];
		try {
		     CsvReader products = new CsvReader(getfile); // Path to second CSV file.
		     products.readHeaders(); // reading now all details from the csv.
		   
		     while (products.readRecord())
		     {
			   details[0] += " "+products.get("FirstSeen");
			      details[1] += " "+products.get("MAC"); 
			      details[2] += " "+products.get("RSSI");
			      details[3] += " "+products.get("SSID").replaceAll("\\s+","");
			      details[4] += " "+products.get("CurrentLongitude");
			      details[5] += " "+products.get("CurrentLatitude");
			      details[6] += " "+products.get("Channel");
			      details[7] += " "+products.get("AltitudeMeters");
		     }
		       products.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return details;
	}
	/**
	 * This function throws string which represents ID of the phone the record has been taken.
	 * gets "getfile" string. (file location)
	 * @param getfile
	 * @return String represents ID of the device.
	 */
	public static String GetID(String getfile) {
		String str = "";
		try {
		     CsvReader products = new CsvReader(getfile); // Path to second CSV file.

		     products.readHeaders(); // reading now all details from the csv.
		   
		     while (products.readRecord())
		     {
		    	 str =  products.get("RSSI");
		    	 if(str.contains("="))
		    	        str=str.substring(str.indexOf("=")+1, str.length());
		     }
		}
		catch (IOException e) 
		{
			 e.printStackTrace();
		}
		return "Device ID: "+str;
	}
	
}
