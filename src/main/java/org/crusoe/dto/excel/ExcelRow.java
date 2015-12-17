package org.crusoe.dto.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelRow {
	private int rowNumber;
	private List<String> cells = new ArrayList<String>();

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public List<String> getCells() {
		return cells;
	}

	public void setCells(List<String> cells) {
		this.cells = cells;
	}

}
