import java.time.LocalDate;
import java.util.*;

public class Member {
    private Integer id;
    private String name;
    private static Integer membershipId = 0;

    private boolean suspended;

    private HashMap<Book, LocalDate> borrowedBooks;

    public String getName() {
        return name;
    }

    public HashMap<Book, LocalDate> getBorrowedBooks() {
        return borrowedBooks;
    }

    public Integer getMembershipIdInteger() {
        return membershipId;
    }

    public boolean isSuspended() {
        return suspended;
    }


    public Member(String name, HashMap borrowedBooks, boolean suspended) {
        this.name = name;
        this.suspended = suspended;
        this.borrowedBooks = new HashMap<>();
        this.membershipId++;
        this.id = membershipId;
    }

    public Member(String name) {
        this.name = name;
        this.borrowedBooks = new HashMap<>();
        this.suspended = false;
        this.membershipId++;
        this.id = membershipId;
    }

    public Integer getId() {
        return id;
    }

    public String getMembershipId() {
        return membershipId.toString();
    }


    public BorrowStatus borrowBook(Book book) {
        if (borrowedBooks.containsKey(book)) {
            System.out.println("در حال حاضر این کتاب در اخنیار شماست !");
            return BorrowStatus.AVAILABEL;
        } else if (!suspended && book.isAvailable().equals(BorrowStatus.AVAILABEL) && borrowedBooks.size() < 3) {
            borrowedBooks.put(book, LocalDate.now());
            book.isBorrowd();
            System.out.println(" کتاب به شما امانت داده شد " + book.getTitle());
            return BorrowStatus.BORROWED;
        } else {
            System.out.println(" کتاب در دسترس نیست !!!! ");
            return BorrowStatus.AVAILABEL;
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.containsKey(book)) {
            borrowedBooks.remove(book);
            book.isAvailable();
            System.out.println("کتاب بازگردانده شد " + book.getTitle());
            LocalDate borrowedDate = borrowedBooks.get(book.getTitle());
            int daysLate = (int) java.time.Duration.between(borrowedDate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() - 30; // 30 روز امانت مجاز
            if (daysLate > 0) {
                if (book instanceof RareBook) {
                    long penalty = calculatePenaltyForRareBook(daysLate);
                    suspended = true;
                    System.out.println("کتاب بازگردانده شد " + book.getTitle());
                    System.out.println("شما " + daysLate + " روز تأخیر داشتید. جریمه شما: " + penalty + " تومان.");
                    return;
                }

                long penalty = calculatePenalty(daysLate);
                suspended = true;
                System.out.println("کتاب بازگردانده شد " + book.getTitle());
                System.out.println("شما " + daysLate + " روز تأخیر داشتید. جریمه شما: " + penalty + " تومان.");
            } else {
                System.out.println("کتاب بازگردانده شد " + book.getTitle());
            }
        } else {
            System.out.println("این کتاب دست شما امانت نبوده ! ");
        }
    }

    public long calculatePenalty(int daysLate) {
        int finePerDay = 100; // هزینه جریمه به ازای هر روز تأخیر
        return daysLate * finePerDay;
    }

    public long calculatePenaltyForRareBook(int daysLate) {
        int finePerDayForRareBook = 200; // هزینه جریمه به ازای هر روز تأخیر
        return daysLate * finePerDayForRareBook;
    }

}
