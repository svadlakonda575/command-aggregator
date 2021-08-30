package com.command.aggregator.persistence.data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CommandId implements Serializable {
    private String command;
    private Integer stateId;

    protected CommandId () { }

    public CommandId(String command, Integer stateId){
        this.command = command;
        this.stateId = stateId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
}
