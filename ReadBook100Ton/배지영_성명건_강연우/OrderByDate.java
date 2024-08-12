import java.util.Comparator;

public class OrderByDate implements Comparator<Book> {
	@Override
	public int compare(Book book1, Book book2){
		int idx1 = book1.getDate().indexOf("년");
		int idx2 = book1.getDate().indexOf("월");
		int idx3 = book1.getDate().indexOf("일");
		int idx4 = book2.getDate().indexOf("일");
		int year1 = Integer.parseInt(book1.getDate().substring(0, idx1));
		int month1 = Integer.parseInt(book1.getDate().substring(idx1+1, idx2));
		int day1 = Integer.parseInt(book1.getDate().substring(idx2+1, idx3));
		int year2 = Integer.parseInt(book2.getDate().substring(0, idx1));
		int month2 = Integer.parseInt(book2.getDate().substring(idx1+1, idx2));
		int day2 = Integer.parseInt(book2.getDate().substring(idx2+1, idx4));
		
		if(year1 == year2) {
			if(month1 == month2) {
				if(day1 == day2) {
					return 0;
				} else {
					return day1 - day2;
				}
			} else {
				return month1 - month2;
			}
		} else {
			return year1 - year2;
		}
	}
}
