package uz.bob.address_university_app.repository.mapRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.bob.address_university_app.entity.map.Address;
import uz.bob.address_university_app.entity.map.Continent;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {

}
