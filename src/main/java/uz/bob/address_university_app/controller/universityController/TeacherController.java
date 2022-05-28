package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.university.Subject;
import uz.bob.address_university_app.entity.university.Teacher;
import uz.bob.address_university_app.payload.universityPayload.TeacherDto;
import uz.bob.address_university_app.repository.universityRepository.SubjectRepository;
import uz.bob.address_university_app.repository.universityRepository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;

    List<Subject>subjectList=new ArrayList<>();

    @GetMapping
    public List<Teacher> get(){
        return teacherRepository.findAll();
    }
    @GetMapping("/{id}")
    public Teacher getById(@PathVariable Integer id){
        if (!teacherRepository.findById(id).isPresent()) {
            return new Teacher();
        }
        return teacherRepository.getById(id);
    }
    @PostMapping
    public String add(@RequestBody TeacherDto teacherDto){
        subjectList.clear();
        Teacher teacher=new Teacher();
        teacher.setFullName(teacherDto.getFullName());
        List<Integer> subjectsIds = teacherDto.getSubjectsIds();
        subjectsIds.forEach(integer -> subjectRepository.findById(integer).ifPresent(subjectList::add));
        if (subjectList.isEmpty()) {
            return "Subject not found";
        }
        teacher.setSubjects(subjectList);
        teacherRepository.save(teacher);
        subjectList.clear();
        return "Teacher saved";
    }

    // TODO: 5/19/2022 teacher classida turib, teacher va subjectId ga birgalikda unique constraint qoyishni organish kk
    //teacher classida qoyib bolmadi sababi teacher table da subjectId yoq, subjectId esa teacher_subject table da
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody TeacherDto teacherDto){
        subjectList.clear();
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if (!optionalTeacher.isPresent()) {
            return "Teacher not found";
        }
        Teacher teacher = optionalTeacher.get();
        teacher.setFullName(teacherDto.getFullName());
        List<Integer> subjectsIds = teacherDto.getSubjectsIds();
        for (Integer subjectsId : subjectsIds) {
            Subject subject = subjectRepository.getById(subjectsId);
            subjectList.add(subject);
        }
        teacher.setSubjects(subjectList);
        teacherRepository.save(teacher);
        subjectList.clear();
        return "Teacher edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!teacherRepository.findById(id).isPresent()) {
            return "Teacher not deleted";
        }
        teacherRepository.deleteById(id);
        return "Teacher deleted";
    }

}
