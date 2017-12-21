/**
 * Class represent Point3D point + weight value.
 * @author Aviv
 *
 */
public class Point3DWeight {
	double x,y,z,w;
	
     public Point3DWeight(double x, double y, double z, double w) {
    	 this.x = x;
    	 this.y = y;
    	 this.z = z;
    	 this.w = w;
     }
     
     public Point3DWeight(Point3D point, double w) {
    	 this.x = point.getLon();
    	 this.y = point.getLat();
    	 this.z = point.getAlt();
    	 this.w = w;
     }
     
     public double getLon() {
    	 return this.x;
     }
     
     public double getLat() {
    	 return this.y;
     }
     
     public double getAlt() {
    	 return this.z;
     }
     public double getWeight() {
    	 return this.w;
     }
     
     public String toString() {
    	 return ("("+this.x+","+this.y+","+this.z+") Weight: "+this.w);
     }
     
}
