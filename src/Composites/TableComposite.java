package Composites;

/*
 * Add, Edit, delete (?), Save
 * 
 * 
 */

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import common.GuiBase;
import common.IGuiTexts;
import data.Box;

/**
 * Class for creating a table with its functionality.
 * 
 * @author NK
 * @since 2021-01-30
 */
public class TableComposite extends GuiBase implements IGuiTexts {
	private Composite composite;
	private Table table;

	ArrayList<Box> boxContainer;

	/**
	 * Constructor.
	 * 
	 * @param composite
	 *                     {@link Composite}
	 * @param boxContainer
	 *                     {@link ArrayList} of boxes
	 */
	public TableComposite(Composite composite, ArrayList<Box> boxContainer) {
		this.composite = composite;
		this.boxContainer = boxContainer;

		createTable();
	}

	/**
	 * Creates the table.
	 */
	private void createTable() {
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		createColumns();
		createRows(boxContainer);
	}

	/**
	 * Creates table's columns.
	 */
	protected void createColumns() {
		createColumn(TEXT_ID, 30);
		createColumn(TEXT_CONTENT, 400);
		createColumn(TEXT_VOLUME, 85);
		createColumn(TEXT_WEIGHT, 80);
	}

	/**
	 * Creates a column.
	 * 
	 * @param columnText
	 *                   {@link String}
	 * @param width
	 *                   int
	 */
	private void createColumn(String columnText, int width) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText(columnText);
		column.setWidth(width);
	}

	/**
	 * Creates table's rows for found boxes.
	 */
	protected void createRows(ArrayList<Box> foundBoxContainer) {
		// Through all available boxes
		for (int index = 0; index < foundBoxContainer.size(); index++) {
			Box box = foundBoxContainer.get(index);
			TableItem item = createItem(box);

			if (box != null) {
				item.setText(fillColumns(foundBoxContainer.get(index)));
			}

			// Alternate row's background color
			alternateRowColor(item, index);
		}
	}

	/**
	 * Creates a table item (row).
	 * 
	 * @param box
	 *            {@link Box}
	 * @return {@link TableItem}
	 */
	private TableItem createItem(Box box) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setData(box);
		return item;
	}

	/**
	 * Returns the header.
	 * 
	 * @return array of Strings
	 */
	public String[] getHeader() {
		return new String[] { "id", "content", "volume", "weight" };
	}

	/**
	 * Fills the columns with the elements (id and content) of a box.
	 * 
	 * @param box
	 *            {@link Box}
	 * @return array of Strings
	 */
	private static String[] fillColumns(Box box) {
		return new String[] { String.valueOf(box.getId()), box.getContent(), box.getVolume(),
				String.valueOf(box.getWeight()) };
	}

	/**
	 * Alternates the background color of the rows.
	 * 
	 * @param item
	 *              {@link TableItem}
	 * @param index
	 *              int
	 */
	private void alternateRowColor(TableItem item, int index) {
		item.setBackground(index % 2 == 0 ? table.getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE)
				: table.getShell().getDisplay().getSystemColor(SWT.COLOR_GRAY));
	}

	/**
	 * Refreshes the table after a keyword-search.
	 * 
	 * @param boxContainer
	 */
	public void refresh(ArrayList<Box> boxContainer) {
		table.removeAll();
		createRows(boxContainer);
	}

	/**
	 * @return {@link Table}
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param data
	 *             {@link GridData}
	 */
	public void setGridData(GridData data) {
		table.setLayoutData(data);
	}
}