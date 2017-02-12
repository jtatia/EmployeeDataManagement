import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.mysql.jdbc.PreparedStatement;

public class UserDAO {
	private Connection myConn;
	private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
	
	public UserDAO() throws Exception{
		Properties properties = new Properties();
		properties.load(new FileInputStream("demo.properties"));
		
		String dburl = properties.getProperty("dburl");
		String user = properties.getProperty("user");
		String pass = properties.getProperty("pass");
		try {
			myConn = DriverManager.getConnection(dburl,user,pass);
			System.out.println("Connected");
			}catch(Exception e){
				System.out.println("Connection not estabilished");
				e.printStackTrace();
			}
	}
	
	public boolean authenticate(User th){
		
		String ptPassword = th.getPassword();
		
		PreparedStatement stmt = null;
		
		try{
			String sql = "select * from users where id=?";
			
			stmt = myConn.prepareStatement(sql);
		}finally{
			stmt.close();
		}
		
		boolean result = passwordEncryptor.checkPassword(ptPassword, encrypted);
		
		return result;
	}
}
