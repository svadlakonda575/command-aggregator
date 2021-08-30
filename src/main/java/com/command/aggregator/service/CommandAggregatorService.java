package com.command.aggregator.service;

import com.command.aggregator.request.CommandRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CommandAggregatorService {

     Map<String, Object> processCommands(Map<String, List<CommandRequest>> request);
}
