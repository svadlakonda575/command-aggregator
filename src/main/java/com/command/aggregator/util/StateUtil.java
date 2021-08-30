package com.command.aggregator.util;

import com.command.aggregator.persistence.dao.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class StateUtil {

    private HashMap<String, Integer> stateMap = new HashMap<>();

    private final StateRepository stateRepository;

    @PostConstruct
    public void load(){
        stateRepository.findAll().forEach(state -> {
            stateMap.put(state.getState(), state.getId());
        });
    }

    public Integer getId(String state){
        return stateMap.get(state);
    }

    public Set<String> getStates(){
        return stateMap.keySet();
    }

}
