package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.map.Address;
import uz.bob.address_university_app.entity.university.University;
import uz.bob.address_university_app.payload.universityPayload.UniversityDto;
import uz.bob.address_university_app.repository.mapRepository.AddressRepository;
import uz.bob.address_university_app.repository.mapRepository.DistrictRepository;
import uz.bob.address_university_app.repository.universityRepository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/university")
public class UniversityController {
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AddressRepository addressRepository;

    @GetMapping
    public List<University> get() {
        return universityRepository.findAll();
    }

    @PostMapping
    public String add(@RequestBody UniversityDto universityDto) {
        University university=new University(null,
                universityDto.getName(),
                addressRepository.getById(universityDto.getAddressId())
        );
        universityRepository.save(university);
        return "University saved";
        //this up code for check
//        Address address = new Address(null,
//                universityDto.getStreet(),
//                universityDto.getHomeNumber(),
//                districtRepository.getById(universityDto.getDistrictId())
//                );
//        Address savedAddress = addressRepository.save(address);
//        University university=new University(null, universityDto.getName(), savedAddress);
//        universityRepository.save(university);
//        return "University saved";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (!optionalUniversity.isPresent()) {
            return "University not found";
        }
        University university = optionalUniversity.get();//universitet.setAddress emas
        university.setName(universityDto.getName());// universitet.getAddress qilib keyin qiymatlar set
        university.setAddress(addressRepository.getById(id));// qilinishi kerak...
        universityRepository.save(university);
        return "University edited";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        if (!universityRepository.findById(id).isPresent()) {
            return "University not deleted";
        }
        universityRepository.deleteById(id);
        return "University deleted";
    }

}
