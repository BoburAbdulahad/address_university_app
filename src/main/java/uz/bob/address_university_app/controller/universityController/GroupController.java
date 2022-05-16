package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.university.Group;
import uz.bob.address_university_app.payload.universityPayload.GroupDto;
import uz.bob.address_university_app.repository.universityRepository.FacultyRepository;
import uz.bob.address_university_app.repository.universityRepository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    FacultyRepository facultyRepository;

    //for Ministry
    @GetMapping
    public List<Group>get(){
        return groupRepository.findAll();
    }

    //for University rectorate Employee by university id
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId){
        List<Group> groupList = groupRepository.findAllByFaculty_UniversityId(universityId);
        return groupList;
    }

    //fakultet id si orqali groups larni chaqirish
    @GetMapping("/byFacultyId/{facultyId}")
    public List<Group> getGroupsByFacultyId(@PathVariable Integer facultyId){
        List<Group> allByGr = groupRepository.findAllByFaculty_Id(facultyId);
        return allByGr;
    }


    @PostMapping
    public String add(@RequestBody GroupDto groupDto){
//        Group group=new Group(null, groupDto.getName(),
//                facultyRepository.getById(groupDto.getFacultyId()));
//        groupRepository.save(group);3
//        return "Group saved";
        boolean existsByNameAndFaculty_id = groupRepository.existsByNameAndFaculty_Id(groupDto.getName(), groupDto.getFacultyId());
        if (existsByNameAndFaculty_id)
            return "Such as group already exist in the faculty";
        if (!facultyRepository.findById(groupDto.getFacultyId()).isPresent())
            return "Faculty not found";
        Group group=new Group();
        group.setName(groupDto.getName());
        group.setFaculty(facultyRepository.getById(groupDto.getFacultyId()));
        groupRepository.save(group);
        return "Group saved";
    }
    @PutMapping("/{id}")//todo full write method
    public String edit(@PathVariable Integer id,@RequestBody GroupDto groupDto){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (!optionalGroup.isPresent()) {
            return "Group not found";
        }
        Group group = optionalGroup.get();
        group.setName(groupDto.getName());

        group.setFaculty(facultyRepository.getById(groupDto.getFacultyId()));
        groupRepository.save(group);
        return "Group edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!groupRepository.findById(id).isPresent()) {
            return "Group not found";
        }
        groupRepository.deleteById(id);
        return "Group deleted";
    }
}
