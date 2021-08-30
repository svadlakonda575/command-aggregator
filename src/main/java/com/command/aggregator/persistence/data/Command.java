package com.command.aggregator.persistence.data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;




@Entity
public class Command {

    @EmbeddedId
    private CommandId commandId;

    private Integer frequency;

    protected Command() {

    }

    public Command(CommandId commandId,  Integer frequency) {
        this.commandId = commandId;
        this.frequency = frequency;
    }

    public CommandId getCommandId() {
        return commandId;
    }

    public void setCommandId(CommandId commandId) {
        this.commandId = commandId;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

}
