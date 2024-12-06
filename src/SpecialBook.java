public abstract class SpecialBook extends Book {
    private static boolean isCheckedOut;
    private static Member bookReciever;

    public SpecialBook(String title, String author, String year, GenreEnum genre) {
        super(title, author, year, genre);
    }

    public static void checkOut(Member member) {
        if (isCheckedOut) {
            System.out.println("این کتاب قبلاً امانت گرفته شده است.");
        }
        bookReciever = member;
        isCheckedOut = true;
    }
}
