import java.util.Comparator;

public class OrderByGrade implements Comparator<Book> {
	@Override
	public int compare(Book book1, Book book2){
		return book1.getGrade() - book2.getGrade();
	}
}
