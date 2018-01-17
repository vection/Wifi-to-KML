
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Class represents "Calculate" - which helping to figure Algorithm 1 & one function of statistics.
 * @author Aviv
 *
 */
public class Cal {
	/**
	 * Algo1 finding center point
	 * @param elements ArrayList of Data
	 * @param MAC - represents MAC address
	 * @return Point3D represents center of all points.
	 */
	public static Point3D FindCenter(ArrayList<Data> elements,String MAC) {
		double [] arr = new double[3];
		int counter=0;
		for(int i=0; i<elements.size(); i++) {
			if(elements.get(i).getSSID().equals(MAC)) {
				Data get = elements.get(i);
				arr[0] += Double.parseDouble(get.getLon());
				arr[1] += Double.parseDouble(get.getLat());
				arr[2] += Double.parseDouble(get.getAlt());
				counter++;
			}
		}
		if(counter > 0 ) {
			arr[0] = arr[0]/counter;
			arr[1] = arr[1]/counter;
			arr[2] = arr[2]/counter;
		}
		Point3D p = new Point3D(arr[0], arr[1], arr[2]);
		return p;
	}
	/**
	 * Finding weight function. for Algo1
	 * @param elements arraylist of data
	 * @param MAC represents MAC address
	 * @return Point3D
	 */
	public static Point3D FindWeight(ArrayList<Data> elements,String MAC) {
		double [] arr = new double[3];
		double weight=0;
		double wsum=0;
		int counter=0;
		for(int i=0; i<elements.size(); i++) {
			if(elements.get(i).getMAC().equals(MAC)) {
				Data get = elements.get(i);
				weight = Double.parseDouble(elements.get(i).getSignal());
				weight = FindWeight(weight);
				wsum += weight;
				arr[0] += Double.parseDouble(get.getLon())*weight;
				arr[1] += Double.parseDouble(get.getLat())*weight;
				arr[2] += Double.parseDouble(get.getAlt())*weight;	
				counter++;
			}
		}
		if(counter > 0 ) {
			arr[0] = arr[0]/wsum;
			arr[1] = arr[1]/wsum;
			arr[2] = arr[2]/wsum;
		}
		Point3D p = new Point3D(arr[0], arr[1], arr[2]);
		return p;
	}
/**
 * Finding weight by given signal value.
 * @param Signal
 * @return
 */
	  public static double FindWeight(double Signal) {
	    double sig = Math.abs(Signal);
	    double temp = Math.pow(sig, 2.0);
	    return temp;
	  }
	  /**
	   * Wifi number - represents how many wifi address we have.
	   * @param elements
	   * @return number of wifi's
	   */
	  
	  public static int Wifinumber(ArrayList<Data> elements) {
		  SortedSet s=new TreeSet();
		  for(int i=0; i<elements.size(); i++) {
			  s.add(elements.get(i).toString());
		  }
		  return s.size();
	  }
}
