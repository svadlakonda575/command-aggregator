package com.command.aggregator.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CommandRequest {

    String speaker;
    String command;
}
