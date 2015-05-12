package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import domainLayer.Customer;

public class CustomerTable {

	public TableModel getCustomerTable(List<Customer> customerList) {
		TableModel tableModel = new AbstractTableModel() {
			private static final long serialVersionUID = 1L;
			String[] columnNames = { "ID", "First name", "Last name", "Bad standing"};
			
			public String getColumnName(int col) {
				return columnNames[col].toString();
			}
			public int getColumnCount() {
				return columnNames.length;
			}
			public int getRowCount() {
				return customerList.size();
			}
			public Object getValueAt(int row, int col) {
				if (col == 0)
					return customerList.get(row).getId();
				else if (col == 1)
					return customerList.get(row).getFirstName();
				else if (col == 2)
					return customerList.get(row).getLastName();
				else 
					return customerList.get(row).getBadStanding();
			}
		};
		return tableModel;
	}
}
