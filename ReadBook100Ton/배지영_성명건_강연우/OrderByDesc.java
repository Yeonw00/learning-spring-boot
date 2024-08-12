import java.util.Comparator;

public class OrderByDesc implements Comparator<String>{
	@Override
	public int compare(String str1, String str2) {
		int idx1 = str1.indexOf("/");
		int idx2 = str2.indexOf("/");
		String str3 = str1.substring(0, idx1);
		int num1 = Integer.parseInt(str3);
		String str4 = str2.substring(0, idx2);
		int num2 = Integer.parseInt(str4);
		return (num1 - num2) * -1;
	}
}
