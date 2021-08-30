package com.command.aggregator.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommandResponse {

    String mostFrequentCommand;
    Long startProcessTime;
    Long stopProcessTime;
}
