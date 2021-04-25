package gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Composites.TableComposite;
import common.GuiBase;
import common.IGuiImages;
import common.IGuiTexts;
import data.Box;

/**
 * Class representing the window for adding/editing a box.
 * 
 * @author NK
 * @since 2021-03-03
 */
public class BoxMask extends GuiBase implements IGuiTexts, IGuiImages {

	private Display display;
	private Shell shell;

	private Composite composite;
	private Composite idComposite;
	private Composite textComposite;
	private Composite volumeComposite;
	private Composite volumeInputComposite;
	private Composite weightComposite;
	private Composite buttonsComposite;

	Label idLabel;
	Label id;
	Label contentLabel;
	private Text contentText;
	Label volumeLabel;
	Text widthText;
	Text lengthText;
	Text heightText;
	Label weightLabel;
	private Text weightText;

	Button saveButton;
	Button cancelButton;

	private TableComposite table;
	private ArrayList<Box> boxContainer;
	private Box box;
	private Box editBox;

	protected boolean newBox;

	/**
	 * Constructor.
	 * 
	 * @param table
	 *                     {@link TableComposite}
	 * @param boxContainer
	 *                     {@link ArrayList} of Boxes
	 */
	public BoxMask(TableComposite table, ArrayList<Box> boxContainer) {
		this.table = table;
		this.boxContainer = boxContainer;
		this.newBox = true;

		create();
	}

	/**
	 * Constructor.
	 * 
	 * @param editBox
	 *                {@link Box}
	 */
	public BoxMask(Box editBox) {
		this.editBox = editBox;
		this.newBox = false;

		create();

		fillMask();
	}

	/**
	 * Fills the mask, by given Box.
	 */
	private void fillMask() {
		idLabel.setText("Box Nr.:" + String.valueOf(editBox.getId()));
		contentText.setText(editBox.getContent());
		splitVolume();
		weightText.setText(String.valueOf(editBox.getWeight()));
	}

	/**
	 * Splits the width x length x height in 3 separate values.
	 */
	private void splitVolume() {
		String[] splittedVolume = editBox.getVolume().split("x");
		widthText.setText(splittedVolume[0]);
		lengthText.setText(splittedVolume[1]);
		heightText.setText(splittedVolume[2]);
	}

	/**
	 * Creates the shell and the composites.
	 */
	private void create() {
		display = Display.getDefault();

		createShell();
		createComposites();
	}

	/**
	 * Creates and sets the shell.
	 */
	private void createShell() {
		shell = new Shell(display);
		setShell();

		centerShellMainMonitor(shell);
	}

	/**
	 * Sets the Shell.
	 */
	private void setShell() {
		shell.setImage(new Image(display, ICON_LOGO));
		shell.setText(PROGRAM_NAME);
		shell.setMinimumSize(320, 397);
		shell.setLocation(290, 200);
		shell.setLayout(new GridLayout());
		shell.pack();
		shell.open();
	}

	/**
	 * Creates shell's composites.
	 */
	private void createComposites() {
		createComposite();
		createIdComposite();
		createTextComposite();
		createVolumeComposite();
		createWeightComposite();
		createButtonsComposite();
	}

	/**
	 * Creates the main composite.
	 */
	private void createComposite() {
		composite = new Composite(shell.getShell(), SWT.BORDER);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.getParent().layout();
	}

	/**
	 * Creates the composite for a box's ID number.
	 */
	private void createIdComposite() {
		idComposite = new Composite(composite, SWT.NONE);
		idComposite.setLayout(new GridLayout());

		idLabel = createLabel(idComposite, "Box Nr.: " + getNextAvailableId());
	}

	/**
	 * Returns the next available box-ID.
	 * 
	 * @return int
	 */
	private int getNextAvailableId() {
		int nextAvailableId = getMax();
		return ++nextAvailableId;
	}

	/**
	 * Returns the maximum Id from the boxContainer.
	 * 
	 * @param boxContainer
	 *                     {@link ArrayList} of boxes
	 * @return int
	 */
	public int getMax() {
		int max = Integer.MIN_VALUE;
		if (boxContainer != null) {
			for (int i = 0; i < boxContainer.size(); i++) {
				Box box = boxContainer.get(i);
				if (box != null) {
					if (boxContainer.get(i).getId() > max) {
						max = boxContainer.get(i).getId();
					}
				}
			}
		}
		return max;
	}

	/**
	 * Creates the composite for a box's content.
	 */
	private void createTextComposite() {
		textComposite = new Composite(composite, SWT.NONE);
		textComposite.setLayout(new GridLayout());

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = 180;
		textComposite.setLayoutData(data);

		createTextCore();
	}

	/**
	 * Creates all needed Text elements.
	 */
	private void createTextCore() {
		contentLabel = createLabel(textComposite, "Content:");
		contentText = new Text(textComposite, SWT.BORDER | SWT.WRAP);
		contentText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	/**
	 * Creates the composite for a box's volume.
	 */
	private void createVolumeComposite() {
		volumeComposite = new Composite(composite, SWT.NONE);
		volumeComposite.setLayout(new GridLayout(6, false));
		volumeComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		volumeLabel = createLabel(volumeComposite, "Volume (cm): ");
		createVolumeInputComposite();
		createVolumeCore();
	}

	/**
	 * Creates the composite for inputing volume.
	 */
	private void createVolumeInputComposite() {
		volumeInputComposite = new Composite(volumeComposite, SWT.NONE);
		volumeInputComposite.setLayout(new GridLayout(5, false));
	}

	/**
	 * Creates all needed Volume elements.
	 */
	private void createVolumeCore() {
		widthText = new Text(volumeInputComposite, SWT.BORDER);
		widthText.setLayoutData(createGridDataWidth(25));
		addOnlyDigitsListener(widthText);
		createLabel(volumeInputComposite, "x");

		lengthText = new Text(volumeInputComposite, SWT.BORDER);
		lengthText.setLayoutData(createGridDataWidth(25));
		addOnlyDigitsListener(lengthText);

		createLabel(volumeInputComposite, "x");
		heightText = new Text(volumeInputComposite, SWT.BORDER);
		heightText.setLayoutData(createGridDataWidth(25));
		addOnlyDigitsListener(heightText);
	}

	/**
	 * Creates the composite for a box's weight.
	 */
	private void createWeightComposite() {
		weightComposite = new Composite(composite, SWT.NONE);
		weightComposite.setLayout(new GridLayout(2, false));

		createWeightCore();
	}

	/**
	 * Creates the needed weight elements.
	 */
	private void createWeightCore() {
		weightLabel = createLabel(weightComposite, "Weight (Kg): ");
		weightText = new Text(weightComposite, SWT.BORDER);

		addOnlyDigitsListener(weightText);

		GridData data = createGridDataWidth(25);
		data.horizontalIndent = 10;
		weightText.setLayoutData(data);
	}

	/**
	 * Modify listener: Allows only numbers.
	 * 
	 * @param text
	 *             {@link Text}
	 */
	private static void addOnlyDigitsListener(Text text) {
		text.addListener(SWT.Verify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				String input = e.text;
				char[] chars = new char[input.length()];
				input.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if ((!('0' <= chars[i] && chars[i] <= '9') && chars[i] != ',')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}

	/**
	 * Creates the composite for the buttons (Save, Cancel).
	 */
	private void createButtonsComposite() {
		buttonsComposite = new Composite(composite, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));

		GridData data = new GridData();
		data.horizontalAlignment = SWT.RIGHT;
		data.grabExcessHorizontalSpace = true;
		buttonsComposite.setLayoutData(data);

		createSaveButton();
		createCancelButton();

		buttonsComposite.getParent().layout();
	}

	/**
	 * Creates the "Save" button.
	 */
	private void createSaveButton() {
		saveButton = createButton(buttonsComposite, "Save");
		saveButton.setLayoutData(createGridDataWidth(60));

		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveButtonFunctionality();
			}
		});
	}

	/**
	 * Functionality of save button.
	 */
	private void saveButtonFunctionality() {
		int id = getNextAvailableId();
		String content = contentText.getText();

		String volume = handleVolumeInput();
		double weight = handleWeightInput();
		// FIXME: By editing a Box and save it -> null pointer exception
		box = new Box(id, content, volume, weight);
		// Add newly created box in the container
		boxContainer.add(box);
		table.refresh(boxContainer);

		shell.dispose();
	}

	/**
	 * Handles the input of volume.
	 * 
	 * @return {@link String}
	 */
	private String handleVolumeInput() {
		String width = checkVolumeField(widthText.getText());
		String length = checkVolumeField(lengthText.getText());
		String height = checkVolumeField(heightText.getText());
		return width + "x" + length + "x" + height;
	}

	/**
	 * Checks if a volume field is empty. If empty sets "0".
	 * 
	 * @param input
	 *              {@link String}
	 * @return {@link String}
	 */
	private static String checkVolumeField(String input) {
		if (input.length() == 0) {
			input = "0";
		}
		return input;
	}

	/**
	 * Handles the input of weight.
	 * 
	 * @return double
	 */
	private double handleWeightInput() {
		double weight = 0;
		try {
			weight = Double.parseDouble(weightText.getText());
		} catch (NumberFormatException ex) {
			weight = 0;
		}
		return weight;
	}

	/**
	 * Creates the "Cancel" button.
	 */
	private void createCancelButton() {
		cancelButton = createButton(buttonsComposite, "Cancel");
		cancelButton.setLayoutData(createGridDataWidth(60));

		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}

	/**
	 * Returns a newly created box.
	 * 
	 * @return {@link Box}
	 */
	public Box getNewBox() {
		return box;
	}
}