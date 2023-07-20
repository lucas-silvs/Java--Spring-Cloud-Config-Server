package com.lucassilvs.springbootconfigserverclient.entrypoint.controllers.impl;


import com.lucassilvs.springbootconfigserverclient.entrypoint.controllers.HelloConfigController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
@RequestMapping(("/hello"))
public class HelloConfigControllerImpl  implements HelloConfigController {

    @Value("${configuracao.semAmbiente}")
    private String message;
    @GetMapping
    public ResponseEntity<String> helloConfig() {
        return ResponseEntity.ok(message);
    }
}
