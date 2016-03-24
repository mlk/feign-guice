# feign-guice
A module to marry together Feign bound resources to the Guice dependency injection

```
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
```

It is on Maven, so use it with:
```
<dependency>
  <groupId>com.github.mlk</groupId>
  <artifactId>feign-guice</artifactId>
  <version>1.0.0</version>
</dependency>
```