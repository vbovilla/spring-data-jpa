package in.dminc.repository;


import in.dminc.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<List<Student>> findStudentsByLastName(String lastName);

    Optional<Student> findStudentsByEmail(String email);
}
