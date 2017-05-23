package id.clorus.bukalelang.presentation.utils;

public class StringUtil {
	
	private StringUtil(){
		
	}
	
	public static final String empty = "";
	
	public static final String[] emptyArray = new String[] {empty};
	
	public static boolean isNullOrEmpty(String input) {
		if (input == null) return true;
		if (input.length() == 0) return true;
		return false;
	}
	
}

