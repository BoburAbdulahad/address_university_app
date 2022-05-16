package uz.bob.address_university_app.repository.universityRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.address_university_app.entity.university.Group;
import uz.bob.address_university_app.entity.university.University;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {

    boolean existsByNameAndFaculty_Id(String name, Integer faculty_id);

    List<Group> findAllByFaculty_UniversityId(Integer faculty_university_id);

    List<Group> findAllByFaculty_Id(Integer faculty_id);
}
