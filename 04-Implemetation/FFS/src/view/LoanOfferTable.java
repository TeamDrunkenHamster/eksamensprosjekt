package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import logic.LoanOfferReader;
import logic.LoanOfferReaderImpl;
import domainLayer.LoanOffer;

@SuppressWarnings("serial")
public class LoanOfferTable extends AbstractTableModel{	
	private String[] columnNames = { "Loan ID", "Salesman ID", "Status", "Loan size","First name", "Last name"};
	
	private LoanOfferReader loanOR = new LoanOfferReaderImpl();
	private List <LoanOffer> loanOfferList;
	
	LoanOfferTable(){
		loanOfferList = loanOR.readAllLoanOffers();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
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
			return loanOfferList.get(rowIndex).getSalesman().getId();
		
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
		loanOfferList = loanOR.readAllLoanOffers();
		fireTableDataChanged();
	}
}
