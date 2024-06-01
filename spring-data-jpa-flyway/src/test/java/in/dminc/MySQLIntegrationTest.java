package in.dminc;


import in.dminc.domain.AuthorUuid;
import in.dminc.domain.BookNatural;
import in.dminc.domain.BookUuid;
import in.dminc.repositories.AuthorUuidRepository;
import in.dminc.repositories.BookNaturalRepository;
import in.dminc.repositories.BookRepository;
import in.dminc.repositories.BookUuidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"in.dminc.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }

    @Test
    void testBookUuid() {
        BookUuid savedBook = bookUuidRepository.save(new BookUuid());
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    void testAuthorUuid() {
        AuthorUuid authorUuid = authorUuidRepository.save(new AuthorUuid());
        assertThat(authorUuid).isNotNull();
        assertThat(authorUuid.getId()).isNotNull();
    }

    @Test
    void testBookNatural(){
        BookNatural savedBook = bookNaturalRepository.save(new BookNatural());
        assertThat(savedBook).isNotNull();
    }
}
