
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * MYSQL class - Database.
 * @author Aviv
 *
 */
public class MYSQL {

	private static String url = "jdbc:mysql://localhost:8888/wifis";
	private static String user = "root";
	private static String password = "root";
	private static Connection _con = null;
	private static PreparedStatement preStatement;
	private static Statement statement;
	private static ResultSet result;
	private static Connection con;

	private static void create_table(ArrayList<Data> e, String table) throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			_con = DriverManager.getConnection(url,user, password);
			String myTableName = "CREATE TABLE `"+table+"` ( `Time` TEXT NOT NULL , `ID` TEXT NOT NULL , `Lat` TEXT NOT NULL , `Lon` TEXT NOT NULL , `Alt` TEXT NOT NULL , `SSID` TEXT NOT NULL , `MAC` TEXT NOT NULL , `Ferquency` TEXT NOT NULL , `Signal` TEXT NOT NULL )";
			try {
				PreparedStatement preparedStmt = _con.prepareStatement(myTableName);
				preparedStmt.execute();
				String a = Addsamples(e, table);
				PreparedStatement stmt = _con.prepareStatement(a);
				stmt.execute();
				System.out.println("Database created.");
			}
			catch (SQLException ex) {
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	/**
	 * Add function - add samples to db.
	 * @param e - Arraylist of samples
	 */
	public static void add(ArrayList<Data> e) {
		try {     
			Class.forName("com.mysql.jdbc.Driver");
			_con = DriverManager.getConnection(url,user, password);
			System.out.println("Connected!");
			try {
				boolean exists = tableExist(_con, "samples");
				if(exists) {
					String a = Addsamples(e, "samples");
					PreparedStatement preparedStmt = _con.prepareStatement(a);
					preparedStmt.execute();
					System.out.println("Database created.");
				}
				else {
					create_table(e, "samples");
				}
			}
			catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		}
		catch (SQLException | ClassNotFoundException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
		}
	}
	/**
	 * tableExists checking if table already exists.
	 * @param conn - connection
	 * @param tableName 
	 * @return
	 * @throws SQLException
	 */
	public static boolean tableExist(Connection conn, String tableName) throws SQLException {
		boolean tExists = false;
		try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
			while (rs.next()) { 
				String tName = rs.getString("TABLE_NAME");
				if (tName != null && tName.equals(tableName)) {
					tExists = true;
					break;
				}
			}
		}
		return tExists;
	}
	/**
	 * Function recives arraylist of samples and create SQL string to add samples to db.
	 * @param elements - samples
	 * @param table - name of table
	 * @return String represent SQL command.
	 */
	private static String Addsamples(ArrayList<Data> elements, String table) {
		Data p = elements.get(0);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
  	    String time = df.format(elements.get(0).getTime());
		String ans = "INSERT INTO `"+table+"` (`Time`, `ID`, `Lat`, `Lon`, `Alt`, `SSID`, `MAC`, `Ferquency`, `Signal`) VALUES ('"+time+"','"+p.GetID()+"','"+p.getLat()+"','"+p.getLon()+"','"+p.getAlt()+"','"+p.getSSID()+"','"+p.getMAC()+"','"+p.getFrequency()+"','"+p.getSignal()+"'),";
		String query;
		for(int i=1; i<elements.size()-1; i++) {
			p = elements.get(i);
			time = df.format(elements.get(i).getTime());
			query = "('"+time+"','"+p.GetID()+"','"+p.getLat()+"','"+p.getLon()+"','"+p.getAlt()+"','"+p.getSSID()+"','"+p.getMAC()+"','"+p.getFrequency()+"','"+p.getSignal()+"'),";
			ans += query;
		}
		p = elements.get(elements.size()-1);
		time = df.format(p.getTime());
		query = "('"+time+"','"+p.GetID()+"','"+p.getLat()+"','"+p.getLon()+"','"+p.getAlt()+"','"+p.getSSID()+"','"+p.getMAC()+"','"+p.getFrequency()+"','"+p.getSignal()+"')";
		ans+= query;
		return ans;
	}
	/**
	 * Load DB function.
	 * @return Arraylist of objects(samples)
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<Data> load() throws ClassNotFoundException {
		ArrayList<Data> elements = new ArrayList<>();
		try {     
			Class.forName("com.mysql.jdbc.Driver");
			_con = DriverManager.getConnection(url,user, password);
			if(tableExist(_con, "samples")) {
		    	  Date time;
				 String query = "SELECT * FROM samples";
				 
			      Statement st = _con.createStatement();   
			      ResultSet rs = st.executeQuery(query);
			      while (rs.next())
			      {
				      time = ToCSV.SetDate(rs.getString("Time"));
			    	  Data p = new Data(rs.getString("SSID"), time, rs.getString("Lon"), rs.getString("Lat"), rs.getString("Alt"), rs.getString("MAC"), rs.getString("Signal"), rs.getString("Ferquency"), rs.getString("ID"));
			    	  elements.add(p);
			    	  
			      }
			      st.close();
			}
		}
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return elements;
		
	}

}
