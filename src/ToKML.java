import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
/**
 * This class represents writing to KML format using JAK.
 * All data was taken from CSV file which represents wifi details.
 * @author Aviv
 *
 */


public class ToKML {
	/**
	 * CreateKML - gets ArrayList type of "Data" and create KML file with descrpitions.
	 * @param elements
	 */
	public static void CreateKML(ArrayList<Data> elements) { // Create KML file with the details required.
		final Kml kml = new Kml();
		Document document = kml.createAndSetDocument();
		for(int i=1; i<elements.size(); i++)
		{
		  document.createAndAddPlacemark()
		   .withName(elements.get(i).getSSID())
		   .withDescription("Time: "+elements.get(i).getTime().toString()+"\n Wifi: "+elements.get(i).getSSID()+"\n Signal: "+elements.get(i).getSignal()+"\n Longtitude: "+elements.get(i).getLat()+"\n Attitude: "+elements.get(i).getAlt()+"\n MAC: "+elements.get(i).getMAC()+"\n Frequency: "+elements.get(i).getFrequency())
		   .createAndSetPoint().addToCoordinates(Double.parseDouble(elements.get(i).getLat()), Double.parseDouble(elements.get(i).getAlt()));
		}
		try {
			String name = "Placements.kml";
			kml.marshal(new File(name));
			 System.out.println("KML file created inside your project folder and named "+name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  CreatesortedKML - Create sorted KML file by signal, only the 10 higest valuse.
	 * @param elements
	 * @param max
	 */
	public static void CreatesortedKML(ArrayList<Data> elements, int[] max) {
		int count=0;
		final Kml kml = new Kml();
		Document document = kml.createAndSetDocument();
		int i=1;
		while(count < 9) // taking the 10 most higest values by column number.
		{
			if(Integer.parseInt(elements.get(i).getSignal()) == max[count])
			{
				 document.createAndAddPlacemark()
				   .withName(elements.get(i).getSSID())
				   .withDescription("Time: "+elements.get(i).getTime().toString()+"\n Wifi: "+elements.get(i).getSSID()+"\n Signal: "+elements.get(i).getSignal()+"\n Longtitude: "+elements.get(i).getLat()+"\n Attitude: "+elements.get(i).getAlt()+"\n MAC: "+elements.get(i).getMAC()+"\n Frequency: "+elements.get(i).getFrequency())
				   .createAndSetPoint().addToCoordinates(Double.parseDouble(elements.get(i).getLat()), Double.parseDouble(elements.get(i).getAlt()));
		      count++;
		      i=1;
			}
			i++;
		}
		try {
			kml.marshal(new File("Sorted2.kml"));
			System.out.println("Created");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
}
