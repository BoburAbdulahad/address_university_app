package uz.bob.address_university_app.controller.mapController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.map.District;
import uz.bob.address_university_app.payload.mapPayload.DistrictDto;
import uz.bob.address_university_app.repository.mapRepository.DistrictRepository;
import uz.bob.address_university_app.repository.mapRepository.RegionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/district")
public class DistrictController {

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    RegionRepository regionRepository;

    @GetMapping
    public List<District>get(){
        return districtRepository.findAll();
    }
    @PostMapping
    public String add(@RequestBody DistrictDto districtDto){
        District district=new District(null,districtDto.getName(),regionRepository.getById(districtDto.getRegionId()));
        districtRepository.save(district);
        return "District saved";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody DistrictDto districtDto){
        if (!districtRepository.findById(id).isPresent()) {
            return "District not found";
        }
        District district = districtRepository.getById(id);
        district.setName(districtDto.getName());
        district.setRegion(regionRepository.getById(districtDto.getRegionId()));
        return "District edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (!optionalDistrict.isPresent()) {
            return "District not found";
        }
        districtRepository.deleteById(id);
        return "District deleted";
    }

}
