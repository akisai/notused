package com.bercut.sa.parentalctl.rest;

import com.bercut.sa.parentalctl.TestConfig;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by haimin-a on 29.03.2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class ParentalctlRestControllerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;
    private final static String URI_P = "/v1/parent";
    private final static String URI_C = "/v1/child";
    private final static String TRUE = "79995257009";
    public final static String CONFLICT = "79995257119";
    public final static String DUPLICATE = "79995257229";

    @Autowired
    ParentalctlRestController parentalctlRestController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parentalctlRestController)
                .build();
    }

    @Test
    public void addParentConflict() throws Exception {
        Msisdn msisdn = new Msisdn();
        msisdn.setMsisdn(CONFLICT);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(msisdn);
        mockMvc.perform(MockMvcRequestBuilders.post(URI_P)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable()).andReturn();
    }

    @Test
    public void addParentDuplicate() throws Exception {
        Msisdn msisdn = new Msisdn();
        msisdn.setMsisdn(DUPLICATE);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(msisdn);
        mockMvc.perform(MockMvcRequestBuilders.post(URI_P)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();
    }

    @Test
    public void addParentTrue() throws Exception {
        Msisdn msisdn = new Msisdn();
        msisdn.setMsisdn(TRUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(msisdn);
        mockMvc.perform(MockMvcRequestBuilders.post(URI_P)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    }

    @Test
    public void addChildConflict() throws Exception {
        Children children = new Children();
        children.setMsisdn(CONFLICT);
        children.setParent(TRUE);
        children.setFwdAoc(true);
        children.setFwdPay(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(children);
        mockMvc.perform(MockMvcRequestBuilders.post(URI_C)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable()).andReturn();
    }

    @Test
    public void addChildDuplicate() throws Exception {
        Children children = new Children();
        children.setMsisdn(DUPLICATE);
        children.setParent(TRUE);
        children.setFwdAoc(true);
        children.setFwdPay(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(children);
        mockMvc.perform(MockMvcRequestBuilders.post(URI_C)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();
    }

    @Test
    public void addChildEqualsMsisdn() throws Exception {
        Children children = new Children();
        children.setMsisdn(TRUE);
        children.setParent(TRUE);
        children.setFwdAoc(true);
        children.setFwdPay(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(children);
        mockMvc.perform(MockMvcRequestBuilders.post(URI_C)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void addChildTrue() throws Exception {
        Children children = new Children();
        children.setMsisdn(TRUE);
        children.setParent(DUPLICATE);
        children.setFwdAoc(true);
        children.setFwdPay(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(children);
        mockMvc.perform(MockMvcRequestBuilders.post(URI_C)
                .contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    }

    @Test
    public void delParentConflict() throws Exception {
        String uri = URI_P + "/" + CONFLICT;
        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable()).andReturn();
    }

    @Test
    public void delParentNotFound() throws Exception {
        String uri = URI_P + "/" + DUPLICATE;
        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    @Test
    public void delParentTrue() throws Exception {
        String uri = URI_P + "/" + TRUE;
        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
    }

    @Test
    public void delChildNotFound() throws Exception {
        String uri = URI_C + "/" + DUPLICATE;
        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    @Test
    public void delChildTrue() throws Exception {
        String uri = URI_C + "/" + TRUE;
        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
    }

    @Test
    public void setChildNotFound() throws Exception {
        String uri = URI_C + "/" + DUPLICATE;
        Flags flags = new Flags();
        flags.setFwdAoc(true);
        flags.setFwdPay(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(flags);
        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
    }

    @Test
    public void setChildTrue() throws Exception {
        String uri = URI_C + "/" + TRUE;
        Flags flags = new Flags();
        flags.setFwdAoc(true);
        flags.setFwdPay(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(flags);
        mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON_UTF8).content(input))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void getMsisdnNotFound() throws Exception {
        String uri = "/v1/msisdn/" + TRUE;
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"result\":\"err_notFound\"}"));
    }

    @Test
    public void getMsisdnChildren() throws Exception {
        String uri = "/v1/msisdn/" + CONFLICT;
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"result\":\"ok_children\"}"));
    }

    @Test
    public void getMsisdnParent() throws Exception {
        String uri = "/v1/msisdn/" + DUPLICATE;
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"result\":\"ok_parent\"}"));
    }
}