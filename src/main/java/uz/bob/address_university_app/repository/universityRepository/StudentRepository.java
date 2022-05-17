package uz.bob.address_university_app.repository.universityRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.bob.address_university_app.entity.university.Faculty;
import uz.bob.address_university_app.entity.university.Student;
import uz.bob.address_university_app.entity.university.University;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    //==================get students by university id
    List<Student>findAllByGroup_Faculty_UniversityId(Integer group_faculty_university_id);

    @Query(value = "select st from Student st where st.group.faculty.university.id=:universityId")
    List<Student>getStudentByUniversityId(Integer universityId);

    @Query(value = "select * from Student s join groups g on s.group_id=g.id" +
            " join Faculty f on f.id=g.faculty_id join University u on u.id=f.university_id " +
            "where u.id=:universityId",nativeQuery = true)
    List<Student>getStudentByUniversityIdNative(Integer universityId);


    //=======================================univerId and facultyId

    @Query(value = "select * from" +
            "(select * from student s join groups g on g.id=s.group_id join faculty f on f.id=g.faculty_id " +
            "join university u on u.id=f.university_id where u.id=:univerId) " +
            "as foo where faculty_id=:faculId",nativeQuery = true)
    List<Student> getStudentByUniversityIdAndFacultyId(Integer univerId,Integer faculId);

    //=========================get students by faculty id
    List<Student>findAllByGroup_FacultyId(Integer group_faculty_id);

    @Query("select st from Student st where st.group.faculty.id=:facultyId")
    List<Student>getStudentByFacultyId(Integer facultyId);

    @Query(value = "select * from Student s join groups g on g.id=s.group_id join Faculty f on f.id=g.faculty_id " +
            "where f.id=:facultyId",nativeQuery = true)
    List<Student>getStudentByFacultyIdNative(Integer facultyId);
//  ===========================================================
    // TODO: 5/17/2022 get students by group id write 3 query


    // TODO: 5/17/2022 get students by university id and faculty id and group id write native query
}
