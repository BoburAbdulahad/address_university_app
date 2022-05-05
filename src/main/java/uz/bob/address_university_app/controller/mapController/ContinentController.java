package uz.bob.address_university_app.controller.mapController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.bob.address_university_app.entity.map.Continent;
import uz.bob.address_university_app.repository.mapRepository.ContinentRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/continent")
public class ContinentController {

    @Autowired
    ContinentRepository continentRepository;
    @GetMapping
    public List<Continent> get(){
        return continentRepository.findAll();
    }

    @PostMapping
    public String add(@RequestBody Continent continent){
        continentRepository.save(continent);
        return "Continent saved";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable Integer id,@RequestBody Continent continent){
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (!optionalContinent.isPresent()) {
            return "Continent not found";
        }
        Continent continent1 = optionalContinent.get();
        continent1.setName(continent.getName());
        continentRepository.save(continent);
        return "Continent edited";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        Optional<Continent> optionalContinent = continentRepository.findById(id);
        if (!optionalContinent.isPresent()) {
            return "Continent not found";
        }
        continentRepository.deleteById(id);
        return "Continent deleted";
    }

}
