package ar.edu.davinci.dvds20221cg2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ar.edu.davinci.dvds20221cg2.domain.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long>{

}
