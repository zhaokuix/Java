package com.example.dao;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T> extends JpaRepository<T, Long>, QuerydslPredicateExecutor<T> {
    List<T> findAll(Predicate predicate);

    List<T> findAll(Predicate predicate, Sort sort);

    List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders);

    List<T> findAll(OrderSpecifier<?>... orders);

    @Transactional
    @Modifying
    default void updateEnabledById(Boolean enabled, Long id) {
    }
}