package gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import Composites.TableComposite;
import common.GuiBase;
import common.IGuiImages;
import common.IGuiTexts;
import data.Box;

/**
 * Class representing the main window of MovingBoxManager.
 * TODO There is functionality to implement.
 * 
 * @author NK
 * @since 2021-01-28
 */
public class MainMask extends GuiBase implements IGuiTexts, IGuiImages {

	private Display display;
	private Shell shell;

	private Composite composite;
	private Composite toolbarComposite;
	private Composite searchComposite;
	private Composite tableComposite;
	private Composite boxContentComposite;
	private Composite infoComposite;

	private Button searchButton;

	private ToolItem addBoxToolItem;
	protected ToolItem editBoxToolItem;
	private ToolItem deleteBoxToolItem;
	private ToolItem exitToolItem;

	private Text searchText;
	private TableComposite table;
	private StyledText boxContentStyledText;
	private Label infoArea;

	private ArrayList<Box> boxContainer;
	private ArrayList<Box> foundBoxContainer;

	protected BoxMask boxMask;

	/**
	 * Constructor.
	 */
	public MainMask() {
		display = Display.getDefault();
		shell = new Shell(display);

		// Create the list for the moving boxes
		boxContainer = new ArrayList<>();
		// Create the list for the found moving boxes
		foundBoxContainer = new ArrayList<>();

		setShell();
		try {
			create();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		shell.open();

		// "Red-X": Save before exit or not.
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				msgBoxWarning(shell, "Warning", "Do you want to save the changes before exit?");
			}
		});

		readAndDispatch();
	}

	/**
	 * Sets the Shell.
	 */
	private void setShell() {
		shell.setImage(new Image(display, ICON_LOGO));
		shell.setText(PROGRAM_NAME);
		shell.setSize(600, 600);
		shell.setMinimumSize(850, 397);
		shell.setLayout(new GridLayout());
		shell.pack();

		centerShellMainMonitor(shell);
	}

	/**
	 * Creates elements of window.
	 * 
	 * @throws FileNotFoundException
	 */
	private void create() throws FileNotFoundException {
		createComposites();
	}

	/**
	 * Creates all composites.
	 * 
	 * @throws FileNotFoundException
	 */
	private void createComposites() throws FileNotFoundException {
		createComposite();
		createToolbarComposite();
		createSearchComposite();
		createTableComposite();
		createBoxContentComposite();
		createInfoAreaComposite();
	}

	/**
	 * Creates the main composite.
	 */
	private void createComposite() {
		composite = new Composite(shell, SWT.BORDER);

		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	/**
	 * Creates the toolbar's composite.
	 */
	private void createToolbarComposite() {
		toolbarComposite = new Composite(composite, SWT.NONE);

		// Layout & data
		createToolbarLayout();
		createToolbarData();

		createToolbar();
	}

	/**
	 * Creates grid layout for the toolBar.
	 */
	private void createToolbarLayout() {
		GridLayout layout = new GridLayout(6, true);
		layout.marginHeight = 0;
		toolbarComposite.setLayout(layout);
	}

	/**
	 * Creates grid data for the toolBar.
	 */
	private void createToolbarData() {
		GridData data = new GridData(SWT.FILL, SWT.NONE, true, false);
		toolbarComposite.setLayoutData(data);
	}

	/**
	 * Creates the ToolBar.
	 */
	private void createToolbar() {
		ToolBar toolbar = new ToolBar(toolbarComposite, SWT.FLAT | SWT.WRAP | SWT.LEFT);

		// Create the toolItems for add, edit and remove a box
		createAddBoxToolItem(toolbar);
		createEditBoxToolItem(toolbar);
		createDeleteBoxToolItem(toolbar);
		createExitToolItem(toolbar);
	}

	/**
	 * Creates the toolItem for adding a box.
	 * 
	 * @param toolbar
	 *                {@link ToolBar}
	 */
	private void createAddBoxToolItem(ToolBar toolbar) {
		addBoxToolItem = createToolItem(shell, toolbar, "Add a box", ICON_ADD);

		addBoxToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addBoxCore();
			}
		});

	}

	/**
	 * Core functionality for adding a box.
	 */
	private void addBoxCore() {
		boxMask = new BoxMask(table, boxContainer);
	}

	/**
	 * Creates the toolItem for editing a box.
	 * 
	 * @param toolbar
	 *                {@link ToolBar}
	 */
	// TODO new functionality
	private void createEditBoxToolItem(ToolBar toolbar) {
		editBoxToolItem = createToolItem(shell, toolbar, "Edit a box", ICON_EDIT);
//		editBoxToolItem.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				editBoxCore();
//			}
//		});
	}

	/**
	 * Core functionality: Opens the mask to edit a box.
	 */
	// FIXME [editBoxCore] If no row selected and clicking "edit" -> index out of bounds
	@SuppressWarnings("unused")
	private void editBoxCore() {
		TableItem[] selection = table.getTable().getSelection();

		TableItem selectedItem = selection[0];
		Box selectedBox = (Box) selectedItem.getData();

		new BoxMask(selectedBox);
	}

	/**
	 * Creates the toolItem for deleting a box.
	 * 
	 * @param toolbar
	 *                {@link ToolBar}
	 */
	private void createDeleteBoxToolItem(ToolBar toolbar) {
		deleteBoxToolItem = createToolItem(shell, toolbar, "Delete a box", ICON_REMOVE);

		deleteBoxToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteBoxCore();
			}
		});
	}

	/**
	 * Core functionality for delete box ToolItem.
	 */
	private void deleteBoxCore() {
		TableItem[] selection = table.getTable().getSelection();
		TableItem selectedItem = selection[0];
		Box selectedBox = (Box) selectedItem.getData();

		deleteBox(selectedBox);

		// update table
		table.refresh(boxContainer);
	}

	/**
	 * Deletes a box from box container.
	 * 
	 * @param selectedBox
	 *                    {@link Box}
	 */
	private void deleteBox(Box selectedBox) {
		for (int i = 0; i < boxContainer.size(); i++) {
			if (boxContainer.get(i).getId() == selectedBox.getId()) {
				boxContainer.remove(i);
			}
		}
	}

	/**
	 * Creates the toolItem for exiting from the program.
	 * 
	 * @param toolbar
	 *                {@link ToolBar}
	 */
	private void createExitToolItem(ToolBar toolbar) {
		exitToolItem = createToolItem(shell, toolbar, "Exit program", ICON_EXIT);

		exitToolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				msgBoxWarning(shell, "Warning", "Do you want to save the changes before exit?");
			}
		});

	}

	/**
	 * Message box for "Warning".
	 * 
	 * @param shell
	 *                Shell
	 * @param text
	 *                String
	 * @param message
	 *                String
	 */
	public void msgBoxWarning(Shell shell, String text, String message) {
		msgBox(shell, text, message);
	}

	/**
	 * Message box core.
	 * 
	 * @param shell
	 *                Shell
	 * @param text
	 *                String
	 * @param message
	 *                String
	 */
	public void msgBox(Shell shell, String text, String message) {
		MessageBox msgBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
		msgBox.setText(text);
		msgBox.setMessage(message);
		int buttonID = msgBox.open();

		// YES / NO button-controls.
		switch (buttonID) {
		case SWT.YES: // If "YES", save and quit.
			try {
				writeBoxes();
			} catch (IOException e) {
				System.out.println("An error occurred while reading or writing a file.");
			}
			System.exit(0);
			break;
		case SWT.NO: // If "NO", quit without save.
			System.exit(0);
			break;
		default:
			break;
		}
	}

	/**
	 * Writing houses to a file.
	 * 
	 * @throws IOException
	 *                     Possible error by saving data in the file.
	 */
	private void writeBoxes() throws IOException {
		ArrayList<String> boxesToSaveInFile = new ArrayList<>();
		for (Box box : boxContainer) {
			boxesToSaveInFile.add(box.toCSV());
		}
		writeLinesIncludeHeader(new File("files/boxes.csv"), table.getHeader(), boxesToSaveInFile);
	}

	/**
	 * Saves in a file, a header and the data.
	 * 
	 * @param file
	 *               File
	 * @param header
	 *               String[]
	 * @param lines
	 *               ArrayList<String>
	 * @throws IOException
	 *                     Errors by parsing values to the file
	 */
	public static void writeLinesIncludeHeader(File file, String[] header, ArrayList<String> lines) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			StringBuilder sb = new StringBuilder();
			for (String field : header) {
				appendToCSV(sb, field);
			}
			sb.append("\n");
			for (String line : lines) {
				sb.append(line + "\n");
			}
			writer.write(sb.toString());
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Creates the search composite.
	 */
	private void createSearchComposite() {
		searchComposite = new Composite(toolbarComposite, SWT.NONE);

		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = 0;
		layout.marginRight = 106;
		searchComposite.setLayout(layout);

		createSearchText();
		createSearchButton();
		createResetButton();
	}

	/**
	 * Creates the search text.
	 */
	private void createSearchText() {
		searchText = new Text(searchComposite, SWT.BORDER);

		GridData data = new GridData();
		data.widthHint = 150;
		data.heightHint = 17;
		data.horizontalIndent = -4;

		searchText.setLayoutData(data);

		// Add listener for return/enter pressing
		addEnterKeyListener();
	}

	/**
	 * Creates the search button.
	 */
	private void createSearchButton() {
		searchButton = new Button(searchComposite, SWT.PUSH);
		searchButton.setImage(new Image(shell.getDisplay(), ICON_SEARCH));

		// Adds the listener for search button
		addSearchButtonListener();
	}

	/**
	 * Adds a listener to the search button.
	 */
	private void addSearchButtonListener() {
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				searchButtonFunctionality();
			}
		});
	}

	/**
	 * Search button's functionality.
	 */
	private void searchButtonFunctionality() {
		String keyword = searchText.getText();

		// If a keyword is available
		if (keyword != null && keyword.length() > 0) {
			searchKeywordInBoxContainer(keyword);
		} else {
			// Refresh table with initial boxes
			table.refresh(boxContainer);
		}
		// Reset found-box-container after every search
		foundBoxContainer.clear();
	}

	/**
	 * Creates the reset button.
	 */
	private void createResetButton() {
		searchButton = new Button(searchComposite, SWT.PUSH);
		searchButton.setImage(new Image(shell.getDisplay(), ICON_RESET));

		addResetButtonListener();
	}

	/**
	 * Adds a listener to the reset button.
	 */
	private void addResetButtonListener() {
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				resetButtonFunctionality();
			}
		});
	}

	/**
	 * Reset button's functionality.
	 */
	private void resetButtonFunctionality() {
		searchText.setText(EMPTY_STR);
		table.refresh(boxContainer);
	}

	/**
	 * Searches for a given keyword in the boxes.
	 * 
	 * @param keyword
	 *                {@link String}
	 */
	private void searchKeywordInBoxContainer(String keyword) {
		for (int index = 0; index < boxContainer.size(); index++) {
			// If keyword matches with a box's content
			if (boxContainer.get(index).getContent().toLowerCase().contains(keyword.toLowerCase())) {
				foundBoxContainer.add(boxContainer.get(index));
			}
		}
		// Refresh with filtered boxes
		table.refresh(foundBoxContainer);
	}

	/**
	 * Creates the table composite.
	 * 
	 * @throws FileNotFoundException
	 *                               if file not found
	 */
	private void createTableComposite() throws FileNotFoundException {
		// Layout & data
		tableComposite = new Composite(composite, SWT.BORDER);
		tableComposite.setLayout(new GridLayout(2, false));
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createTable();
	}

	/**
	 * Creates an instance of TableComposite, representing a table.
	 * 
	 * @throws FileNotFoundException
	 *                               if file not found
	 */
	private void createTable() throws FileNotFoundException {
		boxContainer = getBoxesFromFile();
		table = new TableComposite(tableComposite, boxContainer);
		table.setGridData(new GridData(SWT.NONE, SWT.FILL, false, true));

		// If an item is selected
		addItemSelectionListener();
	}

	/**
	 * Adds a listener on the table, to show in text field the content of a
	 * selected box.
	 */
	private void addItemSelectionListener() {
		table.getTable().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				showBoxContent(e);
			}
		});
	}

	/**
	 * Shows the content of a selected box.
	 * 
	 * @param e
	 *          {@link Event}
	 */
	private void showBoxContent(Event e) {
		StringBuilder sb = new StringBuilder();
		Box selectedBox = (Box) e.item.getData();

		if (selectedBox != null) {
			sb.append("Box Nr.: ").append(selectedBox.getId()).append("\n\nContent: \n");

			String selectedBoxContent = selectedBox.getContent();
			String[] boxContentArray = selectedBoxContent.split(COMMA_STR);
			for (int index = 0; index < boxContentArray.length; index++) {
				sb.append("\t").append(index + 1).append(POINT_STR).append(EMPTY_STR).append(boxContentArray[index])
						.append("\n");
			}
			sb.append("\n\n").append("Volume (cm): ").append(selectedBox.getVolume()).append("\nWeight (Kg): ")
					.append(selectedBox.getWeight());
		}

		// Show the box content in styled text composite
		boxContentStyledText.setText(sb.toString());
	}

	/**
	 * Returns the boxes from the file.
	 * 
	 * @return {@link ArrayList} of moving boxes
	 * @throws FileNotFoundException
	 *                               if file not found
	 */
	private ArrayList<Box> getBoxesFromFile() throws FileNotFoundException {
		File boxesFile = new File(FILE_PATH);
		Scanner sc = new Scanner(boxesFile);
		String line;
		String[] tokensInLine;

		// Exclude the header (first line in csv file)
		@SuppressWarnings("unused")
		String headerLine = sc.nextLine();

		// Content of csv file
		while (sc.hasNext()) {
			// A line from csv file
			line = sc.nextLine();

			// Split line by delimiter
			tokensInLine = line.split(SEMICOLON);

			int id = Integer.parseInt(tokensInLine[0]);
			// Box content - Removes also the double quotes
			String boxContent = tokensInLine[1].replace(DOUBLE_QUOTE, EMPTY_STR);
			String volume = tokensInLine[2].replace(DOUBLE_QUOTE, EMPTY_STR);
			// TODO problem if a weight in csv file is double with ","
			double weight = Double.parseDouble(tokensInLine[3].replace(COMMA_STR, POINT_STR));

			// Creates a box
			Box box = createBox(id, boxContent, volume, weight);

			// Add a newly created box in the list
			boxContainer.add(box);
		}
		sc.close();
		return boxContainer;
	}

	/**
	 * Creates a box.
	 * 
	 * @param id
	 *                   int
	 * @param boxContent
	 *                   {@link String}
	 * @return {@link Box}
	 */
	private static Box createBox(int id, String boxContent, String volume, double weight) {
		return new Box(id, boxContent, volume, weight);
	}

	/**
	 * Adds a key listener: If user clicks return/enter searches for a keyword
	 * in text.
	 */
	private void addEnterKeyListener() {
		searchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				enterKeyFunctionality(e);
			}
		});
	}

	/**
	 * Enter key functionality.
	 * 
	 * @param e
	 *          {@link KeyEvent}
	 */
	private void enterKeyFunctionality(KeyEvent e) {
		// If user clicks return/enter (from keypad also)
		if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
			searchButtonFunctionality();
		}
	}

	/**
	 * Creates the composite to show the content of a selected box.
	 */
	private void createBoxContentComposite() {
		boxContentComposite = new Composite(tableComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		boxContentComposite.setLayout(layout);
		boxContentComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Creates the text field to show the box content
		createBoxContentStyledText();
	}

	/**
	 * Creates the text field to show the box content
	 */
	private void createBoxContentStyledText() {
		boxContentStyledText = new StyledText(boxContentComposite, SWT.BORDER | SWT.V_SCROLL);
		boxContentStyledText.setEditable(false);
		boxContentStyledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	/**
	 * Creates the information area's composite.
	 */
	private void createInfoAreaComposite() {
		infoComposite = new Composite(composite, SWT.BORDER);
		infoComposite.setLayout(new GridLayout());

		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		infoComposite.setLayoutData(data);

		createInfoArea();
	}

	/**
	 * Creates the area where informations for user will appear.
	 */
	private void createInfoArea() {
		infoArea = createLabel(infoComposite);
	}

	/**
	 * Add listener for choosing a file.
	 */
	// TODO Future update
//	private void addListenerChooseFile() {
//		chooseFileTool.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent arg0) {
//				//
//			}
//		});
//	}

	/**
	 * Creates a dialog to choose a file, inclusive all of its settings.
	 * 
	 * @param title
	 *              {@link String}
	 * @return {@link String}
	 */
	@SuppressWarnings("unused")
	private String createDialog(String title) {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText(title);
		dialog.setFilterPath("C:/");
		String[] filterExt = { "*.csv", "*.txt" };
		dialog.setFilterExtensions(filterExt);
		return dialog.open();
	}

	/**
	 * @return {@link Label}
	 */
	public Label getInfoArea() {
		return infoArea;
	}

	/**
	 * Updates the info area with a message for the user.
	 * 
	 * @param text
	 *             {@link String}
	 */
	// TODO Future update
	@SuppressWarnings("unused")
	private void updateInfoArea(String text) {
		getInfoArea().setText(text);
		getInfoArea().getParent().layout();
	}

	@SuppressWarnings("unused")
	private void dispose() {
		// dispose the elements
	}

	/**
	 * Waiting for actions.
	 */
	private void readAndDispatch() {
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}