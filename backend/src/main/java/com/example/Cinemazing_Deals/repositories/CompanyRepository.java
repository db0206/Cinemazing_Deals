package com.example.Cinemazing_Deals.repositories;

import com.example.Cinemazing_Deals.model.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Company findByEmailAndPassword(String email, String password);

    @Query("SELECT c FROM Company c WHERE c.companyId = :companyId")
    Company getOneCompany(Long companyId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Company c WHERE c.email = :email AND c.password = :password")
    boolean existsByEmailAndPassword(String email, String password);
}
