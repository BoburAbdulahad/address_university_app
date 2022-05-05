package uz.bob.address_university_app.payload.mapPayload;

import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private Integer homeNumber;
    private Integer districtId;
}
