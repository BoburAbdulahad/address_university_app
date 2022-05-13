package uz.bob.address_university_app.controller.universityController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.university.TimeTable;
import uz.bob.address_university_app.payload.universityPayload.TimeTableDto;
import uz.bob.address_university_app.repository.universityRepository.GroupRepository;
import uz.bob.address_university_app.repository.universityRepository.SubjectRepository;
import uz.bob.address_university_app.repository.universityRepository.TeacherRepository;
import uz.bob.address_university_app.repository.universityRepository.TimeTableRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("timeTable")
public class TimeTableController {

    @Autowired
    TimeTableRepository timeTableRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping
    public List<TimeTable>get(){
        return timeTableRepository.findAll();
    }
    @PostMapping
    public String add(@RequestBody TimeTableDto timeTableDto){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime=LocalTime.parse(timeTableDto.getStartTime(),formatter);


        TimeTable timeTable=new TimeTable(
                null,
                timeTableDto.getDayName(),
                startTime,
                startTime.plusMinutes(45),
                groupRepository.getById(timeTableDto.getGroupId()),
                subjectRepository.getById(timeTableDto.getSubjectId()),
                teacherRepository.getById(timeTableDto.getTeacherId())
        );
        timeTableRepository.save(timeTable);
        return "TimeTable saved";
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody TimeTableDto timeTableDto){
        if (!timeTableRepository.findById(id).isPresent()) {
            return "TimeTable not found";
        }
        TimeTable timeTable = timeTableRepository.getById(id);// TODO: 5/7/2022 check this row with debug
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime=LocalTime.parse(timeTableDto.getStartTime(),formatter);
// TODO: 5/7/2022 this method should write timetable.setGroup,setSubject,setDayName() ...

        timeTable=new TimeTable(
                null,
                timeTableDto.getDayName(),
                startTime,
                startTime.plusMinutes(45),
                groupRepository.getById(timeTableDto.getGroupId()),
                subjectRepository.getById(timeTableDto.getSubjectId()),
                teacherRepository.getById(timeTableDto.getTeacherId())
        );
        timeTableRepository.save(timeTable);
        return "TimeTable saved";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<TimeTable> optionalTimeTable = timeTableRepository.findById(id);
        if (!optionalTimeTable.isPresent()) {
            return "TimeTable not found";
        }
        timeTableRepository.delete(optionalTimeTable.get());
        return "TimeTable deleted";
    }
}
