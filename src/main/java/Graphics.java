
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * Graphic class - Graphical view of the program.
 * @author Aviv
 *
 */
public class Graphics extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	static ArrayList<Data> melements = new ArrayList<>();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_3;
	private JTextField textField_5;
	private static Path Datapath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Graphics frame = new Graphics();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println("Database is not up correctly.");
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 */
	public Graphics() throws ClassNotFoundException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		textField = new JTextField();
		textField.setColumns(10);
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_4 = new JTextField(); // Minimum
		textField_4.setColumns(10);
		
		textField_3 = new JTextField(); // Maximum
		textField_3.setColumns(10);
		 Connection con;
		 try {
		    Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8888/wifis","root", "root");
			System.out.println("Connected!");
			boolean exists = MYSQL.tableExist(con, "samples");
		    if(exists) {
		    	melements = MYSQL.load();
		    	String s = Integer.toString((Cal.Wifinumber(melements)));
		        textField_1.setText(s);
		        textField_2.setText(Integer.toString(melements.size()));
		        System.out.println("Database loaded.");
		    }
		    else {
		    	System.out.println("There is no database available yet.");
		    }
		}
		 catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}
		JButton btnChooseWiggle = new JButton("Choose wiggle");
		btnChooseWiggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
				chooser.setFileFilter(filter);
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	textField.setText(chooser.getSelectedFile().toString()); 
			      } else {
			        
			      }
			    try {
			    	Connection con;
			    	  Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost:8888/wifis","root", "root");
						boolean exists = MYSQL.tableExist(con, "samples");
					    if(exists) {
					    	 ArrayList<Data> e = new ArrayList<>();
						    	String[] details;
						       details = ReadCSV.ReadFromCSV(chooser.getSelectedFile().toString());
						       e = ToCSV.CreateData(details);
						       for(int i=0; i<e.size(); i++) {
						    	   melements.add(e.get(i));
						       }
						       MYSQL.add(e);
						       System.out.println("Database updated.");
						       
					    }
					    else {
					    	 ArrayList<Data> e = new ArrayList<>();
						    	String[] details;
						       details = ReadCSV.ReadFromCSV(chooser.getSelectedFile().toString());
						       e = ToCSV.CreateData(details);
						       melements = (ArrayList<Data>)e.clone();
						       MYSQL.add(e);
						       System.out.println("Database created.");
					    }
			         String s = Integer.toString((Cal.Wifinumber(melements)));
			         textField_1.setText(s);
			         textField_2.setText(Integer.toString(melements.size()));
			    }
			    catch (Exception e) {
			    }
				
			}
		});
		JButton btnNewButton = new JButton("CSV");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = textField.getText();
				if(melements.size() == 0) {
		                 System.out.println("There is no elements in database.");
					     
					}
					else {
						ToCSV.CreateCSV(melements, "Wifi log");
						String s = Integer.toString((Cal.Wifinumber(melements)));
					     textField_1.setText(s);
					     textField_2.setText(Integer.toString(melements.size()));
					}
			}
		});
		
		JButton button = new JButton("KML");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = textField.getText();
				if(melements.isEmpty()) {
					System.out.println("There is no elements.");
				}
				else {
				      ToKML.CreateKML(melements);
				}
			}
		});
		
		JLabel lblSorting = new JLabel("Sorting:");
		
		JButton btnTime = new JButton("Time");
		btnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
					    try {
					    	ArrayList<Data> elements = (ArrayList<Data>) melements.clone();	
					    	if(textField_3.getText().isEmpty()  == true && textField_4.getText() .isEmpty() == true) { 
							    elements = ToCSV.sortBy(elements, "time");
							    ToCSV.CreateCSV(elements, "Wifi time sort");
					    	}
					    	else {
					    		try {
					    		elements = ToCSV.Filter(elements, "time", textField_4.getText(), textField_3.getText());
					    		elements = ToCSV.sortBy(elements, "time");
							    ToCSV.CreateCSV(elements, "Wifi time sort by filter");
					    		}
				    		    catch (ParseException e1) {
								System.out.println("There is no elements with this value.");
							    }
					    	}
						} catch (ParseException e1) {
							System.out.println("There is no elements with this value.");
						}
				}
				else {
					System.out.println("No elements \n");
				}
			}
		});
		
		JButton btnLon = new JButton("Lon");
		btnLon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
				    try {
				    	ArrayList<Data> elements = (ArrayList<Data>) melements.clone();	
				    	if(textField_3.getText().isEmpty()  == true && textField_4.getText() .isEmpty() == true) { 
						    elements = ToCSV.sortBy(elements, "lon");
						    ToCSV.CreateCSV(elements, "Wifi lon sort");
				    	}
				    	else {
				    		try {
				    		elements = ToCSV.Filter(elements, "lon", textField_4.getText(), textField_3.getText());
				    		elements = ToCSV.sortBy(elements, "lon");
						    ToCSV.CreateCSV(elements, "Wifi lon sort by filter");;
				    		}
			    		    catch (ParseException e1) {
							System.out.println("There is no elements with this value.");
						    }
				    	}
					} catch (ParseException e1) {
						System.out.println("There is no elements with this value.");
					}
			   }
				else {
					System.out.println("No elements \n");
				}
			}
		});
		
		JButton btnLat = new JButton("Lat");
		btnLat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
				    try {
				    	ArrayList<Data> elements = (ArrayList<Data>) melements.clone();		
				    	if(textField_3.getText().isEmpty()  == true && textField_4.getText() .isEmpty() == true) {  
						    elements = ToCSV.sortBy(elements, "lat");
						    ToCSV.CreateCSV(elements, "Wifi lat sort");
				    	}
				    	else {
				    		try {
				    		elements = ToCSV.Filter(elements, "lat", textField_4.getText(), textField_3.getText());
				    		elements = ToCSV.sortBy(elements, "lat");
						    ToCSV.CreateCSV(elements, "Wifi lat sort by filter");
				    		}
			    		    catch (ParseException e1) {
							System.out.println("There is no elements with this value.");
						    } 
				    	}
					} catch (ParseException e1) {
						System.out.println("There is no elements with this value.");
					}
			   }
				else {
					System.out.println("No elements \n");
				}
			}
		});
		
		JButton btnAlt = new JButton("Alt");
		btnAlt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
				    try {
				    	ArrayList<Data> elements = (ArrayList<Data>) melements.clone();	
				    	if(textField_3.getText().isEmpty()  == true && textField_4.getText() .isEmpty() == true) { 
						    elements = ToCSV.sortBy(elements, "alt");
						    ToCSV.CreateCSV(elements, "Wifi alt sort");
				    	}
				    	else {
				    		try {
				    		elements = ToCSV.Filter(elements, "alt", textField_4.getText(), textField_3.getText());
				    		elements = ToCSV.sortBy(elements, "alt");
						    ToCSV.CreateCSV(elements, "Wifi alt sort by filter");
				    		}
			    		    catch (ParseException e1) {
							System.out.println("There is no elements with this value.");
						    }
				    	}
					} catch (ParseException e1) {
						System.out.println("There is no elements with this value.");
					}
			    }
				else {
					System.out.println("No elements \n");
				}
			}
		});
		
		JButton btnAddData = new JButton("Add Data");
		btnAddData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
				chooser.setFileFilter(filter);
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	textField.setText(chooser.getSelectedFile().toString()); 
			      } else {
			        
			      }
			    
			    
			    String[] details;
			    try {
			       details = ReadCSV.ReadFromCSV(chooser.getSelectedFile().toString());
			       ToCSV.AddData(melements, details);
			       System.out.println("Data added!\n");
			       Database.CreateDatabase(melements);
			       String s = Integer.toString((Cal.Wifinumber(melements)));
				     textField_1.setText(s);
				     textField_2.setText(Integer.toString(melements.size()));
			    }
			    catch (Exception e1) {
			    	System.out.println("Path is not good.");
			    }
			}
		});
		
		JLabel lblAlgorithms = new JLabel("Algorithms");
		
		JButton btnAlgo = new JButton("Algo 1");
		btnAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
					ArrayList<Data> elements = (ArrayList<Data>) melements.clone();	
				   ToCSV.CreateWCenterCSV(elements);
				}
				else {
					System.out.println("No elements \n");
				}
			}
		});
		
		JButton btnAlgo_1 = new JButton("Algo 2");
		btnAlgo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
					ArrayList<Data> elements = (ArrayList<Data>) melements.clone();	
				    Algo2.Algo2(elements); 
				}
				else {
					System.out.println("No elements \n");
				}
			}
		});
		
		JButton btnSignal = new JButton("Signal");
		btnSignal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
				    try {
				    	ArrayList<Data> elements = (ArrayList<Data>) melements.clone();	
				    	if(textField_3.getText().isEmpty() == true && textField_4.getText().isEmpty() == true) { 
						    elements = ToCSV.sortBy(elements, "signal");
						    ToCSV.CreateCSV(elements, "Wifi signal sort");
				    	}
				    	else {
				    		try {
				    		elements = ToCSV.Filter(elements, "signal", textField_4.getText(), textField_3.getText());
				    		elements = ToCSV.sortBy(elements, "signal");
						    ToCSV.CreateCSV(elements, "Wifi signal sort by filter");
				    		}
				    		catch (ParseException e1) {
								System.out.println("There is no elements with this value.");
							}
				    	}
					} catch (ParseException e1) {
						System.out.println("There is no elements with this value.");
					}
			}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Statistics:");
		
		JLabel lblWifiNumber = new JLabel("Wifi number");
		
		JLabel lblTotalSamples = new JLabel("Total samples");
		
		JLabel lblFilter = new JLabel("Filter:");
		
		JLabel lblMinimum = new JLabel("Minimum");
		
		JLabel lblMaximum = new JLabel("Maximum");
		
		JButton btnDeleteAll = new JButton("Delete All");
		btnDeleteAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				melements.clear();
				    try {
				    	System.out.println(Datapath);
				    	Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:8888/wifis","root", "root");
				    	 Statement s = con.createStatement();

				    	 String delete ="TRUNCATE TABLE samples";
				    	 s.executeUpdate(delete);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			         textField_1.setText("0");
			         textField_2.setText("0");
				System.out.println("All samples removed from database.");
			}
		});
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		
		JButton btnByDevice = new JButton("By device");
		btnByDevice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String device = textField_5.getText();
				if(device.isEmpty() == false ) {
				    ArrayList<Data> elements = (ArrayList<Data>) melements.clone();
				    for(int i=0; i<elements.size(); i++) {
					   if(elements.get(i).GetID().equals(device) == false) {
						elements.remove(i);
					   }
				    }
				    if(elements.size() > 0) {
				       ToCSV.CreateCSV(elements, "Wifi device filtering");
				    }
				    else {
					   System.out.println("Device not found!\n");
				    }
				}
				else {
					System.out.println("Empty field");
				}
;			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddData)
								.addComponent(btnDeleteAll))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnChooseWiggle)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnAlgo)
										.addComponent(lblAlgorithms))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnAlgo_1))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFilter))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblMaximum)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblMinimum)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(37)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnByDevice)
								.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(lblSorting)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(btnTime)
									.addComponent(btnLat))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(btnLon)
									.addComponent(btnAlt))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSignal)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addGap(9))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblWifiNumber)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblTotalSamples)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(20)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewButton)
							.addComponent(lblSorting))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnChooseWiggle)
									.addComponent(btnTime)
									.addComponent(btnLon)))))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(button)
								.addComponent(btnLat)
								.addComponent(btnAlt))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnSignal)
							.addGap(28)
							.addComponent(lblNewLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddData)
							.addGap(21)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAlgorithms)
								.addComponent(btnDeleteAll))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAlgo)
								.addComponent(btnAlgo_1))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWifiNumber)
						.addComponent(lblFilter))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTotalSamples)
						.addComponent(lblMinimum)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnByDevice))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaximum)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
