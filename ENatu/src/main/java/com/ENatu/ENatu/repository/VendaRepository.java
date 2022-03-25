package com.ENatu.ENatu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ENatu.ENatu.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

}
