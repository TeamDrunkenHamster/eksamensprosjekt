package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import logic.FFSObserver;

@SuppressWarnings("serial")
public class FFSFrame extends JFrame implements FFSObserver{
	
	private JTabbedPane tabPane = new JTabbedPane();
	
	public FFSFrame(){
		setTheme();
		setDefaultSettings();
		initTabs();
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
		Dimension minSize = new Dimension(1024, 768);
		setMinimumSize(minSize);		
		setTitle("Ferrari Financing System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}

	private void initTabs() {
		JPanel createOfferPane = new JPanel(new GridBagLayout());
		JPanel westPanel = new JPanel(new GridBagLayout());
		JPanel eastPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
		
		JLabel customerCPR = new JLabel("CPR");
		JLabel customerFirstName = new JLabel("First name");
		JLabel customerLastName = new JLabel("Last name");
		JLabel customerStanding = new JLabel("Current standing ");
		
		final int textFieldLength = 20;
		JTextField customerCPRTextField = new JTextField(textFieldLength);
		JTextField customerFirstNameTextField = new JTextField(textFieldLength);
		JTextField customerLastNameTextField = new JTextField(textFieldLength);		

		gc.weightx = 0;
		createOfferPane.add(westPanel,gc);
		gc.gridx = 1;
		gc.weightx = 1;
		gc.fill = GridBagConstraints.BOTH;
		createOfferPane.add(eastPanel,gc);
		gc.gridx = 0;
		
		westPanel.setBackground(new Color(0,50,200));		
		westPanel.add(customerCPR, gc);
		gc.gridx = 1;
		westPanel.add(customerCPRTextField, gc);
		gc.gridx = 0;
		gc.gridy += 1;
		westPanel.add(customerFirstName,gc);
		gc.gridx += 1;
		westPanel.add(customerFirstNameTextField,gc);
		gc.gridx = 0;
		gc.gridy += 1;
		westPanel.add(customerLastName,gc);
		gc.gridx += 1;
		westPanel.add(customerLastNameTextField,gc);
		gc.gridx = 0;
		gc.gridy += 1;
		westPanel.add(customerStanding, gc);
		
		//east blob style
		JScrollPane spEast = new JScrollPane();
		String[] header = {"First Name", "Last Name"};
		String[][] data  = {};
		
		JTable tEast = new JTable(data, header);
		spEast.setViewportView(tEast);
		tEast.setFillsViewportHeight(true);
		
		gc.gridx = 1;
		gc.gridy = 0;	
		eastPanel.setBackground(new Color(200,50,0));
		gc.gridy +=1;
		eastPanel.add(spEast, gc);

		tabPane.add("Create Offer", createOfferPane);
		getContentPane().add(tabPane);
	}	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub		
	}
}