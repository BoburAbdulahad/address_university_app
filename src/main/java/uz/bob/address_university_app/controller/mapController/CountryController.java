package uz.bob.address_university_app.controller.mapController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.map.Continent;
import uz.bob.address_university_app.entity.map.Country;
import uz.bob.address_university_app.payload.mapPayload.CountryDto;
import uz.bob.address_university_app.repository.mapRepository.ContinentRepository;
import uz.bob.address_university_app.repository.mapRepository.CountryRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    ContinentRepository continentRepository;

    @GetMapping
    public List<Country>get(){
        return countryRepository.findAll();
    }
    @PostMapping
    public String add(@RequestBody CountryDto countryDto){
        Optional<Continent> optionalContinent = continentRepository.findById(countryDto.getContinentId());
        if (!optionalContinent.isPresent()) {
            return "Continent not founded";
        }
        Country country=new Country(null, countryDto.getName(),optionalContinent.get());
        countryRepository.save(country);
        return "Country saved";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody CountryDto countryDto){
        if (!countryRepository.findById(id).isPresent()) {
            return "Country not edited";
        }
        Country country = countryRepository.getById(id);
        country.setName(countryDto.getName());
        country.setContinent(continentRepository.getById(countryDto.getContinentId()));
        countryRepository.save(country);
        return "Country edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        if (!countryRepository.findById(id).isPresent()) {
            return "Country not deleted";
        }
        countryRepository.deleteById(id);
        return "Country deleted";
    }
}
