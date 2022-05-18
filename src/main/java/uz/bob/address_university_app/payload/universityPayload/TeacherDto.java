package uz.bob.address_university_app.payload.universityPayload;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDto {
    private String fullName;
    private List<Integer> subjectsIds;
}
