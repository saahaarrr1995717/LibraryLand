public class RareBook extends SpecialBook {
    private boolean isRare;
    private String preservationLevel;

    public boolean isRare() {
        return isRare;
    }


    public RareBook(String title, String author, String year, GenreEnum genre, boolean isRare, String preservationLevel) {
        super(title, author, year, genre);
        this.isRare = isRare;
        this.preservationLevel = preservationLevel;
    }

    public void showBookInfo() {
        super.showBookInfo();
        System.out.println("Is Rare: " + isRare + ", Preservation Level: " + preservationLevel);
    }
}
