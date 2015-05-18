package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataLayer.CarDAO;
import dataLayer.CarDAOImpl;
import dataLayer.ConnectImpl;
import domainLayer.Car;
import domainLayer.LoanOffer;

public class LoanOfferFrame extends JDialog {
	
	private static final int TEXTFIELD_SIZE = 20;
	private Connection connection;
	private LoanOffer loanOffer;
	private JPanel basePanel;
	private JPanel loanInformationPanel;
	private JPanel buttonPanel;
	private JButton btnExportToCSV;
	private JButton btnCancel;
	private JButton btnOK;
	private GridBagConstraints gc;
	private List<String> labelText;
	private List<Object> inputFields;
	private JTextField loanID = new JTextField(TEXTFIELD_SIZE);
	private JTextField cpr = new JTextField(TEXTFIELD_SIZE);
	private JTextField firstName = new JTextField(TEXTFIELD_SIZE);
	private JTextField lastName = new JTextField(TEXTFIELD_SIZE);
	private JTextField salesmanID = new JTextField(TEXTFIELD_SIZE);
	private JComboBox<String> carModel = new JComboBox<String>();
	private JTextField loanSize = new JTextField(TEXTFIELD_SIZE);
	private JTextField downPayment = new JTextField(TEXTFIELD_SIZE);
	private JTextField totalInterestRate = new JTextField(TEXTFIELD_SIZE);
	private JTextField apr = new JTextField(TEXTFIELD_SIZE);
	private JTextField paymentPeriod = new JTextField(TEXTFIELD_SIZE);
	private JTextField startDate = new JTextField(TEXTFIELD_SIZE);
	private JComboBox<String> approvalStatus = new JComboBox<String>();
	private JTextField rejectionStatus = new JTextField(TEXTFIELD_SIZE);
	
	public LoanOfferFrame(LoanOffer loanOffer) {
		
		this();
		this.loanOffer = loanOffer;
		showLoanDetails();
		disableAllFields();
	}

	public LoanOfferFrame() {
	
		createLabelList();
		createInputFieldList();
		setDefaultFrameSettings();
		addDefaultPanels();
		addLabels();
		addFields();
		addCarsToComboBox();
		addButtons();
		disableIDFields();
		setVisible(true);
	}

	private void disableAllFields() {

		for (Object field : inputFields)
			try {
				((JTextField) field).setEditable(false);
			} catch (Exception e) {
				// Catches exception when trying to cast JComboBox carModel
			}
	}

	private void disableIDFields() {
		loanID.setEditable(false);
		totalInterestRate.setEditable(false);
		apr.setEditable(false);
		approvalStatus.setEditable(false);
		rejectionStatus.setEditable(false);
		
	}

	private void setDefaultFrameSettings() {

		setTitle("Loan Offer");
		setSize(new Dimension(800, 300));
		setLocation(100, 100);
		setResizable(false);
	}
	
	private void createLabelList() {

		labelText = new ArrayList<String>(Arrays.asList(
				"Loan ID",
				"CPR number",
				"First name",
				"Last name",
				"Salesman ID",
				"Car model",
				"Loan size",
				"Onetime payment",
				"Interest rate",
				"APR",
				"Period in months",
				"Start date",
				"Approved",
				"Rejected"));
	}
	
	private void createInputFieldList() {
		
		inputFields = new ArrayList<Object>(Arrays.asList(
				loanID,
				cpr,
				firstName,
				lastName,
				salesmanID,
				carModel,
				loanSize,
				downPayment,
				totalInterestRate,
				apr,
				paymentPeriod,
				startDate,
				approvalStatus,
				rejectionStatus));
	}

	private void addLabels() {

		gcReset();
		for (int i = 0; i < labelText.size(); i++) {
			if (i == 7)
				gcColumn(+2);
			loanInformationPanel.add(createDefaultLabel(labelText.get(i)), gc);
			gcDown();
		}
	}

	private void addFields() {
		
		gcReset();
		gcColumn(+1);
		for (int i = 0; i < inputFields.size(); i++) {
			if (i == 7)
				gcColumn(+3);
			loanInformationPanel.add((Component) inputFields.get(i), gc);
			gcDown();
		}
	}
	
	private void addCarsToComboBox() {
		
		for (Car car : getCompleteCarList())
			carModel.addItem(car.getModel());
	}

	private void addDefaultPanels() {

		basePanel = new JPanel(new BorderLayout());
		loanInformationPanel = new JPanel(new GridBagLayout());
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
		basePanel.add(loanInformationPanel, BorderLayout.CENTER);
		basePanel.add(buttonPanel, BorderLayout.SOUTH);
		this.getContentPane().add(basePanel);
	}

	private void addButtons() {
		
		btnExportToCSV = new JButton("Export to CSV");
		btnOK = new JButton("OK");
		btnCancel = new JButton("Cancel");
		
		buttonPanel.add(btnExportToCSV, gc);
		buttonPanel.add(btnOK, gc);
		buttonPanel.add(btnCancel, gc);
		
		btnExportToCSV.addActionListener(event -> btnExportPressed());
		btnOK.addActionListener(event -> btnOKPressed());
		btnCancel.addActionListener(event -> btnCancelPressed());
	}
	
	private void showLoanDetails() {
		
		loanID.setText(String.valueOf(loanOffer.getLoanID()));
		cpr.setText(loanOffer.getCprNumber());
		firstName.setText(loanOffer.getCustomer().getFirstName());
		lastName.setText(loanOffer.getCustomer().getLastName());
		salesmanID.setText(String.valueOf(loanOffer.getSalesman().getSalesmanID()));
		carModel.removeAllItems();
		carModel.addItem(loanOffer.getCar().getModel());
		loanSize.setText(String.valueOf(loanOffer.getLoanSize()));
		downPayment.setText(String.valueOf(loanOffer.getDownPayment()));
		totalInterestRate.setText(String.valueOf(loanOffer.getTotalInterestRate()));
		apr.setText(String.valueOf(loanOffer.getApr()));
		paymentPeriod.setText(String.valueOf(loanOffer.getPaymentInMonths()));
		startDate.setText(loanOffer.getStartDate());
		setApprovalStatus();
		rejectionStatus.setText(String.valueOf(loanOffer.getRejected()));
		
	}
	
	private void setApprovalStatus() {
		
		approvalStatus.addItem("Approved");
		approvalStatus.addItem("Not approved");
		if (loanOffer.getApprovedStatus())
			approvalStatus.setSelectedItem("Approved");
		else
			approvalStatus.setSelectedItem("Not approved");
		
		approvalStatus.setEnabled(false);
	}

	private List<Car> getCompleteCarList() {
		
		try {
			connection = new ConnectImpl().getConnection();
			CarDAO carDAO = new CarDAOImpl();
			return carDAO.readAllCars(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		// Return empty list to avoid passing null if connection fails
		return Collections.emptyList();
	}

	private void closeConnection() {
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void gcReset() {
		
		gc.gridx = 0;
		gc.gridy = 0;
	}
	
	private void gcColumn(int col) {
		
		gc.gridy = 0;
		gc.gridx = col;
	}
	
	private void gcDown() {
		
		gc.gridy += 1;
	}
	
	private void btnOKPressed() {
	}

	private void btnCancelPressed() {

		this.dispose();
	}

	private void btnExportPressed() {
	}
	
	private JLabel createDefaultLabel(String text) {
		
		JLabel temp = new JLabel(text);
		return temp;
	}
}
