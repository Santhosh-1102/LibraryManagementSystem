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
        // 1. Initialize Institutional Administrator
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = new User(
                    adminUsername,
                    passwordEncoder.encode(adminPassword),
                    "Chief Librarian Admin",
                    "admin@library.edu",
                    "9876543210",
                    38,
                    Role.ROLE_ADMIN
            );
            userRepository.save(admin);
            System.out.println(">>> Initialized system administrator account: " + adminUsername);
        }

        // 2. Initialize Enrolled Student Members
        User student1 = seedStudentIfNotExists("santhosh", "student123", "Santhosh Kumar", "santhosh.k@library.edu", "9876543210", 21);
        User student2 = seedStudentIfNotExists("priya.sharma", "student123", "Priya Sharma", "priya.s@library.edu", "9845123456", 20);
        User student3 = seedStudentIfNotExists("alex.turner", "student123", "Alex Turner", "alex.t@library.edu", "9712345678", 22);
        User student4 = seedStudentIfNotExists("rahul.verma", "student123", "Rahul Verma", "rahul.v@library.edu", "9654321870", 21);
        User student5 = seedStudentIfNotExists("ananya.patel", "student123", "Ananya Patel", "ananya.p@library.edu", "9512348765", 20);
        User student6 = seedStudentIfNotExists("david.miller", "student123", "David Miller", "david.m@library.edu", "9412356789", 23);
        User student7 = seedStudentIfNotExists("sophia.chen", "student123", "Sophia Chen", "sophia.c@library.edu", "9312456780", 21);
        seedStudentIfNotExists("student1", "student123", "Student One", "student1@library.edu", "9876543211", 20);

        // 3. Initialize Curated Book Catalog
        seedBookIfNotExists("978-0132350884", "Clean Code: A Handbook of Agile Software Craftsmanship",
                "Robert C. Martin", "Technology",
                "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code.",
                8, 6, "https://images.unsplash.com/photo-1532012164546-f432f2e372fe?w=400&q=80");

        seedBookIfNotExists("978-0201633610", "Design Patterns: Elements of Reusable Object-Oriented Software",
                "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Technology",
                "Capturing a wealth of experience about the design of object-oriented software, four top-notch designers present a catalog of simple and succinct solutions to commonly occurring design problems.",
                6, 5, "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400&q=80");

        seedBookIfNotExists("978-0135957059", "The Pragmatic Programmer: Your Journey to Mastery",
                "David Thomas, Andrew Hunt", "Technology",
                "Straight from the trenches, The Pragmatic Programmer cuts through the increasing specialization and technicalities of modern software development to examine the core process.",
                7, 5, "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=400&q=80");

        seedBookIfNotExists("978-0262033848", "Introduction to Algorithms (4th Edition)",
                "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein", "Education",
                "Comprehensive textbook covering a broad range of algorithms in depth, yet making their design and analysis accessible to all levels of readers.",
                10, 8, "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?w=400&q=80");

        seedBookIfNotExists("978-1449373320", "Designing Data-Intensive Applications",
                "Martin Kleppmann", "Technology",
                "Data is at the center of many challenges in system design today. Difficult issues need to be figured out, such as scalability, consistency, reliability, efficiency, and maintainability.",
                9, 7, "https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=400&q=80");

        seedBookIfNotExists("978-1617294945", "Spring in Action (6th Edition)",
                "Craig Walls", "Technology",
                "A hands-on guide to the Spring Framework, covering Spring Boot, reactive programming, microservices architecture, REST APIs, and modern cloud deployment.",
                8, 6, "https://images.unsplash.com/photo-1516259762381-22954d7d3ad2?w=400&q=80");

        seedBookIfNotExists("978-0134685991", "Effective Java (3rd Edition)",
                "Joshua Bloch", "Technology",
                "The definitive guide to best practices in the Java programming language. Contains 90 items exploring design patterns and language idioms for robust, clean architecture.",
                6, 5, "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?w=400&q=80");

        seedBookIfNotExists("978-0136042594", "Artificial Intelligence: A Modern Approach",
                "Stuart Russell, Peter Norvig", "Technology",
                "The authoritative reference in AI, spanning search algorithms, probabilistic reasoning, machine learning, deep neural architectures, and ethical AI systems.",
                7, 5, "https://images.unsplash.com/photo-1620712943543-bcc4688e7485?w=400&q=80");

        seedBookIfNotExists("978-1491929124", "Site Reliability Engineering: How Google Runs Production Systems",
                "Betsy Beyer, Niall Richard Murphy, David K. Rensin", "Technology",
                "Members of Google's SRE team explain how their commitment to the entire lifecycle has enabled Google to build, deploy, monitor, and maintain some of the largest systems in the world.",
                5, 4, "https://images.unsplash.com/photo-1504639725590-34d0984388bd?w=400&q=80");

        seedBookIfNotExists("978-0553380163", "A Brief History of Time",
                "Stephen Hawking", "Science",
                "A landmark volume in science writing by one of the great minds of our time, exploring the universe from the Big Bang to black holes and the nature of time.",
                8, 6, "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=400&q=80");

        seedBookIfNotExists("978-0374533557", "Thinking, Fast and Slow",
                "Daniel Kahneman", "Science",
                "Nobel laureate Daniel Kahneman takes us on a groundbreaking tour of the mind and explains the two systems that drive the way we think: fast, intuitive, and slow, deliberate.",
                7, 5, "https://images.unsplash.com/photo-1457369804613-52c61a468e7d?w=400&q=80");

        seedBookIfNotExists("978-0199291151", "The Selfish Gene",
                "Richard Dawkins", "Science",
                "A classic work on evolutionary biology that changed how humanity understands genetics, natural selection, altruism, and the origin of complex behavior.",
                6, 5, "https://images.unsplash.com/photo-1532187863486-abf9dbad1b69?w=400&q=80");

        seedBookIfNotExists("978-0345539434", "Cosmos",
                "Carl Sagan", "Science",
                "A magnificent exploration of the cosmic universe, science, and the human condition, tracing billions of years of cosmic evolution and scientific discovery.",
                8, 7, "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=400&q=80");

        seedBookIfNotExists("978-0062316097", "Sapiens: A Brief History of Humankind",
                "Yuval Noah Harari", "History",
                "100,000 years ago, at least six human species inhabited the earth. Today there is just one. Us. Homo sapiens. How did our species succeed in the battle for dominance?",
                10, 8, "https://images.unsplash.com/photo-1461360370896-922624d12aa1?w=400&q=80");

        seedBookIfNotExists("978-0140449334", "Meditations",
                "Marcus Aurelius", "Philosophy",
                "The private personal journal and philosophical reflections of the Roman Emperor Marcus Aurelius on Stoic philosophy, virtue, resilience, and ethical leadership.",
                7, 6, "https://images.unsplash.com/photo-1476275466078-4007374efbbe?w=400&q=80");

        seedBookIfNotExists("978-0393317558", "Guns, Germs, and Steel",
                "Jared Diamond", "History",
                "Pulitzer Prize-winning analysis of the geography, technology, and environmental factors that shaped modern civilizations across continents over 13,000 years.",
                6, 5, "https://images.unsplash.com/photo-1447069387593-a5de0862481e?w=400&q=80");

        seedBookIfNotExists("978-0735211292", "Atomic Habits: An Easy & Proven Way to Build Good Habits",
                "James Clear", "Education",
                "Tiny Changes, Remarkable Results. No matter your goals, Atomic Habits offers a proven framework for improving every day through compounding incremental choices.",
                12, 9, "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400&q=80");

        seedBookIfNotExists("978-0804139298", "Zero to One: Notes on Startups, or How to Build the Future",
                "Peter Thiel, Blake Masters", "Business",
                "The great secret of our time is that there are still uncharted frontiers to explore and new inventions to create. Doing what someone else already knows how to do takes the world from 1 to n.",
                8, 6, "https://images.unsplash.com/photo-1553729459-efe14ef6055d?w=400&q=80");

        seedBookIfNotExists("978-1455586691", "Deep Work: Rules for Focused Success in a Distracted World",
                "Cal Newport", "Business",
                "The ability to focus without distraction on a cognitively demanding task. Deep work is necessary to wring every last drop of value out of your current intellectual capacity.",
                7, 5, "https://images.unsplash.com/photo-1499750310107-5fef28a66643?w=400&q=80");

        seedBookIfNotExists("978-0307887894", "The Lean Startup",
                "Eric Ries", "Business",
                "How today's entrepreneurs use continuous innovation to create radically successful businesses with validated learning, rapid prototyping, and scientific iteration.",
                8, 7, "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=400&q=80");

        seedBookIfNotExists("978-0060935467", "To Kill a Mockingbird",
                "Harper Lee", "Fiction",
                "The unforgettable Pulitzer Prize-winning novel of childhood, empathy, racial injustice, and the crisis of conscience in a sleepy Southern town.",
                9, 7, "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?w=400&q=80");

        seedBookIfNotExists("978-0451524935", "1984",
                "George Orwell", "Fiction",
                "A chilling prophetic masterpiece about authoritarian totalitarianism, surveillance, manipulation of language, and individual resistance.",
                8, 6, "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=400&q=80");

        seedBookIfNotExists("978-0743273565", "The Great Gatsby",
                "F. Scott Fitzgerald", "Fiction",
                "The exemplary novel of the Jazz Age, charting the rise, glamour, obsessions, and tragic disillusionment of Jay Gatsby and high society.",
                7, 6, "https://images.unsplash.com/photo-1541963463532-d68292c34b19?w=400&q=80");

        seedBookIfNotExists("978-0140449136", "Crime and Punishment",
                "Fyodor Dostoevsky", "Fiction",
                "A towering psychological drama of guilt, conscience, moral redemption, and the inner turmoil of Rodion Raskolnikov in 19th-century Saint Petersburg.",
                6, 5, "https://images.unsplash.com/photo-1495640388908-05fa85288e61?w=400&q=80");

        // 4. Initialize Realistic Institutional Circulation Records
        if (borrowRecordRepository.count() == 0) {
            bookRepository.findById("978-0132350884").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student1, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(5));
                r.setDueDate(LocalDate.now().plusDays(9));
                r.setStatus(BorrowStatus.ACTIVE);
                borrowRecordRepository.save(r);
            });

            bookRepository.findById("978-1449373320").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student1, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(22));
                r.setDueDate(LocalDate.now().minusDays(8));
                r.setReturnDate(LocalDateTime.now().minusDays(7));
                r.setStatus(BorrowStatus.RETURNED);
                borrowRecordRepository.save(r);
            });

            bookRepository.findById("978-0735211292").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student2, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(3));
                r.setDueDate(LocalDate.now().plusDays(11));
                r.setStatus(BorrowStatus.ACTIVE);
                borrowRecordRepository.save(r);
            });

            bookRepository.findById("978-0135957059").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student3, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(18));
                r.setDueDate(LocalDate.now().minusDays(4));
                r.setStatus(BorrowStatus.ACTIVE); // Overdue active loan for analytics
                borrowRecordRepository.save(r);
            });

            bookRepository.findById("978-0062316097").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student4, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(8));
                r.setDueDate(LocalDate.now().plusDays(6));
                r.setStatus(BorrowStatus.ACTIVE);
                borrowRecordRepository.save(r);
            });

            bookRepository.findById("978-0804139298").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student5, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(12));
                r.setDueDate(LocalDate.now().plusDays(2));
                r.setStatus(BorrowStatus.ACTIVE);
                borrowRecordRepository.save(r);
            });

            bookRepository.findById("978-0451524935").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student6, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(30));
                r.setDueDate(LocalDate.now().minusDays(16));
                r.setReturnDate(LocalDateTime.now().minusDays(15));
                r.setStatus(BorrowStatus.RETURNED);
                borrowRecordRepository.save(r);
            });

            bookRepository.findById("978-0262033848").ifPresent(book -> {
                BorrowRecord r = new BorrowRecord(student7, book);
                r.setBorrowDate(LocalDateTime.now().minusDays(4));
                r.setDueDate(LocalDate.now().plusDays(10));
                r.setStatus(BorrowStatus.ACTIVE);
                borrowRecordRepository.save(r);
            });

            System.out.println(">>> Initialized realistic institutional circulation records and loan telemetry.");
        }
    }

    private User seedStudentIfNotExists(String username, String rawPassword, String fullName, String email, String phone, int age) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            User u = new User(
                    username,
                    passwordEncoder.encode(rawPassword),
                    fullName,
                    email,
                    phone,
                    age,
                    Role.ROLE_STUDENT
            );
            return userRepository.save(u);
        });
    }

    private void seedBookIfNotExists(String isbn, String title, String author, String category,
                                     String description, int total, int available, String coverUrl) {
        if (!bookRepository.existsById(isbn)) {
            Book book = new Book(isbn, title, author, category, description, total, available, coverUrl);
            bookRepository.save(book);
        }
    }
}
