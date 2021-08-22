package sh.aios.export.bananes.infra.repository;

import org.springframework.data.repository.CrudRepository;

import sh.aios.export.bananes.infra.entity.Customer;

public interface CustomerJpaRepository extends CrudRepository<Customer, Integer> {

}