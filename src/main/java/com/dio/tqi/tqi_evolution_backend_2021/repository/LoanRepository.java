package com.dio.tqi.tqi_evolution_backend_2021.repository;

import com.dio.tqi.tqi_evolution_backend_2021.model.LoanModel;
import com.dio.tqi.tqi_evolution_backend_2021.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<LoanModel,String> {

    Page<LoanModel> findByUser(UserModel userModel, Pageable pageable);
}
