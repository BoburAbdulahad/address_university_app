package uz.bob.address_university_app.controller.mapController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.map.Region;
import uz.bob.address_university_app.payload.mapPayload.RegionDto;
import uz.bob.address_university_app.repository.mapRepository.CountryRepository;
import uz.bob.address_university_app.repository.mapRepository.RegionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    CountryRepository countryRepository;

    @GetMapping
    public List<Region>get(){
        return regionRepository.findAll();
    }
    @PostMapping
    public String add(@RequestBody RegionDto regionDto){
        if (!countryRepository.findById(regionDto.getCountryId()).isPresent()) {
            return "Country not found";
        }
        Region region=new Region(null, regionDto.getName(),countryRepository.getById(regionDto.getCountryId()));
        regionRepository.save(region);
        return "Region saved";
    }
    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody RegionDto regionDto){
        Optional<Region> optionalRegion = regionRepository.findById(id);
        if (!optionalRegion.isPresent()){
            return "Region not found";
        }
        Region region = optionalRegion.get();
        region.setName(regionDto.getName());
        if (!countryRepository.findById(regionDto.getCountryId()).isPresent()) {
            return "Country not found";
        }
        region.setCountry(countryRepository.getById(regionDto.getCountryId()));
        regionRepository.save(region);
        return "Region edited";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!regionRepository.findById(id).isPresent()) {
            return "Region not deleted";
        }
        regionRepository.delete(regionRepository.getById(id));
        return "Region deleted";
    }

}
