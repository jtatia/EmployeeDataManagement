//package EmployeeDAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
//import Employee.Employee;

public class EmployeeDAO {
	private Connection myConn;
	
	public EmployeeDAO()throws Exception{
		Properties prop = new Properties();
		prop.load(new FileInputStream("demo.properties"));
		
		String user = prop.getProperty("user");
		String pass = prop.getProperty("pass");
		String dburl = prop.getProperty("dburl");
		try {
		myConn = DriverManager.getConnection(dburl,user,pass);
		System.out.println("Connected");
		}catch(Exception e){
			System.out.println("Connection not estabilished");
			e.printStackTrace();
		}
	}
	
	public List<Employee> getEmployee()throws Exception
	{
		Statement stmt = null;
		ResultSet rs =null;
		List<Employee> list = new ArrayList<>();
		try {
		stmt = myConn.createStatement();
		rs = stmt.executeQuery("select * from employees");
		while(rs.next()){
			Employee temp = convertRowtoEmployee(rs);
			list.add(temp);
		}
		}catch(Exception e){
			System.out.println("1");
			e.printStackTrace();
		}finally{
			if(stmt!=null)
		stmt.close();
			if(rs!=null)
		rs.close();
		}
		return list;
	}
	
	public List<Employee> searchForEmployee(String lastName)throws Exception
	{
		List<Employee> list = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
	try {
		stmt = myConn.prepareStatement("select * from employees where last_name like ?");
		stmt.setString(1, lastName);
		rs = stmt.executeQuery();
		
		while(rs.next())
		{
			Employee temp = convertRowtoEmployee(rs);
			list.add(temp);
		}
	}finally {
		if(rs!=null)
		rs.close();
		if(stmt!=null)
		stmt.close();
	}
		return list;
	}
	
	private Employee convertRowtoEmployee(ResultSet rs){
		Employee emprec=null;
		try {
			emprec = new Employee(rs.getInt("id"),rs.getString("last_name"),rs.getString("first_name"),rs.getString("email"),rs.getBigDecimal("salary"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emprec;
	}
	
	//adding Employee
	
	public void addEmployee(Employee theEmployee)throws Exception
	{
		PreparedStatement stmt = null;
		try {
		String sql = "insert into employees"+" (first_name, last_name, email, salary)"+" values(?, ?, ?, ?)";
		stmt = myConn.prepareStatement(sql);
		stmt.setString(1, theEmployee.getFirstName());
		stmt.setString(2, theEmployee.getLastName());
		stmt.setString(3, theEmployee.getEmail());
		stmt.setBigDecimal(4, theEmployee.getSalary());
		stmt.executeUpdate();
		}finally{
			stmt.close();
		}
	}
	
	public void updateEmployee(Employee theEmployee)throws Exception
	{
		PreparedStatement stmt = null;
		try {
			String sql = "update employees"+" set first_name = ?, last_name = ?, email = ?, salary = ?"+"where id=?";
			stmt = myConn.prepareStatement(sql);
			stmt.setString(1, theEmployee.getFirstName());
			stmt.setString(2, theEmployee.getLastName());
			stmt.setString(3, theEmployee.getEmail());
			stmt.setBigDecimal(4, theEmployee.getSalary());
			stmt.setInt(5, theEmployee.getId());
			stmt.executeUpdate();
		}
		finally{
			stmt.close();
		}
	}
	
	public void deleteEmployee(int Empid)throws Exception
	{
		PreparedStatement stmt = null;
		try {
			String sql = "delete from employees where id=?";
			stmt = myConn.prepareStatement(sql);
			stmt.setInt(1, Empid);
			stmt.executeUpdate();
		}finally {
			stmt.close();
		}
	}
	
	public static void main(String args[])throws Exception
	{
	//	EmployeeDAO emp = new EmployeeDAO();
		//System.out.println(emp.searchForEmployee("doe"));
	}
}
