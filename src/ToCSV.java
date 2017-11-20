import java.text.SimpleDateFormat;
import java.time.Instant;
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
import java.util.TimeZone;
;
	

/**
 * This class represents CSV writing.
 * @author Aviv
 *
 */
public class ToCSV
{	
	/**
	 * CreateData gets string array and throwing Arraylist on Data object.
	 * @param details
	 * @return
	 */
	public static ArrayList<Data> CreateData(String[] details) {
		ArrayList<Data> elements = new ArrayList<>();
		Date time = new Date();
		String[] sp,sp1,sp2,sp3,sp4,sp5,sp6;
	 	sp = details[0].split(" ");
	 	sp1 = details[1].split(" ");
	    sp2 = details[2].split(" ");
	    sp3 = details[3].split(" ");
	    sp4 = details[4].split(" ");
	    sp5 = details[5].split(" ");
	    sp6 = details[6].split(" ");
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	    for(int i=1; i<310; i++) // splitting the whole string and writing the details in specific order.
        {
	    	time = new Date(Long.parseLong(sp[i])*1000);
	      	Data p = new Data(sp3[i], time, sp4[i], sp5[i], sp1[i], sp2[i], sp6[i]);
	 	    elements.add(p);
        }
	    return elements;
	}
	/**
	 * CreateCSV gets Arraylist of Data object and creates CSV file with the requested format.
	 * @param elements
	 */
	public static void CreateCSV(ArrayList<Data> elements) {
		
		String outputFile = "C:/Users/Aviv/Desktop/Bdida/Log.csv"; // Path to output CSV file.
		String IDfile = "C:/Users/Aviv/Desktop/Bdida/WigleWifi_20171031183939.csv";
		boolean alreadyExists = new File(outputFile).exists();
		if(!alreadyExists)
		{
		  try 
		  {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
			// if the file didn't already exist then we need to write out the header line
			if (!alreadyExists)
			{
							csvOutput.write("Time");
							csvOutput.write("ID");
							csvOutput.write("Lat");
							csvOutput.write("Lon");
							csvOutput.write("Alt");
							csvOutput.write("SSID");
							csvOutput.write("MAC");
							csvOutput.write("Ferquency");
							csvOutput.write("Signal");
							csvOutput.endRecord();
			}	
			csvOutput.write("");
			csvOutput.write(ReadCSV.GetID(IDfile));
			csvOutput.endRecord();
		    for(int i=1; i<elements.size(); i++) // splitting the whole string and writing the details in specific order.
		    {
		          csvOutput.write(elements.get(i).getTime().toString());
		          csvOutput.write("");
		          csvOutput.write(elements.get(i).getLat());
		          csvOutput.write(elements.get(i).getAlt());
		          csvOutput.write("");
		          csvOutput.write(elements.get(i).getSSID());
		          csvOutput.write(elements.get(i).getMAC());
		          csvOutput.write(elements.get(i).getFrequency());
		          csvOutput.write(elements.get(i).getSignal());
		          csvOutput.endRecord();
		    }
		    
		    csvOutput.close();
		    System.out.println("CSV file created here -  "+outputFile);
		  }
		  catch (IOException e) 
			{
				e.printStackTrace();
			} 
		}
	}
	/**
	 * Sortby gets ArrayList of object Data, sort it by signal and create KML file with the 10 highest signal valuse.
	 * @param elements
	 * @return
	 */
	public static int[] sortBy(ArrayList<Data> elements)
	{
		int[] max = new int[10];
		int[] sorted = new int[elements.size()];
		for(int i=1; i<elements.size(); i++)
		{
			sorted[i] = Integer.parseInt(elements.get(i).getSignal());
		}
		Arrays.sort(sorted);
		int j=0;
		for(int i =sorted.length-2; i>sorted.length-12; i--)
		{
			max[j] = sorted[i];
			j++;
		}
		return max;
	}
}