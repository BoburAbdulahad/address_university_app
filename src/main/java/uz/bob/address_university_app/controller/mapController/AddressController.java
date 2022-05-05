package uz.bob.address_university_app.controller.mapController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.map.Address;
import uz.bob.address_university_app.payload.mapPayload.AddressDto;
import uz.bob.address_university_app.repository.mapRepository.AddressRepository;
import uz.bob.address_university_app.repository.mapRepository.DistrictRepository;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    AddressRepository addressRepository;

    @GetMapping
    public List<Address>get(){
        return addressRepository.findAll();
    }
    @PostMapping
    public String add(@RequestBody AddressDto addressDto){
        Address address=new Address(null,addressDto.getStreet(), addressDto.getHomeNumber(),districtRepository.getById(addressDto.getDistrictId()));
        addressRepository.save(address);
        return "Address saved";
    }

    @PutMapping("/{id}")// TODO: 5/5/2022 check put mapping and post mapping
    public String edit(@PathVariable Integer id,@RequestBody AddressDto addressDto){
        boolean exist = addressRepository.existsById(id);
        if (!exist) {
            return "Address not found";
        }
        Address address = addressRepository.getById(id);
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        address.setDistrict(districtRepository.getById(addressDto.getDistrictId()));
        addressRepository.save(address);
        return "Address edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!addressRepository.findById(id).isPresent()) {
            return "Address not found";
        }
        addressRepository.deleteById(id);
        return "Address deleted";
    }

}
