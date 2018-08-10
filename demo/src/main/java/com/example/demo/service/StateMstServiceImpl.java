package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.StateMst;
import com.example.demo.repo.StateMstRepository;

@Service
public class StateMstServiceImpl  implements StateMstService{
	@Autowired
	public  StateMstRepository stateMstRepository;
	
	public StateMst saveStateMst(StateMst stateMst) {
		return stateMstRepository.save(stateMst);
	}

	public StateMst updateStateMst(StateMst stateMst) {
		return  stateMstRepository.save(stateMst);
	}

	public Optional<StateMst> getOneStateMstDetails(Long stateId) {
		return stateMstRepository.findById(stateId);
		
	}

	public List<StateMst> findAllStateMsts() {
		return stateMstRepository.findAll();
	}

	public void removeStateMst(Long stateId) {
		 stateMstRepository.deleteById(stateId);
	}

	public StateMst getStateMstBasedOnStateCode(StateMst stateMst) {
		return stateMstRepository.findByStateCode(stateMst.getStateCode());
	}
}
