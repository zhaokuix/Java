package com.example.dao;

import com.example.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface UserDao extends GenericRepository<User>{

    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<User> findAll();

    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<User> findAll(Predicate var1, Pageable var2);

}
