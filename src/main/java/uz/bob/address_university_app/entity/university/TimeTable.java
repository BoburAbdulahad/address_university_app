package uz.bob.address_university_app.entity.university;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String dayName;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @OneToOne(optional = false)
    private Group group;

    @OneToOne(optional = false)
    private Subject subject;

    @OneToOne(optional = false)
    private Teacher teacher;

    public TimeTable(String dayName, LocalTime startTime, LocalTime endTime, Group group, Subject subject, Teacher teacher) {
        this.dayName = dayName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
    }
}
