package com.fabianbah.auth_server.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/loans")
public class LoansController {

    @GetMapping("")
    public Map<String, String> loans() {
        return Collections.singletonMap("msj", "loans");
    }
}
