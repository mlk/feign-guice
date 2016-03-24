package com.github.mlk.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;
import feign.Feign;

import java.util.function.Function;

class FeignSuppler implements Function<Class<?>, Object> {
    private final Feign.Builder builder;
    private final String url;
    private Injector injector;

    FeignSuppler(Feign.Builder builder, String url) {
        this.builder = builder;
        this.url = url;
    }

    @Inject
    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    @Override
    public Object apply(Class<?> aClass) {
        Feign.Builder builder = this.builder;
        if(builder == null) {
            builder = injector.getInstance(Feign.Builder.class);
        }
        return builder.target(aClass, url);
    }
}
