
public class Node {
	Data curr;
	double value;
     public Node(Data curr, double value) {
    	 this.curr = curr;
    	 this.value = value;
     }
     
     public Data getData() {
    	 return this.curr;
     }
     public double getValue() {
    	 return this.value;
     }
}
