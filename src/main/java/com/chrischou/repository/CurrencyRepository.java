package com.chrischou.repository;

import com.chrischou.repository.model.CurrencyPO;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepositoryImplementation<CurrencyPO, Long> {

    Optional<CurrencyPO> findByCode(String code);
}
