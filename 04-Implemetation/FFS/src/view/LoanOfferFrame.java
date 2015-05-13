package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class LoanOfferFrame extends JDialog {
	
	private JPanel loanInformationPanel;
	private JButton exportToCSV;
	private JButton btnCancel;
	private JButton btnOK;
	private GridBagConstraints gc;

	public LoanOfferFrame() {
		
		setDefaultSettings();
		addDefaultPanels();
		addButtons();
		setVisible(true);
//		addTextFields();
//		addLabels();
	}
	
	private void setDefaultSettings() {

		setTitle("Loan Offer");
		setSize(new Dimension(600,500));
		setResizable(false);
	}

	private void addDefaultPanels() {

		loanInformationPanel = new JPanel(new GridBagLayout());
		gc = new GridBagConstraints();
		loanInformationPanel.setBackground(new Color(100,100,30));
		getContentPane().add(loanInformationPanel);
	}

	private void addButtons() {
		
		btnOK = new JButton("OK");
		loanInformationPanel.add(btnOK, gc);
	}
}
