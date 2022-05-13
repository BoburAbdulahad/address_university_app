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

    @GetMapping
    public List<Group>get(){
        return groupRepository.findAll();
    }
    @PostMapping
    public String add(@RequestBody GroupDto groupDto){
        Group group=new Group(null, groupDto.getName(),
                facultyRepository.getById(groupDto.getFacultyId()));
        groupRepository.save(group);
        return "Group saved";
    }
    @PutMapping("/{id}")
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
