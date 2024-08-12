import java.io.Serializable;

public class Book implements Comparable<Book>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String author;
	private String title;
	private String date;
	private String memo;
	private int grade;
	
	public Book(String title) {
		setTitle(title);
	}

	public Book(
		String type, String author, String title, 
		String date, String memo, int grade
	) {
		setType(type);
		setAuthor(author);
		setTitle(title);
		setDate(date);
		setMemo(memo);
		setGrade(grade);
	}
	
	public String getType() {
		return type;
	}
	public String getAuthor() {
		return author;
	}
	public String getTitle() {
		return title;
	}
	public String getDate() {
		return date;
	}
	public String getMemo() {
		return memo;
	}
	public int getGrade() {
		return grade;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		String info = title + "( 작가 : " + author + ", 평점 : " + grade + ", 읽은날짜 : "+date+")";
		return info;
	}
	@Override
	public int compareTo(Book other) {
		return title.compareTo(other.getTitle());
	}
}
