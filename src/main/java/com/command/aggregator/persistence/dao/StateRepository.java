package com.command.aggregator.persistence.dao;

import com.command.aggregator.persistence.data.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<State, Integer> {

}
