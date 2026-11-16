package com.library.config;

import com.library.entity.*;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    public DataInitializer(UserRepository userRepository,
                           BookRepository bookRepository,
                           BorrowRecordRepository borrowRecordRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed Admin
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User(
                    adminUsername,
                    passwordEncoder.encode(adminPassword),
                    "Chief Librarian Admin",
                    "admin@library.com",
                    "9876543210",
                    35,
                    Role.ROLE_ADMIN
            );
            userRepository.save(admin);
            System.out.println(">>> Initialized system administrator account: " + adminUsername);
        }

        // 2. Seed Sample Students
        User student1 = null;
        if (userRepository.findByUsername("student1").isEmpty()) {
            User demoStudent = new User(
                    "student1",
                    passwordEncoder.encode("student123"),
                    "Santhosh Kumar",
                    "santhosh@example.com",
                    "9876543210",
                    21,
                    Role.ROLE_STUDENT
            );
            student1 = userRepository.save(demoStudent);
            System.out.println(">>> Initialized initial demonstration student member.");
        } else {
            student1 = userRepository.findByUsername("student1").get();
        }

        if (userRepository.findByUsername("student2").isEmpty()) {
            User student2 = new User(
                    "student2",
                    passwordEncoder.encode("student123"),
                    "Priya Sharma",
                    "priya@example.com",
                    "9845123456",
                    20,
                    Role.ROLE_STUDENT
            );
            userRepository.save(student2);
        }

        if (userRepository.findByUsername("student3").isEmpty()) {
            User student3 = new User(
                    "student3",
                    passwordEncoder.encode("student123"),
                    "Alex Turner",
                    "alex@example.com",
                    "9712345678",
                    22,
                    Role.ROLE_STUDENT
            );
            userRepository.save(student3);
        }

        // 3. Seed Sample Books
        if (bookRepository.count() == 0) {
            Book b1 = new Book("978-0132350884", "Clean Code", "Robert C. Martin", "Technology",
                    "A handbook of agile software craftsmanship. Even bad code can function. But if code isn't clean, it can bring a development organization to its knees.",
                    5, 4, "https://images.unsplash.com/photo-1532012164546-f432f2e372fe?w=400&q=80");

            Book b2 = new Book("978-0201633610", "Design Patterns", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Technology",
                    "Capturing a wealth of experience about the design of object-oriented software, four top-notch designers present a catalog of simple, concise solutions.",
                    4, 4, "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400&q=80");

            Book b3 = new Book("978-0553380163", "A Brief History of Time", "Stephen Hawking", "Science",
                    "A landmark volume in science writing by one of the great minds of our time, exploring the universe from the Big Bang to black holes.",
                    6, 5, "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=400&q=80");

            Book b4 = new Book("978-0062316097", "Sapiens: A Brief History of Humankind", "Yuval Noah Harari", "History",
                    "100,000 years ago, at least six human species inhabited the earth. Today there is just one. Us. Homo sapiens.",
                    8, 7, "https://images.unsplash.com/photo-1461360370896-922624d12aa1?w=400&q=80");

            Book b5 = new Book("978-0735211292", "Atomic Habits", "James Clear", "Education",
                    "No matter your goals, Atomic Habits offers a proven framework for improving every day. Tiny Changes, Remarkable Results.",
                    10, 9, "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400&q=80");

            Book b6 = new Book("978-0060935467", "To Kill a Mockingbird", "Harper Lee", "Fiction",
                    "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it.",
                    7, 7, "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=400&q=80");

            Book b7 = new Book("978-0135957059", "The Pragmatic Programmer", "Andrew Hunt, David Thomas", "Technology",
                    "Straight from the trenches, The Pragmatic Programmer cuts through the increasing specialization and technicalities of modern software development.",
                    4, 4, "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=400&q=80");

            Book b8 = new Book("978-0451524935", "1984", "George Orwell", "Fiction",
                    "A chilling dystopian masterpiece about government surveillance, pervasive manipulation, and authoritarian control.",
                    6, 6, "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=400&q=80");

            Book b9 = new Book("978-0374533557", "Thinking, Fast and Slow", "Daniel Kahneman", "Science",
                    "Nobel laureate Daniel Kahneman takes us on a groundbreaking tour of the mind and explains the two systems that drive the way we think.",
                    5, 5, "https://images.unsplash.com/photo-1457369804613-52c61a468e7d?w=400&q=80");

            Book b10 = new Book("978-0262033848", "Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson", "Education",
                    "Some books on algorithms are rigorous but incomplete; others cover masses of material but lack rigor. Introduction to Algorithms uniquely combines both.",
                    5, 5, "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?w=400&q=80");

            bookRepository.save(b1);
            bookRepository.save(b2);
            bookRepository.save(b3);
            bookRepository.save(b4);
            bookRepository.save(b5);
            bookRepository.save(b6);
            bookRepository.save(b7);
            bookRepository.save(b8);
            bookRepository.save(b9);
            bookRepository.save(b10);

            // Seed sample active borrow for student1
            if (student1 != null) {
                BorrowRecord record1 = new BorrowRecord(student1, b1);
                record1.setBorrowDate(LocalDateTime.now().minusDays(3));
                record1.setDueDate(LocalDate.now().plusDays(11));
                record1.setStatus(BorrowStatus.ACTIVE);
                borrowRecordRepository.save(record1);

                BorrowRecord record2 = new BorrowRecord(student1, b3);
                record2.setBorrowDate(LocalDateTime.now().minusDays(20));
                record2.setDueDate(LocalDate.now().minusDays(6));
                record2.setReturnDate(LocalDateTime.now().minusDays(5));
                record2.setStatus(BorrowStatus.RETURNED);
                borrowRecordRepository.save(record2);
            }

            System.out.println(">>> Initialized 10 curated books and sample borrow transactions.");
        }
    }
}
