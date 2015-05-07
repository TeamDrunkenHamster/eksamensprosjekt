package view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class FFSFrame extends JFrame implements Observer{
	
	private JTabbedPane tabPane = new JTabbedPane();
	
	public FFSFrame(){
		setDefaultSettings();
		
	}


	private void setDefaultSettings() {
		setTitle("Ferrari Financing System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(800,600);
		setMinimumSize(d);
		setVisible(true);
		getContentPane().add(tabPane);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {	
	}
}
