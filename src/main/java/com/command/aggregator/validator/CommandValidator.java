package com.command.aggregator.validator;

import com.command.aggregator.exception.InvalidStateNameException;
import com.command.aggregator.request.CommandRequest;
import com.command.aggregator.util.StateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CommandValidator {

    private final StateUtil stateUtil;

    public void validator(Map<String, List<CommandRequest>> request){
        Set<String> states = stateUtil.getStates();
        request.entrySet().stream().forEach(entry ->  {

            if(!states.contains(entry.getKey().toLowerCase())){
                throw new InvalidStateNameException("state name is invalid");
            }
        });
    }
}
