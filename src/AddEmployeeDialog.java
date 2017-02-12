import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;

public class AddEmployeeDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField emailTextField;
	private JTextField salaryTextField;
	private EmployeeDAO employeeDAO;
	private EmployeeSearchApp employeeSearchApp;
	private Employee prevEmp = null;
	private boolean updateMode = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddEmployeeDialog dialog = new AddEmployeeDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateGui(Employee prevEmp) {
		try{
	    this.lastNameTextField.setText(prevEmp.getLastName());
		this.firstNameTextField.setText(prevEmp.getFirstName());
		this.emailTextField.setText(prevEmp.getEmail());
		this.salaryTextField.setText(prevEmp.getSalary().toString());
		}catch(Exception e){
			JOptionPane.showMessageDialog(this,"Error:"+e,"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	public AddEmployeeDialog(EmployeeDAO edao, EmployeeSearchApp esa, Employee e, boolean x){
		this();
		employeeDAO = edao;
		employeeSearchApp = esa;
		prevEmp = e;
		updateMode = x;
		if(updateMode = true) {
			setTitle("Update Employee");
			populateGui(prevEmp);
		}
	}
	
	public AddEmployeeDialog(EmployeeDAO edao, EmployeeSearchApp esa){
		this();
		employeeDAO = edao;
		employeeSearchApp = esa;
	}
	
	public AddEmployeeDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblFirstName = new JLabel("First Name");
			contentPanel.add(lblFirstName, "4, 2, right, default");
		}
		{
			firstNameTextField = new JTextField();
			contentPanel.add(firstNameTextField, "6, 2, fill, default");
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "4, 6, right, default");
		}
		{
			lastNameTextField = new JTextField();
			contentPanel.add(lastNameTextField, "6, 6, fill, default");
			lastNameTextField.setColumns(10);
		}
		{
			JLabel lblEmail = new JLabel("Email");
			contentPanel.add(lblEmail, "4, 10, right, default");
		}
		{
			emailTextField = new JTextField();
			contentPanel.add(emailTextField, "6, 10, fill, default");
			emailTextField.setColumns(10);
		}
		{
			JLabel lblSalary = new JLabel("Salary");
			contentPanel.add(lblSalary, "4, 14, right, default");
		}
		{
			salaryTextField = new JTextField();
			contentPanel.add(salaryTextField, "6, 14, fill, default");
			salaryTextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton saveButton = new JButton("Save");
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveEmployee();
					}
				});
				saveButton.setActionCommand("OK");
				buttonPane.add(saveButton);
				getRootPane().setDefaultButton(saveButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public BigDecimal convertStringToBigDecimal(String salarystr){
		BigDecimal result = null;
		
		try {
			Double sal = Double.parseDouble(salarystr);
			result = BigDecimal.valueOf(sal);
		}catch(Exception e){
			e.printStackTrace();
			result = BigDecimal.valueOf(0.0);
		}
		return result;
	}
	
	protected void saveEmployee()
	{
		String first_name = firstNameTextField.getText();
		String last_name = lastNameTextField.getText();
		String email = emailTextField.getText();
		String Salarystr = salaryTextField.getText();
		BigDecimal salary = convertStringToBigDecimal(Salarystr);
		Employee employee = null;
		if(updateMode){
			employee = prevEmp;
			employee.setLastName(last_name);
			employee.setFirstName(first_name);
			employee.setEmail(email);
			employee.setSalary(salary);
		}else{
		employee = new Employee(last_name, first_name, email, salary);
		}
		try {
			if(updateMode){
				employeeDAO.updateEmployee(employee);
			}else {
				employeeDAO.addEmployee(employee);
			}
			setVisible(false);
			dispose();
			
			employeeSearchApp.refreshEmployeesView();
			if(updateMode){
				JOptionPane.showMessageDialog(employeeSearchApp,"Employee Updated","Employee Updated Successfully",JOptionPane.INFORMATION_MESSAGE);
			}else{
			JOptionPane.showMessageDialog(employeeSearchApp,"Employee Added","Employee added successfully",JOptionPane.INFORMATION_MESSAGE);
			}}catch(Exception e){
			JOptionPane.showMessageDialog(employeeSearchApp,"Error saving employee: "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

}
