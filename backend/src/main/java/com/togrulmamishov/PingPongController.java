package com.togrulmamishov;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

    @GetMapping("ping")
    public String getPingPong() {
        return "Pong";
    }
}
