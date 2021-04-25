package common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Class for storing common methods.
 * 
 * @author NK
 * @since 2020-10-20
 */
public class GuiBase {

	/**
	 * Creates a MenuItem for MenuBar.
	 * 
	 * @param menu
	 *             {@link Menu}
	 * @param text
	 *             {@link String}
	 */
	protected static void createMenuItem(Menu menu, String text, int style) {
		MenuItem menuItem = new MenuItem(menu, style);
		menuItem.setText(text);
	}

	/**
	 * Creates a ToolItem for the ToolBar.
	 * 
	 * @param toolbar
	 *                  {@link ToolBar}
	 * @param text
	 *                  {@link String}
	 * @param imagePath
	 *                  {@link String}
	 */
	protected ToolItem createToolItem(Shell shell, ToolBar toolbar, String toolTip, String imagePath) {
		ToolItem toolItem = new ToolItem(toolbar, SWT.PUSH);
		toolItem.setToolTipText(toolTip);
		toolItem.setImage(new Image(shell.getDisplay(), imagePath));
		return toolItem;
	}

	/**
	 * Creates a toolItem as a vertical separator line for a toolBar.
	 * 
	 * @param toolbar
	 */
	protected void createSeparatorItem(ToolBar toolbar) {
		new ToolItem(toolbar, SWT.SEPARATOR);
	}

	/**
	 * Creates a label without text, for placing an icon.
	 * 
	 * @param parent
	 *               {@link Composite}
	 * @param style
	 *               int
	 * @param icon
	 *               {@link String}
	 * @return {@link Label}
	 */
	public Label createLabel(Composite parent, int style, String icon) {
		return this.createLabel(parent, style, "", icon);
	}

	/**
	 * Creates a label with text.
	 * 
	 * @param parent
	 *               {@link Composite}
	 * @param text
	 *               {@link String}
	 * @return {@link Label}
	 */
	public Label createLabel(Composite parent, String text) {
		return createLabel(parent, SWT.NONE, text, null);
	}

	/**
	 * Creates a label.
	 * 
	 * @param parent
	 *               {@link Composite}
	 * @param style
	 *               int
	 * @param text
	 *               {@link String}
	 * @param icon
	 *               {@link String}
	 * @return {@link Label}
	 */
	public Label createLabel(Composite parent, int style, String text, String icon) {
		Label label = new Label(parent, style);
		label.setText(text);
		if (icon != null && icon.length() > 0) {
			label.setImage(new Image(parent.getDisplay(), icon));
		}
		return label;
	}

	/**
	 * Creates a label.
	 * 
	 * @param parent
	 *               {@link Composite}
	 * @param style
	 *               int
	 * @return {@link Label}
	 */
	public Label createLabel(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		return label;
	}

	/**
	 * Creates a button.
	 * 
	 * @param composite
	 *                  {@link Composite}
	 * @param text
	 *                  {@link String}
	 * @return {@link Button}
	 */
	public Button createButton(Composite composite, String text) {
		Button button = new Button(composite, SWT.PUSH);
		button.setText(text);
		return button;
	}

	/**
	 * Creates grid data: Width.
	 * 
	 * @param width
	 *              int
	 * @return {@link GridData}
	 */
	public GridData createGridDataWidth(int width) {
		GridData data = new GridData();
		data.widthHint = width;
		return data;
	}

	/**
	 * Creates a separator for a toolBar.
	 * 
	 * @param toolbar
	 *                {@link ToolBar}
	 * @return {@link ToolItem}
	 */
	public ToolItem createSeparator(ToolBar toolbar) {
		return new ToolItem(toolbar, SWT.SEPARATOR);
	}

	/**
	 * Appends a {@code text} and places a ";" before it.
	 * 
	 * @param sb
	 *             StringBuilder
	 * @param text
	 *             String
	 * @return {@link String}
	 */
	public static String appendToCSV(StringBuilder sb, String text) {
		if (text != null && text.length() > 0) {
			sb.append(",");
		}
		sb.append(text);
		return sb.toString();
	}

	/**
	 * Centers the child shell to its parent shell
	 * 
	 * @param shell
	 */
	public void centerShellMainMonitor(Shell shell) {
		Rectangle screenSize = shell.getDisplay().getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2,
				(screenSize.height - shell.getBounds().height) / 2);
	}
}