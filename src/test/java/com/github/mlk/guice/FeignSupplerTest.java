package com.github.mlk.guice;

import com.github.mlk.guice.thingies.A;
import com.google.inject.Injector;
import feign.Feign;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FeignSupplerTest {
    @Test
    public void createsUsingTheGivenBuilder() {
        Feign.Builder builder = mock(Feign.Builder.class);

        FeignSuppler subject = new FeignSuppler(builder, "http://example.com");
        subject.apply(A.class);

        verify(builder).target(A.class, "http://example.com");
    }

    @Test
    public void whenBuilderIsNullRequestFromInjector() {
        Injector injector = mock(Injector.class);
        Feign.Builder builder = mock(Feign.Builder.class);
        when(injector.getInstance(Feign.Builder.class)).thenReturn(builder);

        FeignSuppler subject = new FeignSuppler(null, "http://example.com");
        subject.setInjector(injector);

        subject.apply(A.class);

        verify(builder).target(A.class, "http://example.com");
    }
}