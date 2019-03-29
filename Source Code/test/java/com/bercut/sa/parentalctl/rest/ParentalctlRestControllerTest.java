package com.bercut.sa.parentalctl.rest;

import com.bercut.sa.parentalctl.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by haimin-a on 29.03.2019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ParentalctlRestControllerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;
    private String uriP = "/v1/parent";
    private String uriC = "/v1/children";

    @Autowired
    ParentalctlRestController parentalctlRestController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parentalctlRestController)
                .build();
    }

    @Test
    public void addParent() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uriP)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
    }

    @Test
    public void addChildren() {
    }

    @Test
    public void delParent() {
    }

    @Test
    public void delChild() {
    }

    @Test
    public void setChild() {
    }
}