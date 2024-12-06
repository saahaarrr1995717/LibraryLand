import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {

    private final String name = "LibraryLand";
    private static List<Book> books = new ArrayList<>();
    private static List<Member> members = new ArrayList<>();

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }


    public static List<Book> getBooks() {
        return books;
    }

    public static List<Member> getMembers() {
        return members;
    }

    public static void addNewBook(Scanner scanner) {
        System.out.print("نوع کتاب (1: کتاب عادی، 2: کتاب امضا شده، 3: کتاب کمیاب): ");
        int type = scanner.nextInt();
        scanner.nextLine();
        System.out.print("عنوان کتاب: ");
        String title = scanner.nextLine();
        System.out.print("نویسنده: ");
        String author = scanner.nextLine();
        System.out.print("سال انتشار: ");
        String year = scanner.nextLine();
//        scanner.nextLine(); // consume newline
        System.out.print("ژانر: (ROMANCE, FICTION, NON_FICTION, REFERENCE) ");
        String genre = scanner.nextLine();
        GenreEnum genreEnum = GenreEnum.NONE;
        if (genre.equalsIgnoreCase("ROMANCE")) {
            genreEnum.equals(GenreEnum.ROMANCE);
        } else if (genre.equalsIgnoreCase("FICTION")) {
            genreEnum.equals(GenreEnum.FICTION);
        } else if (genre.equalsIgnoreCase("NON_FICTION")) {
            genreEnum.equals(GenreEnum.NON_FICTION);
        } else if (genre.equalsIgnoreCase("REFERENCE")) {
            genreEnum.equals(GenreEnum.REFERENCE);
        } else {
            System.out.println("نوع ژانر صحیح نمی باشد" + genre);
        }
        switch (type) {
            case 1: // Basic Book
                getBooks().add(new Book(title, author, year, genreEnum));
                break;
            case 2: // Signed Book
                System.out.print("آیا کتاب توسط نویسنده امضا شده است؟ (true/false): ");
                boolean isSignedByAuthor = scanner.nextBoolean();
                scanner.nextLine();
                System.out.print("مالک کتاب: ");
                String owner = scanner.nextLine();
                getBooks().add(new SignedBook(title, author, year, genreEnum, isSignedByAuthor, owner));
                break;
            case 3: // Rare Book
                System.out.print("آیا کتاب کمیاب است؟ (true/false): ");
                boolean isRare = scanner.nextBoolean();
                scanner.nextLine();
                System.out.print("سطح حفاظت (عالی/خوب): ");
                String preservationLevel = scanner.nextLine();
                getBooks().add(new RareBook(title, author, year, genreEnum, isRare, preservationLevel));
                break;
            default:
                System.out.println("نوع کتاب نامعتبر است.");
                return;
        }
        System.out.println("کتاب با موفقیت اضافه شد !");
    }

    public static void addNewMember(Scanner scanner) {
        System.out.print("نام عضو: ");
        String name = scanner.nextLine();
        getMembers().add(new Member(name));
        System.out.println(" عضو با موفقیت اضافه شد !");
    }

    public static void borrowBook(Scanner scanner) {
        System.out.print("شماره عضویت: ");
        String membershipId = scanner.nextLine();
        Member member = findMember(membershipId);
        if (member == null) {
            System.out.println("عضو یافت نشد.");
            return;
        }


        System.out.print("عنوان کتاب: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);
        if (book != null) {
            if (book instanceof SignedBook || book instanceof RareBook) {
                if (member.getBorrowedBooks().containsKey(book)) {
                    System.out.println("این کتاب قبلاً امانت گرفته شده است.");
                    return;
                }
                ((SpecialBook) book).checkOut(member);
                return;
            }
            member.borrowBook(book);
        } else {
            System.out.println("کتاب یافت نشد.");
        }
    }

    private static Member findMember(String membershipId) {
        for (Member member : members) {
            if (member.getMembershipId().equals(membershipId)) {
                return member;
            }
        }
        return null;
    }

    public static Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public static void returnBook(Scanner scanner) {
        System.out.print("شماره عضویت: ");
        String membershipId = scanner.nextLine();
        Member member = findMember(membershipId);
        if (member == null) {
            System.out.println("عضو یافت نشد.");
            return;
        }

        System.out.print("عنوان کتاب: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);
        if (book != null) {
            member.returnBook(book);
        } else {
            System.out.println("کتاب یافت نشد.");
        }
    }

    public static void showBookStatus(Scanner scanner) {
        System.out.print("عنوان کتاب: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);
        if (book != null) {
            if (book.getBorrowStatus().equals(BorrowStatus.AVAILABEL)) {
                System.out.println("وضعیت کتاب : در کتابخانه موجود است");
            } else if (book.getBorrowStatus().equals(BorrowStatus.BORROWED)) {
                System.out.println("وضعیت کتاب : امانت داده شده");

            }
        } else {
            System.out.println("کتاب یافت نشد.");
        }
    }

    public static void saveData() {
        try (PrintWriter bookWriter = new PrintWriter(new FileWriter("bookInput.txt"));
             PrintWriter memberWriter = new PrintWriter(new FileWriter("memberInput.txt"))) {
            for (Book book : books) {
                bookWriter.println(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getYear() + "," + book.getGenre() + "," + book.getBorrowStatus() + ",");
            }
            for (Member member : members) {
                memberWriter.println(member.getId() + "," + member.getName() + "," + member.getBorrowedBooks() + "," + member.isSuspended());
            }
            System.out.println("اطلاعات با موفقیت ذخیره شد. خداحافظ !");
        } catch (IOException e) {
            System.out.println("خطا در ذخیره‌سازی اطلاعات: " + e.getMessage());
        }
    }

}
