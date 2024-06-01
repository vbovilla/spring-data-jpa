package in.dminc.bootstrap;

import in.dminc.domain.AuthorUuid;
import in.dminc.domain.Book;
import in.dminc.domain.BookUuid;
import in.dminc.repositories.AuthorRepository;
import in.dminc.repositories.AuthorUuidRepository;
import in.dminc.repositories.BookRepository;
import in.dminc.repositories.BookUuidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;

    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository, BookUuidRepository bookUuidRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // remove all records, if present
        bookRepository.deleteAll();

        // add bookDDD
        Book bookDDD = new Book("Domain Driven Design", "0321125215", "Addison-Wesley", null);
        log.debug("Domain Driven Design - Id: {}", bookDDD.getId());
        Book savedDDD = bookRepository.save(bookDDD);
        log.debug("Domain Driven Design - Id: {}", savedDDD.getId());

        // add bookSIA
        Book bookSIA = new Book("Spring In Action", "9781617294945", "Manning Publications", null);
        log.debug("Spring In Action - Id: {}", bookSIA.getId());
        Book savedSIA = bookRepository.save(bookSIA);
        log.debug("Spring In Action - Id: {}", savedSIA.getId());

        bookRepository.findAll().forEach(book -> {
            log.debug("Book Id: {}", book.getId());
            log.debug("Book Title: {}", book.getTitle());
        });

        AuthorUuid author = new AuthorUuid();
        author.setFirstName("Balagurusamy");
        author.setLastName("E");
        AuthorUuid savedAuthor = authorUuidRepository.save(author);
        log.debug("Author Id: {}", savedAuthor.getId());

        BookUuid bookUuid = new BookUuid();
        bookUuid.setIsbn("935316513X");
        bookUuid.setPublisher("McGraw Hill Education (India) Private Limited");
        bookUuid.setTitle("Programming In Ansi C");
        BookUuid savedBook = bookUuidRepository.save(bookUuid);
        log.debug("Book Id: {}", savedBook.getId());
    }
}
