package br.edu.atitus.greetingservice.controllers;

import br.edu.atitus.greetingservice.configs.GreetingConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.edu.atitus.greetingservice.dtos.GreetingRequest;

@RestController
@RequestMapping("/greeting")
public class GreetingControler {
//    @Value("${greeting-service.greeting}")
//    private String greeting;
//
//    @Value("${greeting-service.default-name}")
//    private String defaultName;

    private final GreetingConfig config;
    //Injeção de dependência
    public GreetingControler(GreetingConfig config) {
        this.config = config;
    }

    @GetMapping({"", "/"})
    public String getGreeting(
            @RequestParam(required = false)String name) {
        if (name == null || name.isEmpty()) {
            name = config.getDefaultName();
        }
        String greetingReturn = String.format("%s, %s!!!", config.getGreeting(), name);
        return greetingReturn;
    }

    @GetMapping("/{name}")
    public String getGreetingPath(@PathVariable String name) {
    if (name == null || name.isEmpty()) {
        name = config.getDefaultName();
    }
    String greetingReturn = String.format("%s, %s!!!", config.getGreeting(), name);
    return greetingReturn;
    }

    @PostMapping
    public String createGreeting(@RequestBody GreetingRequest request) {
    String name = request.getName();

    if (name == null || name.isEmpty()) {
        name = config.getDefaultName();
    }

    String greetingReturn = String.format("%s, %s!!!", config.getGreeting(), name);
    return greetingReturn;
    }
}