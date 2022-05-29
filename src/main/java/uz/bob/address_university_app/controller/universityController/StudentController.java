package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.university.Student;
import uz.bob.address_university_app.payload.universityPayload.StudentDto;
import uz.bob.address_university_app.repository.universityRepository.GroupRepository;
import uz.bob.address_university_app.repository.universityRepository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    GroupRepository groupRepository;

    // for ministry
    @GetMapping()
    public Page<Student> get(@RequestParam int page,@RequestParam int size){
        Pageable pageable= PageRequest.of(page, size);
        return studentRepository.findAll(pageable);
    }

//     for university employee
    @GetMapping("/byUniversityId/{universityId}")
    public Page<Student> getStudentByUniversityId(@PathVariable Integer universityId,@RequestParam int page,@RequestParam int size){
        Pageable pageable=PageRequest.of(page, size);
        Page<Student> studentByUniversityIdNative = studentRepository.getStudentByUniversityIdNative(universityId,pageable);
        List<Student> studentByUniversityId = studentRepository.getStudentByUniversityId(universityId);
        List<Student> allByGroup_faculty_universityId = studentRepository.findAllByGroup_Faculty_UniversityId(universityId);
        return studentByUniversityIdNative;
    }

    //for university employee - university id and faculty id
    @GetMapping("/byUniversityAndFacultyId/{universityId}/{facultyId}")
    public List<Student>getStudentByUniversityIdAndFacultyId(@PathVariable Integer universityId,@PathVariable Integer facultyId){
        List<Student> studentByUniversityIdAndFacultyId = studentRepository.getStudentByUniversityIdAndFacultyId(universityId, facultyId);
        return studentByUniversityIdAndFacultyId;


    }//for rectorate employee -get student by faculty id
    @GetMapping("/jpaNew/{facultyId}/{page}/{size}")
    public Page<Student>getStudentByFacultyId(@PathVariable Integer facultyId, @PathVariable Integer page, @PathVariable Integer size){
        Pageable pageable=PageRequest.of(page,size);
        List<Student> allByGroup_facultyId = studentRepository.findAllByGroup_FacultyId(facultyId);
        Page<Student> studentByFacultyId = studentRepository.getStudentByFacultyId(facultyId,pageable);
        List<Student>getList=studentRepository.getStudentByFacultyIdNative(facultyId);
        return studentByFacultyId;
    }
    //for decanat employee -get student by group id
    @GetMapping("/byGroupId/{groupId}")
    public Page<Student> getStudentByGroupId(@PathVariable Integer groupId,@RequestParam Integer page, @RequestParam Integer size){
        Pageable pageable=PageRequest.of(page,size);
        Page<Student> allByGroup_id = studentRepository.findAllByGroup_Id(groupId,pageable);
        List<Student> studentByGroupId = studentRepository.getStudentByGroupId(groupId);
        List<Student> studentListNative=studentRepository.getStudentByNative(groupId);
        return allByGroup_id;
    }

    //get student by university id and facultyId and group id
    @GetMapping("/threeId/{uId}/{fId}/{gId}")
    public List<Student>getStudentByThreeId(@PathVariable Integer uId,@PathVariable Integer fId,@PathVariable Integer gId){
        List<Student> list = studentRepository.getStudentByUniversityIdAndFacultyIdAndGroupId(uId, fId, gId);
        return list;
    }






    // get student by id
    @GetMapping("/{id}")
    public Student getById(@PathVariable Integer id){
        if (!studentRepository.findById(id).isPresent()) {
            return new Student();
        }
        return studentRepository.getById(id);
    }
    @PostMapping
    public String add(@RequestBody StudentDto studentDto){
        Student student=new Student(null,
                studentDto.getFirstName(),
                studentDto.getLastName(),
                groupRepository.getById(studentDto.getGroupId())
                );
        studentRepository.save(student);
        return "Student saved";
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody StudentDto studentDto){
        if (!studentRepository.findById(id).isPresent()) {
            return "Student not found";
        }
        Student student = studentRepository.getById(id);
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setGroup(groupRepository.getById(studentDto.getGroupId()));
        studentRepository.save(student);
        return "Student edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!studentRepository.findById(id).isPresent()) {
            return "Student not found";
        }
        studentRepository.deleteById(id);
        return "Student deleted";
    }

}
