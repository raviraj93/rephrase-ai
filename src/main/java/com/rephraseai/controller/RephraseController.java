package com.rephraseai.controller;

import com.rephraseai.dto.RephraseRequest;
import com.rephraseai.dto.RephraseResponse;
import com.rephraseai.service.RephraseService;
import com.rephraseai.service.impl.RephraseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class RephraseController {
    private final RephraseService rephraseService;

    public RephraseController(RephraseService rephraseService) {
        this.rephraseService = rephraseService;
    }
    private static final Logger log = LoggerFactory.getLogger(RephraseController.class);


    @PostMapping("/rephrase")
    public RephraseResponse rephrase(@RequestBody RephraseRequest request,
                                     @RequestParam(defaultValue = "default") String mode) {
        if (request.text() == null || request.text().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Text to rephrase must not be empty");
        }
        log.info("Calling rephrase service with mode: {}", mode);
        String rephrasedText = rephraseService.rephrase(request.text(), mode);
        return new RephraseResponse(rephrasedText);
    }

}
