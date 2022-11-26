package one.digitalinnovation.labpadroesprojetospringpratica.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import one.digitalinnovation.labpadroesprojetospringpratica.model.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, String>{

}
