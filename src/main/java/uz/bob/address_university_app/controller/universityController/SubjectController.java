package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.university.Subject;
import uz.bob.address_university_app.repository.universityRepository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping
    public List<Subject>get(){
        return subjectRepository.findAll();
    }
    @GetMapping("/{id}")
    public Subject getById(@PathVariable Integer id){
        if (!subjectRepository.findById(id).isPresent()){
            return new Subject();
        }
        return subjectRepository.getById(id);
    }

    @PostMapping
    public String add(@RequestBody Subject subject){
        if (subjectRepository.existsByName(subject.getName())){
            return "This name subject already exist";
        }
        subjectRepository.save(subject);
        return "Subject saved";
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody Subject subject){
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (!optionalSubject.isPresent())
            return "Subject not";
        Subject subject1 = optionalSubject.get();
        subject1.setName(subject.getName());
        subjectRepository.save(subject1);
        return "Subject edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        subjectRepository.deleteById(id);
        return "Subject deleted";
    }


}
