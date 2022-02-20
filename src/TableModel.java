import javax.swing.table.AbstractTableModel;


public class TableModel extends AbstractTableModel {
	private int rowCount, columnCount;
	String[][] data;
	public TableModel(int r, int c, String[][] data){
		rowCount = r;
		columnCount = c;
		this.data = data;
	}

	public void setRowCount(int c) {
		rowCount = c;
	}

	public void setData(String[][] d){
		data = d;
	}
	@Override
	public int getRowCount() {
		return rowCount;
	}

	public void setColumnCount(int c) {
		columnCount = c;
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(data == null){
			return null;
		}
		return data[rowIndex][columnIndex];
	}

	public String getColumnName(int c){
		String[] column = {"nom", "prenom", "pseudo"};
		return column[c];

	}
}
