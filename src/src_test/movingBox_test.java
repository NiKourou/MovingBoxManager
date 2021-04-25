package src_test;

import static common.GuiBase.appendToCSV;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class movingBox_test {

	/**
	 * Testing method: appendToCSV.
	 */
	@Test
	void testAppendToCSV() {
		assertEquals(",box", appendToCSV(createStringBuilder(), "box"));
	}

	/* ---- Help methods ----*/
	private static StringBuilder createStringBuilder() {
		return new StringBuilder();
	}
}
