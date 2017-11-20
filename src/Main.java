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
;
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
		String[] details;
		String mainpath = "C:/Users/Aviv/Desktop/Bdida/wifiscan-export.csv";
		details = ReadCSV.ReadFromCSV(mainpath);
		elements = ToCSV.CreateData(details);
		ToCSV.CreateCSV(elements);
		ToKML.CreateKML(elements);
		int[] max = ToCSV.sortBy(elements); // Sorting the data
		ToKML.CreatesortedKML(elements, max); // Create sorted KML file by somthing [number is the column number]
	}
}