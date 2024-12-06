import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        File bookFile = new File("bookInput.txt");
        File memberFile = new File("memberInput.txt");
        loadMemberFile(scanner, memberFile);
        loadBookFile(scanner, bookFile);
        defineLibrary();
    }


    public static void defineLibrary() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println(" به کتابخانه لایبرری لند خوش آمدید !");
                System.out.println("1. اضافه کردن کتاب جدید");
                System.out.println("2. اضافه کردن عضو جدید");
                System.out.println("3. امانت گرفتن کتاب");
                System.out.println("4. بازگرداندن کتاب");
                System.out.println("5. نمایش وضعیت کتاب");
                System.out.println("6. ذخیره اطلاعات و خروج");
                System.out.print("انتخاب شما: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        Library.addNewBook(scanner);
                        break;
                    case 2:
                        Library.addNewMember(scanner);
                        break;
                    case 3:
                        Library.borrowBook(scanner);
                        break;
                    case 4:
                        Library.returnBook(scanner);
                        break;
                    case 5:
                        Library.showBookStatus(scanner);
                        break;
                    case 6:
                        Library.saveData();
                        return;
                    default:
                        System.out.println("انتخاب نامعتبر.");
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }


    public static void loadMemberFile(Scanner scanner, File memberFile) throws FileNotFoundException {
        try (Scanner input = new Scanner(memberFile)) {
            while (input.hasNextLine()) {
                String memberString = input.nextLine();
                String[] parts = memberString.split(",");
                Integer membershipId = Integer.valueOf(parts[0]);
                String name = parts[1];
                HashMap<Object, LocalDate> map = new HashMap<>();
                map.put(parts[2], null);
                Boolean suspended = Boolean.valueOf(parts[3]);
                Member member = new Member(name, map, suspended);
                Library.getMembers().add(member);

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static void loadBookFile(Scanner scanner, File bookFile) throws FileNotFoundException {

        try (Scanner input = new Scanner(bookFile)) {
            while (input.hasNextLine()) {
                String bookString = input.nextLine();
                String[] parts = bookString.split(",");
                String id = parts[0];
                String title = parts[1];
                String author = parts[2];
                String year = parts[3];
                GenreEnum genre = GenreEnum.valueOf(parts[4]);
                BorrowStatus borrowStatus = BorrowStatus.valueOf(parts[5]);
                Book book = new Book(id, title, author, year, genre, borrowStatus);
                Library.getBooks().add(book);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


}