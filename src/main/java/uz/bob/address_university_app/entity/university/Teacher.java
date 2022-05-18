package uz.bob.address_university_app.entity.university;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // TODO: 5/18/2022 set unique constraint to teacher table for name and subjects ids
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"fullName",""}))
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @ManyToMany
    private List<Subject> subjects;

}
