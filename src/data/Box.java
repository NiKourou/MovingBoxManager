package data;

/**
 * Class represents a relocation's moving box.
 * 
 * @author NK
 *
 * @since 27.01.2021
 */
public class Box {
	private int id;
	private String content;
	private String volume;
	private double weight;

	/**
	 * Constructor.
	 */
	public Box() {
		//
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *                int
	 * @param content
	 *                {@link String}
	 * @param volume
	 *                {@link String}
	 * @param weight
	 *                double
	 */
	public Box(int id, String content, String volume, double weight) {
		setId(id);
		setContent(content);
		setVolume(volume);
		setWeight(weight);
	}

	/**
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *           int
	 */
	public void setId(int id) {
		if (id > 0) {
			this.id = id;
		}
	}

	/**
	 * @return String
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *                {@link String}
	 */
	public void setContent(String content) {
		if (content != null && content.length() > 0) {
			this.content = content;
		}
	}

	/**
	 * @return {@link String}
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *               {@link String}
	 */
	public void setVolume(String volume) {
		if (volume != null && volume.length() > 0) {
			this.volume = volume;
		}
	}

	/**
	 * @return double
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *               double
	 */
	public void setWeight(double weight) {
		if (weight > 0) {
			this.weight = weight;
		}
	}

	/**
	 * Returns the box as formatted text, for saving it in csv/txt file.
	 * 
	 * @return string
	 */
	public String toCSV() {
		StringBuilder sb = new StringBuilder();
		appendToCSV(sb, Integer.toString(getId()));
		appendToCSV(sb, getContent());
		appendToCSV(sb, getVolume());
		appendToCSV(sb, Double.toString(getWeight()));
		return sb.toString();
	}

	/**
	 * Appends a {@code text} and places a ";" before it.
	 * 
	 * @param sb
	 *             StringBuilder
	 * @param text
	 *             String
	 */
	public static void appendToCSV(StringBuilder sb, String text) {
//		checkNullE(text, "text");
		if (sb.length() != 0) {
			sb.append(";");
		}
		sb.append(text);
	}
}