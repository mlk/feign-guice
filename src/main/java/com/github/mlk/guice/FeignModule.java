package com.github.mlk.guice;

import feign.Feign;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FeignModule extends ExternalCreationModule {

    private final List<String> packagesToScan;
    private final Function<Class<?>, ?> function;

    FeignModule(Function<Class<?>, ?> function, List<String> packagesToScan) {
        super(function);
        this.packagesToScan = packagesToScan;
        this.function = function;
    }

    @Override
    protected void configure() {
        requestInjection(function);
        for(String scanPackage : packagesToScan) {
            scan(scanPackage);
        }
    }

    public static FeignModuleBuilder builder() {
        return new FeignModuleBuilder();
    }

    public static class FeignModuleBuilder {
        private Feign.Builder builder;

        FeignModuleBuilderWithUrl withUrl(String url) {
            return new FeignModuleBuilderWithUrl(url, builder);
        }

        public FeignModuleBuilder withBuilder(Feign.Builder builder) {
            this.builder = builder;
            return this;
        }
    }

    public static class FeignModuleBuilderWithUrl {
        private final String url;
        private final Feign.Builder builder;
        private final List<String> packages = new ArrayList<>();

        private FeignModuleBuilderWithUrl(String url, Feign.Builder builder) {
            this.url = url;
            this.builder = builder;
        }

        public FeignModuleBuilderWithUrl scan(String packageToScan) {
            packages.add(packageToScan);
            return this;
        }

        public FeignModuleBuilderWithUrl scan(String... packagesToScan) {
            for(String packageToScan : packagesToScan) {
                packages.add(packageToScan);
            }
            return this;
        }

        public FeignModule build() {
            return new FeignModule(new FeignSuppler(builder, url), packages);
        }
    }
}
