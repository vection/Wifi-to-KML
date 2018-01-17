import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.math3.geometry.spherical.oned.S1Point;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
/**
 * <<<<<<< NOT USED ANYMORE >>>>>>>
 * <<<<<<< NOT USED ANYMORE >>>>>>>
 * <<<<<<< NOT USED ANYMORE >>>>>>>
 * Database - represents database class.  
 * @author Aviv
 *
 */
public class Database {
	/**
	 * Create Database csv format
	 * @param elements  - samples
	 */
	public static void CreateDatabase(ArrayList<Data> elements) {
		//boolean alreadyExists = new File(path).exists();
		File file = new File("Database.csv");
	    String path = file.getAbsolutePath();
		file.delete(); // Delete exist file
		try 
		  {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, true), ',');
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
			csvOutput.write("");
			csvOutput.write(elements.get(1).GetID());
			csvOutput.endRecord();
		    for(int i=1; i<=elements.size(); i++) // splitting the whole string and writing the details in specific order.
		    {
		    	  DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		    	  String time = df.format(elements.get(i).getTime());
		          csvOutput.write(time);
		          csvOutput.write(elements.get(i).GetID());
		          csvOutput.write(elements.get(i).getLat());
		          csvOutput.write(elements.get(i).getLon());
		          csvOutput.write(elements.get(i).getAlt());
		          csvOutput.write(elements.get(i).getSSID());
		          csvOutput.write(elements.get(i).getMAC());
		          csvOutput.write(elements.get(i).getFrequency());
		          csvOutput.write(elements.get(i).getSignal());
		          csvOutput.endRecord();
		    }
		    
		    csvOutput.close();
		  }
		  catch (IOException e) 
			{
				e.printStackTrace();
			} 
	}
	/**
	 * Read from data (my format) - allowing to read my format inorder to get into objects.
	 * @param getfile
	 * @return
	 */
	public static String[] ReadFromData(String getfile) {
		String[] details = new String[9];
		try {
		     CsvReader products = new CsvReader(getfile); // Path to second CSV file.
		     products.readHeaders(); // reading now all details from the csv.
		   
		     while (products.readRecord())
		     {
		    	 if(products.get("Time") == null) {
		    		 
		    	 }
		    	 else {
			          details[0] += ","+products.get("Time");
			          details[1] += " "+products.get("MAC"); 
			          details[2] += " "+products.get("Signal");
			          if(!products.get("SSID").isEmpty()) { 
			            details[3] += " "+products.get("SSID").replaceAll("\\s+","");
			          }
			          else {
			        	  details[3] += " "+"NULL";
			          }
			          details[4] += " "+products.get("Lon");
			          details[5] += " "+products.get("Lat");
			          details[6] += " "+products.get("Ferquency");
			          details[7] += " "+products.get("Alt");
			          details[8] += ","+products.get("ID");
		         }
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
	 * Creating objects by getting the data
	 * @param details - array string
	 * @return objects in type "Data"
	 * @throws ParseException
	 */
	public static ArrayList<Data> CreateData(String[] details) throws ParseException {
		ArrayList<Data> elements = new ArrayList<>();
		Date time = new Date();
		String[] sp,sp1,sp2,sp3,sp4,sp5,sp6,sp7,sp8;
	 	sp = details[0].split(",");
	 	sp1 = details[1].split(" ");
	    sp2 = details[2].split(" ");
	    sp3 = details[3].split(" ");
	    sp4 = details[4].split(" ");
	    sp5 = details[5].split(" ");
	    sp6 = details[6].split(" ");
	    sp7 = details[7].split(" ");
	    sp8 = details[8].split(",");
	    for(int i=2; i<sp1.length; i++) // splitting the whole string and writing the details in specific order.
        {
	    	SimpleDateFormat theSameDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	    	time = ToCSV.SetDate(sp[i]);
	    	try {
	    	  Data p = new Data(sp3[i], time, sp4[i], sp5[i], sp7[i], sp1[i], sp2[i], sp6[i], sp8[i]);
	    	  elements.add(p);
	    	}
	    	catch(Exception e1) {
	    		System.out.println("Error with loading all database.");
	    	}
        }
	    return elements;
	}
	
}
