package Composites;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import data.Box;

/**
 * Class representing the table for the boxes.
 * 
 * @author NK
 * @since 06-02-2021
 */
public class BoxTableComposite extends TableComposite {

	private Table table;

	/**
	 * Constructor.
	 * 
	 * @param composite
	 *                     {@link Composite}
	 * @param boxContainer
	 *                     {@link ArrayList} of Boxes
	 */
	public BoxTableComposite(Composite composite, ArrayList<Box> boxContainer) {
		super(composite, boxContainer);

	}
  
	/**
	 * Creates table's columns.
	 */
	@Override
	protected void createColumns() {
		TableColumn columnId = new TableColumn(table, SWT.NONE);
		columnId.setText(TEXT_ID);
		columnId.setWidth(30);
		TableColumn columnContent = new TableColumn(table, SWT.NONE);
		columnContent.setText(TEXT_CONTENT);
		columnContent.setWidth(640);
	}
}
