package com.example.demo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.StateMst;
import com.example.demo.service.StateMstService;

@RestController
@RequestMapping(path="/api/stateMst")
public class StateMstController {
	
	@Autowired
	public StateMstService stateMstService;
	
	@GetMapping("/")
	public String demo(){
		return "Hello there!!!";
	}
	
	public static String emptyString = "";
	public static int zeroVal = 0;
	
	/**
	 * 
	 * This method checks whether incoming parameter are unique and State code is mandatory for
	 * creating new state.
	 * 
	 * @param stateMst
	 * @return StateMst as response entity sent over HTTP in Json format
	 */
	@PostMapping(
			path = "/save",
			  consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE
	            )
	public ResponseEntity<StateMst> save(@RequestBody StateMst stateMst){
		
		if(stateMst.getStateCode() != null && stateMst.getStateCode() != emptyString){
			if(stateMstService.getStateMstBasedOnStateCode(stateMst) == null){
				return new ResponseEntity<StateMst>(stateMstService.saveStateMst(stateMst), HttpStatus.CREATED);
			}else{
				return new ResponseEntity<StateMst>(HttpStatus.CONFLICT);
			}
		}else{
			return new ResponseEntity<StateMst>(new StateMst(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * 
	 * This method checks whether incoming request have valid state code & state id.
	 *  If yes then update state record else send bad request error.
	 *  
	 * @param stateMst
	 * @return  StateMst as response entity sent over HTTP in Json format
	 */
	@RequestMapping(
			path = "/update",
			method = RequestMethod.PUT,
			 consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StateMst> update(
			@RequestBody StateMst stateMst){
		if((stateMst.getStateCode() != null && stateMst.getStateCode() != emptyString)
				|| (stateMst.getStateId() != null && stateMst.getStateId() != zeroVal)){
			return new ResponseEntity<StateMst>(stateMstService.updateStateMst(stateMst), HttpStatus.OK);
		}else{
			return new ResponseEntity<StateMst>( HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * This method accepts state id as URL path variable. Method checks for positive value for positive value records are fetched from DB,
	 * else bad request error thrown to user
	 * 
	 * @param stateId
	 * @return StateMst as response entity sent over HTTP in Json format
	 */
	@GetMapping(path = "/state/{id}",
			 consumes = MediaType.APPLICATION_JSON_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StateMst>  findByIdStateMst(@PathVariable("id") Long stateId){
		if(stateId!= null && stateId != zeroVal){
			
			Optional<StateMst> stateMst = stateMstService.getOneStateMstDetails(stateId);
			
			if(stateMst.isPresent())
				return new ResponseEntity<StateMst>(stateMst.get(), HttpStatus.OK);
			else
				return new ResponseEntity<StateMst>(HttpStatus.NO_CONTENT);
		}else{
			return new ResponseEntity<StateMst>( HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 *  
	 *  This method returns all state records from database.
	 *  
	 * @return Collection of StateMst as response entity sent over HTTP in Json format
	 */
	@GetMapping(path="/findAll",
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<StateMst>> findAllStateMsts(){
		List<StateMst> stateMsts = stateMstService.findAllStateMsts();
		return new ResponseEntity<Collection<StateMst>>(stateMsts, HttpStatus.OK);
	}
	
	/**
	 * 
	 * This method checks for URL path variable and records present for that state id, if present the deletes record from system
	 * 
	 * @param stateId
	 * @return 
	 */
	@RequestMapping(path = "/state/{id}",
			method = RequestMethod.DELETE)
	public ResponseEntity<StateMst> removeStateMst(@PathVariable("id") Long stateId) {
		if(stateId != null && stateId != zeroVal){
			Optional<StateMst> stateMst=stateMstService.getOneStateMstDetails(stateId);
			
			if(stateMst.isPresent()){
				stateMstService.removeStateMst(stateId);
				return new ResponseEntity<StateMst>(HttpStatus.OK);
			}else{
				return new ResponseEntity<StateMst>(HttpStatus.NOT_FOUND);
			}
		}else{
			return new ResponseEntity<StateMst>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
