package testRequetes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import testRequetes.testConnection.TestConnection;

public class TestRequetes extends JFrame{
	private JTable table = new JTable();
	private JLabel label = new JLabel("Initialisation");
	
    // the class implementing the connection with the database
	private TestConnection tConn = TestConnection.getInstance();
	
	public TestRequetes(){
		super();

		// the different components we need
		JPanel content = new JPanel();
		JPanel top = new JPanel();
		JTextArea jta= new JTextArea(3,1);
		JButton button = new JButton("Tester !");
		
		button.setPreferredSize(new Dimension(20,30));

		jta.setPreferredSize(new Dimension(250,90));
		
		
		// Provisional config of table:
		Object[][] data={{}};
	    String  title[] = {};
	    
	    table = new JTable(data, title);
	    
		
		// Class for button and its listener
		class ButtonListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				long start = System.currentTimeMillis();
				table.setModel(tConn.outputQuery(jta.getText()));
				long time = System.currentTimeMillis() - start;
				label.setText("La requête a été exécutée en "+time+" ms et a retourné "+table.getModel().getRowCount()+" ligne(s).");
			}
		}
	    button.addActionListener(new ButtonListener());
		
		
		// Describes the top area of the window
		BorderLayout bl0 = new BorderLayout();
		top.setLayout(bl0);
		top.add(new JScrollPane(jta), BorderLayout.SOUTH);
		top.add(button,BorderLayout.NORTH);	
		
		// Integrates all the components into the frame
		BorderLayout bl = new BorderLayout();
		content.setLayout(bl);
		content.add(top, BorderLayout.NORTH);
		content.add(new JScrollPane(table), BorderLayout.CENTER);
		content.add(label, BorderLayout.SOUTH);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("JTable");
	    this.setSize(500, 400);
		this.add(content);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new TestRequetes();
	}

}
