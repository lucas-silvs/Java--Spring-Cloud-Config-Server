package com.lucassilvs.springbootconfigserverclient.entrypoint.controllers;

import org.springframework.http.ResponseEntity;

public interface HelloConfigController {



    ResponseEntity<String> helloConfig();
}
