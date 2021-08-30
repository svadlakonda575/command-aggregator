package com.command.aggregator.controller;

import com.command.aggregator.request.CommandRequest;
import com.command.aggregator.service.CommandAggregatorService;
import com.command.aggregator.validator.CommandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class CommandAggregatorController {

    private final CommandValidator commandValidator;
    private final CommandAggregatorService commandAggregatorService;

    @PostMapping(value = "/commands",  consumes = "application/json")
    public Map<String, Object> commands(@RequestBody Map<String, List<CommandRequest>> request) {

        commandValidator.validator(request);

        return commandAggregatorService.processCommands(request);

    }


}
