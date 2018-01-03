import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference.Elements;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Graphics extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	ArrayList<Data> melements = new ArrayList<>();

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
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Graphics() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		textField = new JTextField();
		textField.setColumns(10);
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
				
			}
		});
		JButton btnNewButton = new JButton("CSV");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String path = textField.getText();
				if(path != null) {
					ArrayList<Data> elements = new ArrayList<>();
					String[] details;
					details = ReadCSV.ReadFromCSV(path);
					elements = ToCSV.CreateData(details);
					ToCSV.CreateCSV(elements, "Wifi log");
					for(int i=0; i<elements.size(); i++) {
						melements.add(elements.get(i));
					}
				}
			}
		});
		
		JButton button = new JButton("KML");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = textField.getText();
				if(path != null) {
				      ArrayList<Data> elements = new ArrayList<>();
				      String[] details;
				      details = ReadCSV.ReadFromCSV(path);
				      elements = ToCSV.CreateData(details);
				      ToKML.CreateKML(elements);
				      for(int i=0; i<elements.size(); i++) {
							melements.add(elements.get(i));
						}
				}
			}
		});
		
		JLabel lblSorting = new JLabel("Sorting:");
		
		JButton btnTime = new JButton("Time");
		btnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
					    try {
					    	ArrayList<Data> elements;					    	
							elements = ToCSV.sortBy(melements, "time");
							ToCSV.CreateCSV(elements, "Wifi time sort");
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
						melements = ToCSV.sortBy(melements, "lon");
						ToCSV.CreateCSV(melements, "Wifi lon sort");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
						melements = ToCSV.sortBy(melements, "lat");
						ToCSV.CreateCSV(melements, "Wifi lat sort");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
						melements = ToCSV.sortBy(melements, "alt");
						ToCSV.CreateCSV(melements, "Wifi alt sort");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
			    details = ReadCSV.ReadFromCSV(textField.getText());
			    ToCSV.AddData(melements, details);
			    System.out.println("Data added!\n");
			}
		});
		
		JLabel lblAlgorithms = new JLabel("Algorithms");
		
		JButton btnAlgo = new JButton("Algo 1");
		btnAlgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(melements.size() > 0) {
				   ToCSV.CreateWCenterCSV(melements);
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
				    Algo2.Algo2(melements); 
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
						melements = ToCSV.sortBy(melements, "signal");
						ToCSV.CreateCSV(melements, "Wifi time sort");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddData))
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnAlgo)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAlgo_1))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnChooseWiggle)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addComponent(lblAlgorithms))
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblSorting)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(btnTime)
											.addComponent(btnLat))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(btnAlt)
											.addComponent(btnLon))))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(btnSignal)
									.addGap(20)))))
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
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(btnLat)
						.addComponent(btnAlt))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAddData)
								.addComponent(lblAlgorithms))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAlgo)
								.addComponent(btnAlgo_1)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnSignal)))
					.addContainerGap(97, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
