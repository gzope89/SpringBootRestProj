/*package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.StateMst;
import com.example.demo.service.StateMstService;

@RunWith(SpringRunner.class)
@WebMvcTest(StateMstController.class)
@Transactional
public class RestJunitTest {

	 @Autowired
	    private MockMvc mvc;
	 
	    @MockBean
	    private StateMstService service;
	    
	    @Test
	    public void givenStatesReturnJsonArray()
	      throws Exception {
	         
	        StateMst mh = new StateMst();
	        mh.setStateCode("mh");
	        
	        List<StateMst> allStates = Arrays.asList(mh);
	     
	        given(service.findAllStateMsts()).willReturn(allStates);
	     
	        mvc.perform(get("/api/employees")
	          .contentType(MediaType.APPLICATION_JSON))
	          .andExpect(status().isOk())
	          .andExpect(jsonPath("$", hasSize(1)))
	          .andExpect(jsonPath("$[0].name", is(alex.getName())));
	    }
	    
	    @Test
	public void testfindAllStateMstsNotEmpty(){
		Assert.assertNotNull("Should not be empty", stateMstService.findAllStateMsts());
	}
	
	@Test
	public void testfindAllStateMstsSizeNotEqualsToZero(){
		Assert.assertNotEquals(0, stateMstService.findAllStateMsts().size());
	}
	
	@Test
	public void testfindByIdStateMstNotEmpty(){
		Long stateId = 1l;
		Assert.assertNotNull("Should not be NULL", stateMstService.getOneStateMstDetails(stateId));
	}
	
	@Test
	public void testfindByIdStateMst(){
		Long stateId = 1l;
		Assert.assertEquals("MH1", stateMstService.getOneStateMstDetails(stateId).getStateCode());
	}
	
	@Test
	public void testfindByIdStateMstForMaxValue(){
		Long stateId = Long.MAX_VALUE;
		Assert.assertNull(stateMstService.getOneStateMstDetails(stateId));
	}
	
	 @Test
	    public void testSaveStateMst() {

	        StateMst stateMst = new StateMst();
	        stateMst.setStateCode("test");

	        StateMst createdEntity = stateMstService.saveStateMst(stateMst);

	        Assert.assertNotNull("failure - expected not null", createdEntity);
	        Assert.assertNotNull("failure - expected id attribute not null",
	                createdEntity.getStateId());
	        Assert.assertEquals("failure - expected text attribute match", "test",
	                createdEntity.getStateCode());
	    }
	 
	  @Test
	    public void testUpdate() {

	        Long id = new Long(1);

	        StateMst entity = stateMstService.getOneStateMstDetails(id);

	        Assert.assertNotNull("failure - expected not null", entity);

	        String updatedText = entity.getStateName() + " test";
	        entity.setStateName(updatedText);
	        StateMst updatedEntity = stateMstService.updateStateMst(entity);

	        Assert.assertNotNull("failure - expected not null", updatedEntity);
	        Assert.assertEquals("failure - expected id attribute match", id,
	                updatedEntity.getStateId());
	        Assert.assertEquals("failure - expected text attribute match",
	                updatedText, updatedEntity.getStateName());

	    }
	  
	  	@Test
	    public void testRemoveStateMst() {

	        Long id = new Long(1);

	        StateMst entity = stateMstService.getOneStateMstDetails(id);

	        Assert.assertNotNull("failure - expected not null", entity);

	        stateMstService.removeStateMst(id);

	        StateMst deletedEntity = stateMstService.getOneStateMstDetails(id);

	        Assert.assertNull("failure - expected null", deletedEntity);

	    }
}
*/