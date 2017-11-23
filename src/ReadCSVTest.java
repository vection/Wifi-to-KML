import static org.junit.Assert.*;

import org.junit.Test;


public class ReadCSVTest {

	@Test
	public void testReadFromCSV() {
		ReadCSV RC = new ReadCSV();
		String[] s = RC.ReadFromCSV("C:/Users/Aviv/Desktop/Bdida/Log.csv");
		String[] parts = s[3].split(" ");
		String name = "7-tec";
		if(name.equals(parts[4])) {}
		else { fail("Somthing wrong with reading"); }
			
	}

	@Test
	public void testGetID() {
		ReadCSV RC = new ReadCSV();
		String s = ReadCSV.GetID("C:/Users/Aviv/Desktop/Bdida/WigleWifi_20171031183939.csv");
		String name = "LMY47V";
		if(name.equals(s)) {} 
		else { fail("ID doesnt read properly"); }
	}
}
