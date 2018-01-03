import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import java.util.Date;

	/**
	 * Project dealing with taking information on wifi networks from any application that provides it,
	 * we take the information and create CSV,KML file with those information organized in colums(CSV) or as placemarks(KML).
	 * Also you can sort the information by signal.
	 * @author Aviv
	 *
	 */
public class Main
{	
	public static void main(String[] args) 
	{
		ArrayList<Data> elements = new ArrayList<>();
		ArrayList<Data> elements2 = new ArrayList<>();
		String[] details;
		String path = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/Log.csv";
		String path2 = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/Algo2.csv";
		String path3 = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/TimesortedLog.csv";
		String path4 = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/WCenter.csv";
		String path5 = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/LonSorted.csv";
		String mainpath = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/WigleWifi_20171210163737.csv";
		details = ReadCSV.ReadFromCSV(mainpath);
		elements = ToCSV.CreateData(details);
		ToCSV.CreateCSV(elements, "Wifi log");
		ToKML.CreateKML(elements);
		
		
		// Adding more data.
		String additional = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/WigleWifi_1012.csv";
		ToCSV.AddData(elements, ReadCSV.ReadFromCSV(additional), path);
		additional = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/WigleWifi_101219.csv";
		ToCSV.AddData(elements, ReadCSV.ReadFromCSV(additional), path);
		
		String MAC = "e0:ce:c3:8a:d4:45"; // Setting MAC address.
		ToCSV.CreateWCenterCSV(elements, path4); // Algorithem 1.
		Algo2.Algo2(elements, path2); // Algorithem 2 
		try { // Sort functions.
			elements = ToCSV.sortBy(elements, "time");
			ToCSV.CreateCSV(elements, "Wifi time sort");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			elements2 = ToCSV.sortBy(elements, "lon");
			ToCSV.CreateCSV(elements2, "Wifi lon sort");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String path6 = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/_comb_all_.csv";

		String path7 = "C:/Users/Aviv/Desktop/Testing/Wiggle tests/result.csv";
		details = ReadCSV.ReadFromCSV(path6);
		elements = ToCSV.CreateData(details);
		ToCSV.CreateWCenterCSV(elements,path7);
		//ToKML.CreatesortedKML(elements, max); // Create sorted KML file by somthing [number is the column number]
	}
}