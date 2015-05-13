package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableModel;

import logic.DatabaseBuilder;
import logic.DatabaseBuilderImpl;
import logic.FFSObserver;
import logic.LoanOfferGenerator;
import logic.LoanOfferGeneratorImpl;
import logic.LoanOfferReader;
import logic.LoanOfferReaderImpl;
import domainLayer.Customer;

@SuppressWarnings("serial")
public class FFSFrame extends JFrame implements FFSObserver{
	
	private JTabbedPane tabPane = new JTabbedPane();
	private DatabaseBuilder databaseBuilder = new DatabaseBuilderImpl();
	private LoanOfferGenerator loanOG = new LoanOfferGeneratorImpl();
	private LoanOfferReader loanOR = new LoanOfferReaderImpl();
	private List<Customer> customerList = new ArrayList<>();
	private CustomerTable customerTable = new CustomerTable();
	private TableModel tableMoodel;
	private JTable tEast = new JTable();
	private JScrollPane spEast = new JScrollPane();

	public FFSFrame(){
		setTheme();
		setDefaultSettings();
		initTabs();
		update();
		databaseBuilder.createDatabase();
		loanOG.addObserver(this);
		loanOR.addObserver(this);
	}

	private void setTheme() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
			// do something random.
			throw new IndexOutOfBoundsException("Totalt relevant error!");
		}		
	}

	private void setDefaultSettings() {
		Dimension minSize = new Dimension(1024, 700);
		setMinimumSize(minSize);		
		setTitle("Ferrari Financing System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}

	private void initTabs() {
		addLoanOffersTab("Loan Offers");
	}

	private void addLoanOffersTab(String tabTitle) {
		JPanel baseTabPanel = new JPanel(new GridBagLayout());
		JPanel userInputPanel = new JPanel(new GridBagLayout());
		JPanel tablePanel = new JPanel(new GridBagLayout());
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
		
		
		
		gc.weightx = 0;
		baseTabPanel.add(userInputPanel,gc);
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		baseTabPanel.add(buttonPanel,gc);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.gridy = 0;
		gc.gridx = 1;
		gc.weightx = 1;
		gc.gridheight = 2;
		gc.fill = GridBagConstraints.BOTH;
		baseTabPanel.add(tablePanel,gc);
		gc.gridheight = 1;
		
		//userinput panel
		JLabel customerCPR = new JLabel("CPR");
		JLabel customerFirstName = new JLabel("First name");
		JLabel customerLastName = new JLabel("Last name");
		JLabel customerStanding = new JLabel("Current standing ");
		
		final int textFieldLength = 20;
		JTextField customerCPRTextField = new JTextField(textFieldLength);
		JTextField customerFirstNameTextField = new JTextField(textFieldLength);
		JTextField customerLastNameTextField = new JTextField(textFieldLength);
		
//		userInputPanel.setBackground(new Color(0,50,200));
		gc.gridx = 0;
		gc.gridy = 0;
		userInputPanel.add(customerCPR, gc);
		gc.gridx += 1;
		userInputPanel.add(customerCPRTextField, gc);
		gc.gridx = 0;
		gc.gridy += 1;
		userInputPanel.add(customerFirstName,gc);
		gc.gridx += 1;
		userInputPanel.add(customerFirstNameTextField,gc);
		gc.gridx = 0;
		gc.gridy += 1;
		userInputPanel.add(customerLastName,gc);
		gc.gridx += 1;
		userInputPanel.add(customerLastNameTextField,gc);
		gc.gridx = 0;
		gc.gridy += 1;
		userInputPanel.add(customerStanding, gc);
		
		gc.gridx = 1;
		gc.gridy = 0;	
		tablePanel.setBackground(new Color(200,50,0));
		gc.gridy +=1;
		tablePanel.add(spEast, gc);

		//button panel
//		buttonPanel.setBackground(new Color(200,50,200));
		JButton createButton = new JButton();
		createButton.setText("Create");
		createButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Customer customer = new Customer();
				customer.setFirstName(customerFirstNameTextField.getText());
				customer.setLastName(customerLastNameTextField.getText());
				customer.setBadStanding(false);
				loanOG.createCustomer(customer);
			}
		});
		
		buttonPanel.add(createButton, gc);
		
		//add it all to the basePanel
		tabPane.add(tabTitle, baseTabPanel);
		getContentPane().add(tabPane);
	}

	private void drawTable() {
		tEast = setCustomerTable();
		
		tEast.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tEast.setFillsViewportHeight(true);
		spEast.setViewportView(tEast);
	}			
	
	private JTable setCustomerTable() {

		if (customerList.isEmpty()) {
			String[] header = {"ID", "First Name", "Last Name", "Standing"};
			String[][] data  = {};
			tEast = new JTable(data, header);
			return tEast;
		}
		else {
			tEast = new JTable();
			tableMoodel = customerTable.getCustomerTable(customerList);
			tEast.setModel(tableMoodel);
			return tEast;
		}
	}

	@Override
	public void update() {
		customerList = loanOR.readAllCustomers();
		drawTable();
	}
}