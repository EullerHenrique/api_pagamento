package com.api.pagamento.repository;

import com.api.pagamento.domain.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository

//É uma extensão específica de JPA de Repository. Ele contém a API completa de CrudRepository e
//PagingAndSortingRepository. Portanto, ele contém API para operações CRUD básicas e também API para paginação
//e classificação.

public interface TransacaoRepository extends JpaRepository<Transacao, Long> { }
