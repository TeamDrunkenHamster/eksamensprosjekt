package view;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import logging.ErrorTypes;
import logging.LogObject;
import logging.Logger;
import logic.FFSObserver;
import logic.ObserverSingleton;

@SuppressWarnings("serial")
public class FFSFrame extends JFrame implements FFSObserver{
	
	private JTabbedPane tabPane = new JTabbedPane();
	private Logger logger = new Logger();
	private LoanOfferTab loanOfferTab;
	
	public FFSFrame(){
		setTheme();
		setDefaultSettings();
		addTabs();
		ObserverSingleton.instance().addObserver(this);
	}

	private void setTheme() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (ClassNotFoundException
				| InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			logger.log("Theme error", "Error setting Nimbus theme.", ErrorTypes.ERROR);
		}	
	}

	private void setDefaultSettings() {
		Dimension minSize = new Dimension(1024, 700);
		setMinimumSize(minSize);		
		setTitle("Ferrari Financing System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);	
	}

	private void addTabs() {
		loanOfferTab = new LoanOfferTab();
		tabPane.add("Loan Offers", loanOfferTab);
		getContentPane().add(tabPane);

	}

	@Override
	public void update() {
		loanOfferTab.updateTab();
		Collection<LogObject> log = logger.getLog();
		if (!log.isEmpty()) {
			for (LogObject entry : log) {
				if (entry.getErrorType().equals(ErrorTypes.ERROR))
					JOptionPane.showMessageDialog(this, entry.getMessage() , entry.getTitle(), JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(this, entry.getMessage() , entry.getTitle(), JOptionPane.INFORMATION_MESSAGE);
			}
			logger.clearLog();
		}
	}
}