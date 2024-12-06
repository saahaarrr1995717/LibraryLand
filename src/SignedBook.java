public class SignedBook extends SpecialBook {
    private boolean isSignedByAuthor;
    private String owner;

    public boolean isSignedByAuthor() {
        return isSignedByAuthor;
    }

    public SignedBook(String title, String author, String year, GenreEnum genre, boolean isSignedByAuthor, String owner) {
        super(title, author, year, genre);
        this.isSignedByAuthor = isSignedByAuthor;
        this.owner = owner;
    }

    public void showBookInfo() {
        super.showBookInfo();
        System.out.println("Signed by Author: " + isSignedByAuthor + ", Owner: " + owner);
    }
}
