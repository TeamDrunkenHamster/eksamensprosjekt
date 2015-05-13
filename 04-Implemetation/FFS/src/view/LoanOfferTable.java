package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import logic.LoanOfferReader;
import domainLayer.LoanOffer;

public class LoanOfferTable extends AbstractTableModel{	
	private String[] columnNames = { "Loan ID", "Salesman ID", "Status", "Loan size","First name", "Last name"};
	private LoanOfferReader loanOR;
	private List <LoanOffer> loanOfferList;
	
	LoanOfferTable(){
		this.loanOfferList = loanOR.readAllLoanOffer();
	}
	
	@Override	
	public int getRowCount() {
		return loanOfferList.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		
		case 0:
			return loanOfferList.get(rowIndex).getLoanID();
			
		case 1:
			return loanOfferList.get(rowIndex).getSalesman();
		
		case 2:			
			return loanOfferList.get(rowIndex).getRejected();
		
		case 3:
			return loanOfferList.get(rowIndex).getLoanSize();
			
		case 4:
			return loanOfferList.get(rowIndex).getCustomer().getFirstName();
			
		case 5:
			return loanOfferList.get(rowIndex).getCustomer().getLastName();
			
		default:
			return null;
		}
	}
	
	public void updateTable(){
		this.loanOfferList = loanOR.readAllLoanOffer();
	}
}
