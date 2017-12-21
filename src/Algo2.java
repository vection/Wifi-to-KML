import java.util.ArrayList;
import java.util.Arrays;
/**
 * Class represents Algorithem 2 solution.
 * @author Aviv
 *
 */

public class Algo2 {
	/**
	 * By given MAC address function taking out all samples that recognize with this MAC address.
	 * @param elements 
	 * @param MAC
	 * @return Arraylist of Data (only MAC samples.)
	 */
	public static ArrayList<Data> FindMAC(ArrayList<Data> elements, String MAC) {
		ArrayList<Data> p = new ArrayList<>();
		int size = elements.size();
		if(size < 0) { throw new RuntimeException("ERROR: empty"); }
		for(int i=0; i< size; i++) {
			Data cur = elements.get(i);
			if(MAC.equals(cur.getMAC())) {
				p.add(cur);
			}
		}
		return p;
	}
	/**
	 * Finding the difference 
	 * @param elements
	 * @param curr
	 * @return difference.
	 */
	public static double FindDiff(ArrayList<Data> elements, Data curr) {
		double ans = 1;
		double diff = 0;
		for(int i=0; i<elements.size(); i++) {
			Data temp = elements.get(i);
			if(temp.equals(curr)) { continue; }
			else {
				double signal = Double.parseDouble(curr.getSignal());
				diff = Double.parseDouble(temp.getSignal());
				diff = Math.abs(diff-signal);
				ans *= (signal-diff)/signal;
			}
		}
		return ans;
		
	}
	/**
	 * Starting function to Algorithem 2.
	 * @param elements
	 * @param path represents file path
	 */
	public static void Algo2(ArrayList<Data> elements, String path) {
		ArrayList<Data> p = new ArrayList<>();
		Point3D point;
		for(int i=0; i<elements.size(); i++) {
			Data curr = elements.get(i);
		        p = FindMost(elements, curr.getMAC());
		        point = Cal.FindWeight(p, curr.getMAC());
		        curr.setLon(point.getLon());
		        curr.setLat(point.getLat());
		        curr.setAlt(point.getAlt());
		}
		ToCSV.CreateCSV(elements, path);
	}
	/**
	 * Algorithem 2 calculates. finding the most 3 strongest samples.
	 * @param elements
	 * @param MAC
	 * @return Arraylist with maximum 3 samples.
	 */
	public static ArrayList<Data> FindMost(ArrayList<Data> elements, String MAC) {
		ArrayList<Data> p = FindMAC(elements,MAC);
		ArrayList<Data> result = new ArrayList<>();
		Node[] arr = new Node[p.size()];
		double[] arr2 = new double[p.size()];
		double value;
		if(arr.length < 4) {
			return p;
		}
		else {
		         for(int i=0; i<p.size(); i++) {
			        value = FindDiff(p, p.get(i));
			        Node s = new Node(p.get(i),value);
			        arr[i] = s;
			        arr2[i] = value;
		         }
		         Arrays.sort(arr2);
		         for(int i=0; i<3; i++) {
		        	 for(int j=0; j<p.size(); j++) {
		        		 if(arr[j].getValue() == arr2[i]) {
		        			 result.add(arr[j].getData());
		        		 }
		        	 }
		         }
		         return result;
		         
		}
	}
}

