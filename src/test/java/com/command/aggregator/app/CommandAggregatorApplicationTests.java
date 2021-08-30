package com.command.aggregator.app;

import com.command.aggregator.exception.ApiError;
import com.command.aggregator.exception.InvalidStateNameException;
import com.command.aggregator.request.CommandRequest;

import static org.junit.Assert.*;

import com.command.aggregator.response.CommandResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * 1. No mocking used in these tests
 * 2. these tests are dependent on each other, being executed sequentially on purpose
 * 3. H2 database is used in the code to store all the commands by state
 * 4. we can see dynamic values being returned on top commands of the nation and each state
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandAggregatorApplicationTests {

    public CommandAggregatorApplicationTests(){}

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test1() {
        Map<String, List<CommandRequest>> request = new HashMap<>();

        request.put("alabama", new ArrayList<>());
        request.get("alabama").addAll(Arrays.asList(new CommandRequest("Fred Zhang", "CNN"),
                new CommandRequest("Fred Zhang", "NBC"),
                new CommandRequest("Fred Zhang", "CNN"),
                new CommandRequest("Fred Zhang", "CNN")));

        Map<String, Object> response = restTemplate.postForObject("http://localhost:" + port + "/commands", request, Map.class);

        System.out.println(response.toString());

        assertTrue(response.containsKey("alabama"));

        Object obj = response.get("alabama");
        ObjectMapper objectMapper = new ObjectMapper();

        CommandResponse commandResponse = objectMapper.convertValue(obj, CommandResponse.class);

        assertTrue(commandResponse.getMostFrequentCommand().equalsIgnoreCase("CNN"));
        assertEquals((List<String>)response.get("topCommandsNationally"), Arrays.asList("cnn", "nbc"));


    }

    @Test
    public void test2() {
        Map<String, List<CommandRequest>> request = new HashMap<>();

        request.put("alabama", new ArrayList<>());
        request.get("alabama").addAll(Arrays.asList(new CommandRequest("Fred Zhang", "CNN"),
                new CommandRequest("Fred Zhang", "NBC"),
                new CommandRequest("Fred Zhang", "NBC")));
        request.put("texas", new ArrayList<>());
        request.get("texas").addAll(Arrays.asList(new CommandRequest("Fred Zhang", "NBC"),
                new CommandRequest("Fred Zhang", "NBC"),
                new CommandRequest("Fred Zhang", "Game of Throwns")));

        Map<String, Object> response = restTemplate.postForObject("http://localhost:" + port + "/commands", request, Map.class);

        System.out.println(response.toString());

        assertTrue(response.containsKey("alabama"));
        assertTrue(response.containsKey("texas"));

        ObjectMapper objectMapper = new ObjectMapper();

        Object alabamaObject = response.get("alabama");
        CommandResponse alabamaCommandResponse = objectMapper.convertValue(alabamaObject, CommandResponse.class);

        //first test added CNN 3 times, it still leads
        assertTrue(alabamaCommandResponse.getMostFrequentCommand().equalsIgnoreCase("CNN"));

        Object texasObject = response.get("texas");
        CommandResponse texasCommandResponse = objectMapper.convertValue(texasObject, CommandResponse.class);

        assertTrue(texasCommandResponse.getMostFrequentCommand().equalsIgnoreCase("NBC"));

        assertEquals((List<String>)response.get("topCommandsNationally"), Arrays.asList("nbc", "cnn", "game of throwns"));

    }

    @Test
    public void test3() {
        Map<String, List<CommandRequest>> request = new HashMap<>();

        request.put("new hampshire", new ArrayList<>());
        request.get("new hampshire").addAll(Arrays.asList(new CommandRequest("Fred Zhang", "CNBC")));

        List<CompletableFuture<Void>> listOfFutures = new ArrayList<>();
        //execute 10 requests in parallel
        for(int i = 0; i< 10; i++){
            listOfFutures.add(CompletableFuture.runAsync(() -> restTemplate.postForObject("http://localhost:" + port + "/commands", request, Map.class)));
        }

        //wait till all are executed
        listOfFutures.stream().forEach(future -> future.join());

        Map<String, Object> response = restTemplate.postForObject("http://localhost:" + port + "/commands", request, Map.class);

        System.out.println(response.toString());

        assertTrue(response.containsKey("new hampshire"));

        ObjectMapper objectMapper = new ObjectMapper();

        Object nHObject = response.get("new hampshire");

        CommandResponse nHCommandResponse = objectMapper.convertValue(nHObject, CommandResponse.class);

        assertTrue(nHCommandResponse.getMostFrequentCommand().equalsIgnoreCase("CNBC"));

        //we should expect cnbc on top now
        assertEquals((List<String>)response.get("topCommandsNationally"), Arrays.asList("cnbc", "nbc", "cnn"));

    }

    @Test
    public void test4() {
        Map<String, List<CommandRequest>> request = new HashMap<>();

        request.put("invalid state", new ArrayList<>());
        request.get("invalid state").addAll(Arrays.asList(new CommandRequest("Fred Zhang", "CNN"),
                new CommandRequest("Fred Zhang", "NBC")));


        ApiError exception = restTemplate.postForObject("http://localhost:" + port + "/commands", request, ApiError.class);

        System.out.println(exception.toString());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

}
