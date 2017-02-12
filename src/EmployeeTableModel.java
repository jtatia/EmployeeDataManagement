//package EmployeeTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.table.AbstractTableModel;
//import Employee.Employee;

public class EmployeeTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	private static final int LAST_NAME_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int EMAIL_COL = 2;
	private static final int SALARY_COL = 3;
	protected static final int OBJECT_COL = -1;
	
	private String columnNames[] = {"Last Name", "First Name", "Email", "Salary"};
	private List<Employee> employee;
	
	public EmployeeTableModel(List<Employee> theemployee)
	{
		employee = theemployee;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return employee.size();
	}
	
	@Override
	public String getColumnName(int col){
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Employee temp = employee.get(rowIndex);
		
		switch(columnIndex){
		case LAST_NAME_COL:
			return temp.getLastName();
		case FIRST_NAME_COL:
			return temp.getFirstName();
		case EMAIL_COL:
			return temp.getEmail();
		case SALARY_COL:
			return temp.getSalary();
		case OBJECT_COL:
			return temp;
		default:
			return temp.getLastName();
		
	}	
}
	@Override
	public Class<?> getColumnClass(int c){
		return getValueAt(0,c).getClass();
	}
	
}