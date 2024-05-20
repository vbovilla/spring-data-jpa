package in.dminc;

import com.mongodb.client.result.DeleteResult;
import in.dminc.model.Address;
import in.dminc.model.Gender;
import in.dminc.model.Student;
import in.dminc.repository.StudentRepository;
import in.dminc.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@SpringBootApplication
public class SpringDataMangodbAmigosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataMangodbAmigosApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentService studentService, MongoTemplate mongoTemplate, StudentRepository studentRepository) {
        return args -> {
            Address address = new Address("INDIA", "Hyderabad", "500090");
            Student student = new Student(
                "Harini",
                "Bovilla",
                "harini.bovilla@gmail.com",
                Gender.FEMALE,
                address,
                List.of("Computer Science", "Maths"),
                BigDecimal.TEN, LocalDateTime.now()
            );

            addUsingMongoTemplateAndQuery(studentService, mongoTemplate, student);
//            crudOperationsUsingTemplateAndQuery(mongoTemplate, studentRepository);
        };


    }

    private static void crudOperationsUsingTemplateAndQuery(MongoTemplate mongoTemplate, StudentRepository studentRepository) {
        studentRepository.findStudentsByLastName("Bovilla")
            .ifPresentOrElse(students -> System.out.println("Found " + students.size() + " students"), () -> System.out.println("Student not found"));

        studentRepository.findStudentsByEmail("vihar.bovilla@gmail.com")
            .ifPresentOrElse(s -> System.out.println("student exist.. !!\n" + s), () -> System.out.println("Student not found"));


        // find by id
        Student studentById = mongoTemplate.findById("664b358d09678678ae7458bb", Student.class);
        System.out.println("studentById: " + studentById);

        // find all
        List<Student> allStudents = mongoTemplate.findAll(Student.class);
        System.out.println(allStudents);

        // remove student
        DeleteResult remove = mongoTemplate.remove(studentById);
        System.out.println(remove);

        // drop collection
        mongoTemplate.dropCollection(Student.class);
    }

    private static void addUsingMongoTemplateAndQuery(StudentService studentService, MongoTemplate mongoTemplate, Student student) {
        Query query = new Query();
        query.addCriteria(where("email").is(student.getEmail()));

        List<Student> students = mongoTemplate.find(query, Student.class);
        if (!students.isEmpty()) {
            throw new IllegalStateException("found " + students.size() + " students with email " + student.getEmail());
        } else {
            System.out.println("Inserting student " + student);
            Student savedStudent = studentService.save(student);
            System.out.println(savedStudent);
        }
    }
}
