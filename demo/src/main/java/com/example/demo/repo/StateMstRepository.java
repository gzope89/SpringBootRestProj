package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StateMst;

@Repository
public interface StateMstRepository extends JpaRepository<StateMst, Long>{

	StateMst findByStateCode(String StateCode);
}
