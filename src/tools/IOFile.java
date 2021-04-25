package tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class for write and read from a file.
 * 
 * @author NK
 * @since 2020-10-11
 */
public class IOFile {

	/**
	 * Read a text from a file.
	 * 
	 * @param inputFile
	 *                  {@link File}
	 * @return Byte[]
	 */
	@SuppressWarnings({ "null", "resource" })
	public byte[] read(File inputFile) {
		// Read input file into byte array
		FileInputStream fileInputStream = null;

		byte[] inputBytes = new byte[(int) inputFile.length()];

		try {
			fileInputStream = new FileInputStream(inputFile);
		} 
		catch (FileNotFoundException e) {
			System.err.println("File not found. " + e.getMessage());
		}

		try {
			fileInputStream.read(inputBytes);
		} 
		catch (IOException e) {
			System.err.println("An error occurred while reading from the file. " + e.getMessage());
		} 
		finally {
			// Close file stream
			try {
				fileInputStream.close();
			} catch (IOException e) {
				System.err.println("An error occurred while closing the reading stream. " + e.getMessage());
			}
		}
		return inputBytes;
	}

	/**
	 * Write a text to a file.
	 * 
	 * @param outputBytes
	 *                         byte[]
	 * @param selectedFilePath
	 *                         {@link String}
	 * @return Array of {@link Byte}s
	 * @throws IOException
	 */
	public byte[] write(byte[] outputBytes, String selectedFilePath) throws IOException {
		File fileToSave = getPath(selectedFilePath);
		// Write the output byte array to the output file
		@SuppressWarnings("resource")
		FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
		// Write in file
		fileOutputStream.write(outputBytes);
		// Close file streams
		fileOutputStream.close();

		return outputBytes;
	}

	/**
	 * Returns the path of the file. If this is null or empty then returns a
	 * file with specific filename and location to save it.
	 * 
	 * @param text
	 *             {@link String}
	 * @return {@link File}
	 */
	private static File getPath(String text) {
		File file = null;
		if (text != null && text.length() != 0) {
			file = new File(text);
		} else {
			// TODO: how to save a new file (place and name, from
			// user) in properties file maybe?
			file = new File("files/newlyEncryptedFile.txt");
		}
		return file;
	}
}