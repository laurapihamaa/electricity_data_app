package electricity.data.backend.v1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import electricity.data.backend.v1.entities.ElectricityData;

public interface ElectricityDataRepository extends JpaRepository<ElectricityData, Long>{
    
}
