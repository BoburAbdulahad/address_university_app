package uz.bob.address_university_app.repository.universityRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.address_university_app.entity.university.Student;
import uz.bob.address_university_app.entity.university.University;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

}
