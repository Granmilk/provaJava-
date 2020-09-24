package br.com.tokiomarine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.domain.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	List<Cliente> findAllByOrderByNomeAsc();

	@Query("SELECT c FROM Cliente c WHERE LOWER(c.email) = LOWER(:email)")
    Cliente findByEmail(String email);
}
