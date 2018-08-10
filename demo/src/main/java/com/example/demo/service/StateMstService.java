package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.StateMst;

public interface StateMstService {

	
	public StateMst saveStateMst(StateMst stateMst);

	public StateMst updateStateMst(StateMst stateMst);

	public Optional<StateMst> getOneStateMstDetails(Long stateId);

	public List<StateMst> findAllStateMsts();

	public void removeStateMst(Long stateId);

	public StateMst getStateMstBasedOnStateCode(StateMst stateMst);
}
