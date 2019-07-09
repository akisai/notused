package com.bercut.sa.parentalctl;

import com.bercut.sa.parentalctl.atlas.AtlasProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by haimin-a on 09.07.2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class IPInterceptorTest {

    @Autowired
    private AtlasProvider atlasProvider;

    @Test
    public void preHandleTrue() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setRemoteAddr("1");
        Assert.assertTrue(new IPInterceptor(atlasProvider).preHandle(request, response, 1));
    }

    @Test
    public void preHandleFalse() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Assert.assertFalse(new IPInterceptor(atlasProvider).preHandle(request, response, 1));
    }
}