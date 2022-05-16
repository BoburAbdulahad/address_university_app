package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.university.Faculty;
import uz.bob.address_university_app.entity.university.University;
import uz.bob.address_university_app.payload.universityPayload.FacultyDto;
import uz.bob.address_university_app.repository.universityRepository.FacultyRepository;
import uz.bob.address_university_app.repository.universityRepository.UniversityRepository;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    // for Ministry: get all faculties
    @GetMapping
    public List<Faculty>get(){
        return facultyRepository.findAll();
    }

    //for university employee: get all faculties only by university_id
    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId){
        List<Faculty> allByUniversityId = facultyRepository.findAllByUniversityId(universityId);
//        Iterable<Faculty> iterable=() ->
//        Iterator<Faculty> iterator=facultyRepository.findAllById()
//        iterator.next()//   search in you tube
//        Faculty faculty = iterator.next();
//        facultyRepository.findAllById(() -> )
        return allByUniversityId;
    }

    //CREATE
    @PostMapping
    public String add(@RequestBody FacultyDto facultyDto){
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "This university such as faculty exist";
        Faculty faculty=new Faculty();
        faculty.setName(facultyDto.getName());
        Optional<University> optionalUniversity = universityRepository.findById(facultyDto.getUniversityId());
        if (!optionalUniversity.isPresent())
            return "University not found";
        faculty.setUniversity(optionalUniversity.get());
        facultyRepository.save(faculty);
        return "Faculty added";
//        Faculty faculty=new Faculty(null,
//                                    facultyDto.getName(),
//                                    universityRepository.getById(facultyDto.getUniversityId())
//                                    );
//        facultyRepository.save(faculty);
//        return "Faculty saved";
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody FacultyDto facultyDto){
        if (!facultyRepository.findById(id).isPresent()) {
            return "Faculty not found";
        }
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyDto.getName(), facultyDto.getUniversityId());
        if (exists)
            return "This faculty already exist in the university";
        if (!universityRepository.findById(facultyDto.getUniversityId()).isPresent())
            return "University not found";
        Faculty faculty = facultyRepository.getById(id);
        faculty.setName(facultyDto.getName());
        faculty.setUniversity(universityRepository.getById(facultyDto.getUniversityId()));
        facultyRepository.save(faculty);
        return "Faculty edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!facultyRepository.findById(id).isPresent()) {
            return "Faculty not deleted";
        }
        facultyRepository.deleteById(id);
        return "Faculty deleted";
    }
}