package com.github.mlk.guice;

import com.github.mlk.guice.api.GitHub;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dto.Contributor;
import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;

public class Example {
    public static void main(String... arg) {
        Injector injector = Guice.createInjector(FeignModule.builder()
                // Setting the builder here is optional, if none are set it will ask Guice for one.
                .withBuilder(Feign.builder().decoder(new GsonDecoder()))
                // The base URL is required.
                .withUrl("https://api.github.com")
                // The packages to scan.
                .scan("com.github.mlk.guice.api").build());

        GitHub github = injector.getInstance(GitHub.class);
        List<Contributor> contributors = github.contributors("netflix", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
