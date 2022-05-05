package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.bob.address_university_app.entity.university.University;
import uz.bob.address_university_app.repository.universityRepository.UniversityRepository;

import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @GetMapping
    public List<University>get(){
        return universityRepository.findAll();
    }

}
