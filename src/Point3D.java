
public class Point3D {
	double x,y,z;
	
     public Point3D(double x, double y, double z) {
    	 this.x = x;
    	 this.y = y;
    	 this.z = z;
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
     
     public String toString() {
    	 return ("("+this.x+","+this.y+","+this.z+")");
     }
     
}
