package uz.bob.address_university_app.payload.universityPayload;

import lombok.Data;

@Data
public class TimeTableDto {
    private String dayName;
    private String startTime;
    private String endTime;
    private Integer groupId;
    private Integer subjectId;
    private Integer teacherId;
}
