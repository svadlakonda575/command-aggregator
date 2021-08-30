package com.command.aggregator.persistence.dao;

import com.command.aggregator.persistence.data.Command;
import com.command.aggregator.persistence.data.CommandId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository extends CrudRepository<Command, CommandId> {

    @Query("from Command where commandId.stateId = ?1 order by frequency desc")
    public List<Command> getTopCommandByState(Integer stateId, Pageable pageable);

    @Query("select command.commandId.command, sum(command.frequency) as total_frequency from Command command group by command.commandId.command order by total_frequency desc")
    public List<Object[]> getDistinctTopByFrequency(Pageable pageable);
}
