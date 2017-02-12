//package EmployeeSearchApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import EmployeeDAO.EmployeeDAO;
//import Employee.Employee;
//import EmployeeTableModel.EmployeeTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class EmployeeSearchApp extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameTextField;
	private EmployeeDAO employeeDAO;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeSearchApp frame = new EmployeeSearchApp();
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
	public EmployeeSearchApp() {
		try {
			employeeDAO = new EmployeeDAO();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(this, "Error:"+e,"Error",JOptionPane.ERROR_MESSAGE);
		}
		setTitle("Employee Search App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterLastName = new JLabel("Enter Last Name");
		panel.add(lblEnterLastName);
		
		lastNameTextField = new JTextField();
		panel.add(lastNameTextField);
		lastNameTextField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	try {			
				String lastName = lastNameTextField.getText();
				List<Employee> employee = null;
				
				if(lastName!=null && lastName.trim().length()>0){
					System.out.println("Here 2");
					employee = employeeDAO.searchForEmployee(lastName);
				}
				else{
					employee = employeeDAO.getEmployee();
				}
				//creating instance of tablemodel
				
				EmployeeTableModel model = new EmployeeTableModel(employee);
				table.setModel(model);			
				
				
				/*for(Employee temp : employee){
					System.out.println(temp);
				}*/
	}catch(Exception ex){ex.printStackTrace();
		JOptionPane.showMessageDialog(EmployeeSearchApp.this,"Error:"+ex,"Error:",JOptionPane.ERROR_MESSAGE);
	}
			}});
		
		btnSearch.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
		btnSearch.setEnabled(true);
		panel.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnAddEmployee = new JButton("Add Employee");
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddEmployeeDialog addEmployeeDialog = new AddEmployeeDialog(employeeDAO, EmployeeSearchApp.this);
				addEmployeeDialog.setVisible(true);
				//addEmployeeDialog.saveEmployee();
			}
		});
		panel_1.add(btnAddEmployee);
		
		JButton btnUpdateEmployee = new JButton("Update Employee");
		btnUpdateEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int row = table.getSelectedRow();
				if(row < 0){
					JOptionPane.showMessageDialog(EmployeeSearchApp.this,"You mush select an employee","Error",JOptionPane.ERROR_MESSAGE);
				return ;
				}
				
				Employee temp = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);
				AddEmployeeDialog dialog = new AddEmployeeDialog(employeeDAO, EmployeeSearchApp.this, temp, true);
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnUpdateEmployee);
		
		JButton btnDeleteEmployee = new JButton("Delete Employee");
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int row = table.getSelectedRow();
				if(row < 0)
				{
					JOptionPane.showMessageDialog(EmployeeSearchApp.this, "You must select an employee","Error",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				int response = JOptionPane.showConfirmDialog(EmployeeSearchApp.this, "Delete this Employee?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(response != JOptionPane.YES_OPTION){
					return ;
				}
				else{
					Employee temp = (Employee) table.getValueAt(row, EmployeeTableModel.OBJECT_COL);
					try {
						employeeDAO.deleteEmployee(temp.getId());
						refreshEmployeesView();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		panel_1.add(btnDeleteEmployee);
	}

	public void refreshEmployeesView() throws Exception {
		try{
		List<Employee> emp = employeeDAO.getEmployee();
		EmployeeTableModel model = new EmployeeTableModel(emp);
		table.setModel(model);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "Error:"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

