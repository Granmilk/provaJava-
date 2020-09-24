package br.com.tokiomarine.repository;

import br.com.tokiomarine.domain.Funcionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.email) = LOWER(:email)")
    Funcionario findFuncionarioByEmail(String email);
}
