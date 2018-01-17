import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
	 * @param details - represent string array with information.
	 * @return Arraylist of type "Data".
	 */
	public static ArrayList<Data> CreateData(String[] details) {
		ArrayList<Data> elements = new ArrayList<>();
		Date time = new Date();
		String[] sp,sp1,sp2,sp3,sp4,sp5,sp6,sp7,sp8;
	 	sp = details[0].split(" ");
	 	sp1 = details[1].split(" ");
	    sp2 = details[2].split(" ");
	    sp3 = details[3].split(" ");
	    sp4 = details[4].split(" ");
	    sp5 = details[5].split(" ");
	    sp6 = details[6].split(" ");
	    sp7 = details[7].split(" ");
	    String ID = details[8];
	    int d = 1;
	    for(int i=1; i<sp1.length-1; i++) // splitting the whole string and writing the details in specific order.
        {
	    	//time = SetDate(sp[i]);
	    	String time1 = (sp[d++]+" "+sp[d++]);
	    	//System.out.println(time1);
	    	time = SetDate(time1);
	    	//System.out.println(time.toString());
	      	Data p = new Data(sp3[i], time, sp4[i], sp5[i], sp7[i], sp1[i], sp2[i], sp6[i], ID);
	 	    elements.add(p);
        }
	    return elements;
	}
	/**
	 * CreateCSV gets Arraylist of Data object and creates CSV file with the requested format.
	 * @param elements - Arraylist with all objects
	 */
	public static void CreateCSV(ArrayList<Data> elements , String name) {
		//boolean alreadyExists = new File(path).exists();
		File file = new File(name+".csv");
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
		    for(int i=0; i<elements.size(); i++) // splitting the whole string and writing the details in specific order.
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
		    System.out.println("CSV file created here -  "+path);
		  }
		  catch (IOException e) 
			{
				e.printStackTrace();
			} 
	}
	/**
	 * Sortby gets ArrayList of object Data, sort it by signal/time/lat/lon/alt
	 * @param elements which represents the arraylist of data.
	 * @param s which represents the sort option
	 * @throws ParseException 
	 */
	public static ArrayList<Data> sortBy(ArrayList<Data> elements, String s) throws ParseException
	{
		int[] max = new int[10];
		double[] sorted = new double[elements.size()];
		ArrayList<Date> dsorted = new ArrayList<>();
		ArrayList<Data> asorted = new ArrayList<>();
		if(s.equalsIgnoreCase("signal"))
		{
		   for(int i=0; i<elements.size(); i++)
		   {
			 sorted[i] = Double.parseDouble(elements.get(i).getSignal());
		   }
		   Arrays.sort(sorted);
		   asorted = Sort(elements,sorted,"signal");
		}
		else if(s.equalsIgnoreCase("lat"))
		{
		   for(int i=0; i<elements.size(); i++)
		   {
			 sorted[i] = Double.parseDouble(elements.get(i).getLat());
		   }
		   Arrays.sort(sorted);
		   asorted = Sort(elements,sorted,"lat");
		}
		else if(s.equalsIgnoreCase("lon"))
		{
		   for(int i=0; i<elements.size(); i++)
		   {
			 sorted[i] = Double.parseDouble(elements.get(i).getLon());
		   }
		   Arrays.sort(sorted);
		   asorted = Sort(elements,sorted,"lon");
		}
		else if(s.equalsIgnoreCase("alt"))
		{
		   for(int i=0; i<elements.size(); i++)
		   {
			 sorted[i] = Double.parseDouble(elements.get(i).getAlt());
		   }
		   Arrays.sort(sorted);
		   asorted = Sort(elements,sorted,"alt");
		}
		else if(s.equalsIgnoreCase("time")) {
			for(int i=0; i<elements.size(); i++) {
				dsorted.add(elements.get(i).getTime());
			}
			Collections.sort(dsorted);
			for(int i=0; i<dsorted.size(); i++) // splitting the whole string and writing the details in specific order.
 		    {
 		    	int id = FindID(elements,dsorted.get(i));
 		    	if(id > -1) {
 		          asorted.add(elements.get(id));
 		    	}
 		    	elements.remove(id);
		    }
		}
		return asorted;
	}
	/**
	 * 
	 * @param elements represents data arraylist
	 * @param arr sorted array.
	 * @param s represents sorting option
	 * @return
	 */
	public static ArrayList<Data> Sort(ArrayList<Data> elements, double[] arr, String s) {
		ArrayList<Data> temp = new ArrayList<>();
		ArrayList<Data> helper = elements;
		if(s.equals("alt")) {
		    for(int i =0; i<arr.length; i++) {
			    for(int j =0; j<helper.size(); j++) {
				    if(Double.parseDouble(helper.get(j).getAlt()) == arr[i]) {
					   temp.add(helper.get(j));
					  helper.remove(helper.get(j));
				    }
			    }
		    }
		}
		else if(s.equals("signal")) {
		    for(int i =0; i<arr.length; i++) {
			    for(int j =0; j<helper.size(); j++) {
				    if(Double.parseDouble(helper.get(j).getSignal()) == arr[i]) {
					   temp.add(helper.get(j));
					   helper.remove(helper.get(j));
				    }
			    }
		    }
		}
		else if(s.equals("lat")) {
		    for(int i =0; i<arr.length; i++) {
			    for(int j =0; j<helper.size(); j++) {
				    if(Double.parseDouble(helper.get(j).getLat()) == arr[i]) {
					   temp.add(helper.get(j));
					   helper.remove(helper.get(j));
				    }
			    }
		    }
		}
		else if(s.equals("lon")) {
		    for(int i =0; i<arr.length; i++) {
			    for(int j =0; j<helper.size(); j++) {
				    if(Double.parseDouble(helper.get(j).getLon()) == arr[i]) {
					   temp.add(helper.get(j));
					   helper.remove(helper.get(j));
				    }
			    }
		    }
		}
		return temp;
	}
	/**
	 * Funtion that transfering String to Date.
	 * @param date 
	 * @return
	 */
	public static Date SetDate(String date) 
	{
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 

        Date startDate = null;
		try {
			startDate = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return startDate;
	}
	/**
	 * AddData funtion
	 * @param elements - ArrayList of "Data"
	 * @param details - String array represents data.
	 * @param path - path to new file.
	 */
	public static void AddData(ArrayList<Data> elements, String[] details) {
		Date time = new Date();
		String[] sp,sp1,sp2,sp3,sp4,sp5,sp6,sp7;
	 	sp = details[0].split(" ");
	 	sp1 = details[1].split(" ");
	    sp2 = details[2].split(" ");
	    sp3 = details[3].split(" ");
	    sp4 = details[4].split(" ");
	    sp5 = details[5].split(" ");
	    sp6 = details[6].split(" ");
	    sp7 = details[7].split(" ");
	    String ID = details[8];
	    int d = 1;
	    for(int i=1; i<sp1.length-1; i++) // splitting the whole string and writing the details in specific order.
        {
	    	//time = SetDate(sp[i]);
	    	String time1 = (sp[d++]+" "+sp[d++]);
	    	time = SetDate(time1);
	      	Data p = new Data(sp3[i], time, sp4[i], sp5[i], sp7[i], sp1[i], sp2[i], sp6[i], ID);
	 	    elements.add(p);
        }
	}
	/**
	 * Create Weight CSV file - Algo1.
	 * @param elements
	 * @param path
	 */
     public static void CreateWCenterCSV(ArrayList<Data> elements) {
		
		 // Path to output CSV file.
		Point3DWeight wpoint;
		File file = new File("WCenter.csv");
		String path = file.getAbsolutePath();
		try 
		  {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(file, true), ',');
			csvOutput.write("Time");
			csvOutput.write("Device ID");
			csvOutput.write("WCenter Lat");
			csvOutput.write("WCenter Lon");
   		    csvOutput.write("WCenter Alt");
			csvOutput.write("SSID");
			csvOutput.write("MAC");
			csvOutput.write("Ferquency");
			csvOutput.write("Signal");
			csvOutput.endRecord();
			csvOutput.write("");
			csvOutput.write(elements.get(1).GetID());
			csvOutput.endRecord();
		    for(int i=1; i<elements.size(); i++) // splitting the whole string and writing the details in specific order.
		    {
		    	Data temp = elements.get(i);
		    	String x,y,z;
		    	wpoint = temp.setWPoint(Cal.FindWeight(elements, temp.getMAC()), Cal.FindWeight(Double.parseDouble(temp.getSignal())));
		    	x = Double.toString(wpoint.x);
		    	y = Double.toString(wpoint.y);
		    	z = Double.toString(wpoint.z);
		    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		    	  String time = df.format(elements.get(i).getTime());
		          csvOutput.write(time);
		          csvOutput.write("");
		          csvOutput.write(y);
		          csvOutput.write(x);
		          csvOutput.write(z);
		          csvOutput.write(elements.get(i).getSSID());
		          csvOutput.write(elements.get(i).getMAC());
		          csvOutput.write(elements.get(i).getFrequency());
		          csvOutput.write(elements.get(i).getSignal());
		          csvOutput.endRecord();
		    }
		    
		    csvOutput.close();
		    System.out.println("Weight Center Point CSV file created here -  "+path);
		  }
		  catch (IOException e) 
			{
				e.printStackTrace();
			} 
	}
     /**
      * Helping function to get ID of object.
      * @param elements Arraylist of Data.
      * @param s Data object
      * @return
      */
     public static int FindID(ArrayList<Data> elements, Date s) {
    		int id=-1;
    	 for(int i=0; i<elements.size(); i++) {
    			if(s == elements.get(i).getTime()) {
    				id = i;
    			}
    		}
			return id;
     }
     /**
      * Filter funtion.
      * @param elements - samples
      * @param sortype - lon,lat,alt,signal, time
      * @param min - min value
      * @param max - max value
      * @return
      */
    public static ArrayList<Data> Filter(ArrayList<Data> elements, String sortype ,String min, String max) {
    	ArrayList<Data> newelements = new ArrayList<>();
    	if(!elements.isEmpty()) {
    	  if(sortype.equals("lon")) {
    		double lon,min1, max1;
    		min1 = Double.parseDouble(min);
    		max1 = Double.parseDouble(max);
    	      for(int i=0; i<elements.size(); i++) {
    	    	  Data temp = elements.get(i);
    	    	  lon = Double.parseDouble(temp.getLon());
    	    	  if(lon >= min1 && lon <= max1) {
    	    		newelements.add(elements.get(i));
    	    	  }
    	    		  
    	       }
    		
    	  }
    	  else if(sortype.equals("alt")) {
    		double alt,min1, max1;
    		min1 = Double.parseDouble(min);
    		max1 = Double.parseDouble(max);
    	      for(int i=0; i<elements.size(); i++) {
    	    	  Data temp = elements.get(i);
    	    	  alt = Double.parseDouble(temp.getAlt());
    	    	  if(alt >= min1 && alt <= max1) {
    	    		newelements.add(elements.get(i));
    	    	  }
    	    		  
    	       }
    		
    	  }
    	  else if(sortype.equals("signal")) {
    		int sig,min1, max1;
    		min1 = Integer.parseInt(min);
    		max1 = Integer.parseInt(max);
    	      for(int i=0; i<elements.size(); i++) {
    	    	  Data temp = elements.get(i);
    	    	  sig = Integer.parseInt(temp.getSignal());
    	    	  if(sig >= min1 && sig <= max1) {
    	    		newelements.add(elements.get(i));
    	    	  }
    	    		  
    	       }
    		
    	  }
    	  else if(sortype.equals("lat")) {
    		double lat,min1, max1;
    		min1 = Double.parseDouble(min);
    		max1 = Double.parseDouble(max);
    	      for(int i=0; i<elements.size(); i++) {
    	    	  Data temp = elements.get(i);
    	    	  lat = Double.parseDouble(temp.getLat());
    	    	  if(lat >= min1 && lat <= max1) {
    	    		newelements.add(elements.get(i));
    	    	  }
    	    		  
    	       }
    		
    	  }
    	  else if(sortype.equals("time")) {
    		long time,min1, max1;
    		min1 = Long.parseLong(min);
    		max1 = Long.parseLong(max);
    	      for(int i=0; i<elements.size(); i++) {
    	    	  Data temp = elements.get(i);
    	    	  time = temp.getlTime();
    	    	  if(time >= min1 && time <= max1) {
    	    		newelements.add(elements.get(i));
    	    	  }
    	    		  
    	       }
    		
    	}
    	}
    	else {
    		System.out.println("There is no elements!");
    	}
    	return newelements;
    }
}
