package com.dio.tqi.tqi_evolution_backend_2021.repository;

import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<LoanModel,String> {

}
