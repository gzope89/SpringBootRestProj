package com.example.demo;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.entity.StateMst;
import com.example.demo.service.StateMstService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StateMstController.class })
@WebAppConfiguration
@EnableWebMvc
public class DemoApplicationTests {

	@Autowired
	public StateMstService stateMstService;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@MockBean
	private StateMstService service;

	@Test
	public void givenStatesReturnJsonArray()
			throws Exception {
		StateMst mh = new StateMst();
		mh.setStateCode("mh");

		when(service.findAllStateMsts()).thenReturn(Arrays.asList(mh));

		mockMvc.perform(get("/api/stateMst/findAll")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].stateCode", is(mh.getStateCode())));

		verify(service, times(1)).findAllStateMsts();
		verifyNoMoreInteractions(service);
	}


	@Test
	public void findById_EntryFound_ShouldReturnHttpStatusCode200() throws Exception {

		StateMst mh = new StateMst();
		mh.setStateCode("mh");
		mh.setStateId(1L);
		mh.setStateName("Maharashtra");
		Optional<StateMst> stateMstOpt = Optional.of(mh);

		when(service.getOneStateMstDetails(1L)).thenReturn((stateMstOpt));

		mockMvc.perform(get("/api/stateMst/state/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());


		verify(service, times(1)).getOneStateMstDetails(1L);
		verifyNoMoreInteractions(service);
	}

	@Test
	public void findById_EntryNotFound_ShouldReturnHttpStatusBadReq() throws Exception {

		when(service.getOneStateMstDetails(1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/stateMst/state/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());

		verify(service, times(1)).getOneStateMstDetails(1L);
		verifyNoMoreInteractions(service);
	}
	
	@Test
    public void test_create_state_success() throws Exception {
		StateMst mh = new StateMst();
		mh.setStateCode("mh");
		mh.setStateId(1L);
		mh.setStateName("Maharashtra");

        when(service.getStateMstBasedOnStateCode(mh)).thenReturn(null);
        when(service.saveStateMst(any(StateMst.class))).thenReturn(mh);

        mockMvc.perform(
                post("/api/stateMst/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mh)))
                .andExpect(status().isCreated());

        verify(service, times(1)).getStateMstBasedOnStateCode(mh);
        verify(service, times(1)).saveStateMst(mh);
        verifyNoMoreInteractions(service);
    }
	
	@Test
    public void test_create_state_fail_409_not_found() throws Exception {
		StateMst mh = new StateMst();
		mh.setStateCode("mh");
		mh.setStateId(1L);
		mh.setStateName("Maharashtra");

		 when(service.getStateMstBasedOnStateCode(mh)).thenReturn(mh);

        mockMvc.perform(
                post("/api/stateMst/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mh)))
                .andExpect(status().isConflict());

        verify(service, times(1)).getStateMstBasedOnStateCode(mh);
        verifyNoMoreInteractions(service);
    }
	
	@Test
    public void test_delete_state_success() throws Exception {

		StateMst mh = new StateMst();
		mh.setStateCode("mh");
		mh.setStateId(1L);
		mh.setStateName("Maharashtra");
		Optional<StateMst> stateMstOpt = Optional.of(mh);
		
        when(service.getOneStateMstDetails(mh.getStateId())).thenReturn(stateMstOpt);
        doNothing().when(service).removeStateMst(mh.getStateId());

        mockMvc.perform(
                delete("/api/stateMst//state/{id}", mh.getStateId()))
                .andExpect(status().isOk());

        verify(service, times(1)).getOneStateMstDetails(mh.getStateId());
        verify(service, times(1)).removeStateMst(mh.getStateId());
        verifyNoMoreInteractions(service);
    }
	
	@Test
    public void test_delete_state_fail_409_not_found() throws Exception {
		StateMst mh = new StateMst();
		mh.setStateCode("mh");
		mh.setStateId(1L);
		mh.setStateName("Maharashtra");

		when(service.getStateMstBasedOnStateCode(mh)).thenReturn(null);

        mockMvc.perform(
        		delete("/api/stateMst/state/{id}", mh.getStateId()))
                .andExpect(status().isNotFound());

    }	
	
	
	@Test
    public void test_update_user_success() throws Exception {
		StateMst mh = new StateMst();
		mh.setStateCode("mh");
		mh.setStateId(1L);
		mh.setStateName("Maharashtra");


		 when(service.updateStateMst(any(StateMst.class))).thenReturn(mh);


        mockMvc.perform(
                put("/api/stateMst//update", mh.getStateId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mh)))
                .andExpect(status().isOk());

        verify(service, times(1)).updateStateMst(mh);
        verifyNoMoreInteractions(service);
    }
	
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
