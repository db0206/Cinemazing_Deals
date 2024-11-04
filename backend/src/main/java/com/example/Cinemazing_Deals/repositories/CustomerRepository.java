package com.example.Cinemazing_Deals.repositories;

import com.example.Cinemazing_Deals.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.customerId = :customerId")
    Customer getOneCustomer(Long customerId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Customer c WHERE c.email = :email AND c.customerId <> :customerId")
    boolean existsByEmailWithDifferentId(String email, Long customerId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Customer c WHERE c.email = :email AND c.password = :password")
    boolean existsByEmailAndPassword(String email, String password);

    Customer findByEmailAndPassword(String email, String password);
}
