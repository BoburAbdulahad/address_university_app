package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Student>get(){
        return studentRepository.findAll();
    }
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
