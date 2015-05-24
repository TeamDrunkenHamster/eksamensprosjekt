package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import logic.LoanOfferReader;
import logic.LoanOfferReaderImpl;
import domainLayer.LoanOffer;

@SuppressWarnings("serial")
public class LoanOfferTab extends JPanel {
	
	private JPanel userInputPanel = new JPanel(new GridBagLayout());
	private JPanel tablePanel = new JPanel(new GridBagLayout());
	private JPanel buttonPanel = new JPanel(new GridBagLayout());
	private GridBagConstraints gcLoanOfferTab = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
	private LoanOfferTable loanOfferModel = new LoanOfferTable();
	private LoanOfferReader loanOR = new LoanOfferReaderImpl();
	
LoanOfferTab(){
	setLayout(new GridBagLayout());
	setPanelsRelative();
	setInputPanel();
	setTablePanel();
	setButtonPanel();
	setPanelColor(false);
	}

private void setButtonPanel() {
	GridBagConstraints gcButtonPanel = new GridBagConstraints();
	JButton createButton = new JButton();
	JButton searchButton = new JButton();
	searchButton.setText("Search");
	createButton.setText("Create");
	createButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new LoanOfferFrame();
		}
	});
	gcButtonPanel.insets = new Insets(5,0,5,5);
	gcButtonPanel.gridx = 0;
	gcButtonPanel.gridy = 0;
	buttonPanel.add(searchButton, gcButtonPanel);
	gcButtonPanel.gridx +=1;
	buttonPanel.add(createButton, gcButtonPanel);
}

private void setTablePanel() {
	
	JScrollPane scrollpaneTablePane = new JScrollPane();
	JTable loanTable = new JTable();
	GridBagConstraints gcTablePanel = new GridBagConstraints();
	
	
	gcTablePanel.weightx = 1;
	gcTablePanel.weighty = 1;
	gcTablePanel.fill = GridBagConstraints.BOTH;
	
	loanTable.setModel(loanOfferModel);
	scrollpaneTablePane.setViewportView(loanTable);
	loanTable.setFillsViewportHeight(true);
	loanTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	loanTable.addMouseListener(new MouseAdapter() {

		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2){
				JTable target = (JTable)e.getSource();
				LoanOffer clickedLoanOffer = new LoanOffer();
				int row = target.getSelectedRow();
				int clickedLoanID = (int) target.getValueAt(row, 0);
				clickedLoanOffer = loanOfferModel.getLoanOffer(clickedLoanID);
				new LoanOfferFrame(clickedLoanOffer);
			}
		}
	});

	tablePanel.add(scrollpaneTablePane, gcTablePanel);
}

private void setInputPanel() {
	JLabel customerCPR = new JLabel("CPR");
	JLabel loanOfferID = new JLabel("LoanID");
	JLabel salesmanID = new JLabel("SalesmanID");
	
	final int textFieldLength = 20;
	JTextField customerCPRTextField = new JTextField(textFieldLength);
	JTextField loanOfferIDTextField = new JTextField(textFieldLength);
	JTextField salesmanIDTextField = new JTextField(textFieldLength);
	
	GridBagConstraints gcInputPanel = new GridBagConstraints();
	
	gcInputPanel.gridx = 0;
	gcInputPanel.gridy = 0;
	gcInputPanel.anchor = GridBagConstraints.LINE_START;
	userInputPanel.add(customerCPR, gcInputPanel);
	gcInputPanel.gridx += 1;
	userInputPanel.add(customerCPRTextField, gcInputPanel);
	gcInputPanel.gridx = 0;
	gcInputPanel.gridy += 1;
	userInputPanel.add(loanOfferID, gcInputPanel);
	gcInputPanel.gridx = 1;
	userInputPanel.add(loanOfferIDTextField, gcInputPanel);
	gcInputPanel.gridx = 0;
	gcInputPanel.gridy += 1;
	userInputPanel.add(salesmanID, gcInputPanel);
	gcInputPanel.gridx = 1;
	userInputPanel.add(salesmanIDTextField,gcInputPanel);
}

private void setPanelsRelative() {	
	gcLoanOfferTab.weightx = 0;
	add(userInputPanel,gcLoanOfferTab);
	gcLoanOfferTab.gridy = 1;
	gcLoanOfferTab.anchor = GridBagConstraints.LAST_LINE_END;
	add(buttonPanel,gcLoanOfferTab);
	gcLoanOfferTab.anchor = GridBagConstraints.FIRST_LINE_START;
	gcLoanOfferTab.gridy = 0;
	gcLoanOfferTab.gridx = 1;
	gcLoanOfferTab.weightx = 1;
	gcLoanOfferTab.gridheight = 2;
	gcLoanOfferTab.fill = GridBagConstraints.BOTH;
	add(tablePanel,gcLoanOfferTab);
}

private void setPanelColor(boolean yes){
	if(yes){
		tablePanel.setBackground(new Color(200,50,0));
		userInputPanel.setBackground(new Color(0,50,200));
		buttonPanel.setBackground(new Color(200,50,200));
	}
}

public void updateTab(){
		loanOfferModel.updateTable();
	}
}
