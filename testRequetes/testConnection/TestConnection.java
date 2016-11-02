package testRequetes.testConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TestConnection {
	
	private static Connection conn;
	
	// implementation singleton par "initialisation immédiate".
	private static TestConnection instance = new TestConnection();
	
	
	private TestConnection(){
		try {
	        Class.forName("org.postgresql.Driver");
	        String url = "jdbc:postgresql://localhost:5432/Ecole";
	        String user = "postgres";
	        String passwd = "PoufPaf87?jjJ";
	        conn = DriverManager.getConnection(url, user, passwd);	
		} catch (ClassNotFoundException e) {
	        e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public static TestConnection getInstance(){
		return instance;
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	public DefaultTableModel outputQuery(String query){
		// Default values of output
		Object[][] data={{}};
		String[] titles={};
		
		try{
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(query);
			ResultSetMetaData resultMeta = rs.getMetaData();
			int nbCol = resultMeta.getColumnCount();

			titles = new String[nbCol];
			
			// get the titles
			for (int i=1; i <= resultMeta.getColumnCount(); i++)
				titles[i-1]=resultMeta.getColumnName(i);
			
			List<Object[]> l = new ArrayList();
			
			// get the results, convert it into a list:
			while(rs.next()){
				Object[] temp = new Object[nbCol];
				for (int i =1; i <= nbCol; i++){
					temp[i-1]=rs.getObject(i).toString();
				}
				l.add(temp);
			}
			
			rs.close();
			state.close();
			
			data = l.toArray(new Object[l.size()][]);
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR !",JOptionPane.ERROR_MESSAGE);
		} catch (Exception e){
			e.printStackTrace();
		}
		return new DefaultTableModel(data, titles);
	}
	
}
