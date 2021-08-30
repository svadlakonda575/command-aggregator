package com.command.aggregator.service.impl;

import com.command.aggregator.persistence.dao.CommandRepository;
import com.command.aggregator.persistence.data.Command;
import com.command.aggregator.persistence.data.CommandId;
import com.command.aggregator.request.CommandRequest;
import com.command.aggregator.response.CommandResponse;
import com.command.aggregator.service.CommandAggregatorService;
import com.command.aggregator.util.StateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class CommandAggregatorServiceImpl implements CommandAggregatorService {
    public static final String TOP_COMMANDS = "topCommandsNationally";
    public static final ReentrantLock reentrantLock = new ReentrantLock();
    private final CommandRepository commandRepository;
    private final StateUtil stateUtil;


    @Override
    @Transactional
    public Map<String, Object> processCommands(Map<String, List<CommandRequest>> request) {

        Map<String, Object> response = new HashMap<>();

        reentrantLock.lock();
        try {

            request.forEach((key, value) -> {
                CommandResponse commandResponse = new CommandResponse();
                commandResponse.setStartProcessTime(System.currentTimeMillis());
                String state = key.toLowerCase();
                Integer id = stateUtil.getId(state);
                value.forEach(commandRequest -> {
                    CommandId commandId = new CommandId(commandRequest.getCommand().toLowerCase(), id);
                    Optional<Command> commandOptional = commandRepository.findById(commandId);
                    if (commandOptional.isPresent()) {
                        Command command = commandOptional.get();
                        command.setFrequency(command.getFrequency() + 1);
                        commandRepository.save(command);
                    } else {
                        Command command = new Command(commandId, 1);
                        commandRepository.save(command);
                    }
                });
                List<Command> command = commandRepository.getTopCommandByState(id, PageRequest.of(0, 1));
                commandResponse.setMostFrequentCommand(command.get(0).getCommandId().getCommand());
                response.put(state, commandResponse);
                List<Object[]> topCommandsObjects = commandRepository.getDistinctTopByFrequency(PageRequest.of(0, 3));

                List<String> topCommands = topCommandsObjects.stream().map(objArr -> objArr[0].toString()).collect(Collectors.toList());
                response.put(TOP_COMMANDS, (Object) topCommands);
                commandResponse.setStopProcessTime(System.currentTimeMillis());
            });
        }finally {
            reentrantLock.unlock();
        }

        return response;
    }
}
