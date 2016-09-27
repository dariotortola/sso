package com.konecta.sso.config;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

public class MvcConfigurationTest {

    @Test
    public void test() {
        ViewControllerRegistry registry = Mockito.mock(ViewControllerRegistry.class);
        ViewControllerRegistration registration = Mockito.mock(ViewControllerRegistration.class);
        Mockito.when(registry.addViewController(Matchers.anyString())).thenReturn(registration);

        MvcConfiguration config = new MvcConfiguration();
        config.addViewControllers(registry);
        Mockito.verify(registry, Mockito.times(2)).addViewController(Matchers.anyString());
        Mockito.verify(registration, Mockito.times(2)).setViewName(Matchers.anyString());

        Assert.assertNotNull(config.bcrypt());
        Assert.assertNotNull(config.sha1());
        Assert.assertNotNull(config.sha1b64());
        Assert.assertNotNull(config.md5());
    }
}
