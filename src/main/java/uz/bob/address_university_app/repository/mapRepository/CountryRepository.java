package uz.bob.address_university_app.repository.mapRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.address_university_app.entity.map.Continent;
import uz.bob.address_university_app.entity.map.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country,Integer> {

}
