public class Book {

    private String id;

    private String title;
    private String author;
    private String year;
    private GenreEnum genre;

    private BorrowStatus borrowStatus;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }


    public BorrowStatus getBorrowStatus() {
        return borrowStatus;
    }

    public Book(String id, String title, String author, String year, GenreEnum genre, BorrowStatus borrowStatus) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.borrowStatus = borrowStatus;
    }

    public Book(String title, String author, String year, GenreEnum genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.borrowStatus = BorrowStatus.AVAILABEL;
    }

    public BorrowStatus isAvailable() {
        return BorrowStatus.AVAILABEL;
    }

    public BorrowStatus isBorrowd() {
        return BorrowStatus.BORROWED;
    }

    public void showBookInfo() {
        System.out.println("Book : " +
                "title : '" + title + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", genre=" + genre + '}');
    }

    public void updateBookInfo(String title, String author, String year, GenreEnum genre) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

}
