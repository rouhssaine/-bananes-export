package sh.aios.export.bananes.infra.repository;

import org.springframework.data.repository.CrudRepository;

import sh.aios.export.bananes.infra.entity.BananaOrder;

public interface OrderJpaRepository extends CrudRepository<BananaOrder, Integer> {

}