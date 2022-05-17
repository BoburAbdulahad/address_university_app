package uz.bob.address_university_app.repository.universityRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bob.address_university_app.entity.university.Group;
import uz.bob.address_university_app.entity.university.University;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {

    //my self code
    boolean existsByNameAndFaculty_Id(String name, Integer faculty_id);

    //it's jpa query
    List<Group> findAllByFaculty_UniversityId(Integer faculty_university_id);


    @Query("select dr from groups dr where dr.faculty.university.id=:universityId")
    List<Group> getGroupsByUniversityId(Integer universityId);

    @Query(value = "select * from groups g join faculty f on g.faculty_id=f.id " +
            "join university u on f.university_id=u.id where u.id=:universityId",nativeQuery = true)
    List<Group>getGroupsByUniversityIdNative(Integer universityId);

    //===my self code
    List<Group> findAllByFaculty_Id(Integer faculty_id);
}
