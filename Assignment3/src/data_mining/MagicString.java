package data_mining;

public class MagicString {
	public static String[] getStockDBStrings() {
		String[] retval = new String[3];
		retval[0] = "jdbc:mysql://db.cs.wwu.edu/CS330_201310";
		retval[1] = "nguyen82_reader";
		retval[2] = "Jf5SVetI";
		return retval;
	}
	
	public static String[] getNewDBStrings() {
		String[] retval = new String[3];
		retval[0] = "jdbc:mysql://db.cs.wwu.edu/nguyen82_CS330";
		retval[1] = "nguyen82_writer";
		retval[2] = "rcLAvMHI4Q";
		return retval;
}
}