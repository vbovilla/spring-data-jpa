package in.dminc;

import in.dminc.domain.Book;
import in.dminc.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Commit
@DataJpaTest
@ComponentScan(basePackages = {"in.dminc.bootstrap"})
public class SpringBootJpaTestSliceWithDataInitializer {
    @Autowired
    BookRepository bookRepository;

    //    @Rollback(false)
    @Commit
    @Order(1)
    @Test
    void testJpaTestSplice() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
        bookRepository.save(new Book("My Book", "1234567890", "Self"));
        long countAfter = bookRepository.count();

        assertThat(countBefore).isLessThan(countAfter);
        assertThat(countAfter).isGreaterThan(countBefore);
    }

    @Order(2)
    @Test
    void testJpaTestSpliceTransaction() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);   //2 (from data initialization) + 1 (from method above)
    }
}
