package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.university.Faculty;
import uz.bob.address_university_app.payload.universityPayload.FacultyDto;
import uz.bob.address_university_app.repository.universityRepository.FacultyRepository;
import uz.bob.address_university_app.repository.universityRepository.UniversityRepository;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    UniversityRepository universityRepository;

    @GetMapping
    public List<Faculty>get(){
        return facultyRepository.findAll();
    }
    @PostMapping
    public String add(@RequestBody FacultyDto facultyDto){
        Faculty faculty=new Faculty(null, facultyDto.getName(),
                universityRepository.getById(facultyDto.getUniversityId()));
        facultyRepository.save(faculty);
        return "Faculty saved";
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody FacultyDto facultyDto){
        if (!facultyRepository.findById(id).isPresent()) {
            return "Faculty not found";
        }
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