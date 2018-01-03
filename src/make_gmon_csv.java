
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class make_gmon_csv {

	private JFrame frame;
	private JTextField path_txt;
	private JButton btnGo;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					make_gmon_csv window = new make_gmon_csv();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public make_gmon_csv() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 479, 277);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		path_txt = new JTextField();
		path_txt.setBounds(12, 89, 298, 22);
		frame.getContentPane().add(path_txt);
		path_txt.setColumns(10);
		
		JButton btnSelectFolder = new JButton("Select Folder");
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			        path_txt.setText(chooser.getSelectedFile().toString()); 
			      } else {
			        
			      }
				
			}
		});
		btnSelectFolder.setBounds(322, 88, 127, 25);
		frame.getContentPane().add(btnSelectFolder);
		
		btnGo = new JButton("Go");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnGo.setVisible(false);
			}
		});
		btnGo.setBounds(185, 143, 97, 25);
		frame.getContentPane().add(btnGo);
		
		
	}
}
