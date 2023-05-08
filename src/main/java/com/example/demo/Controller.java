package com.example.demo;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    // declare the logger for this class
    final Logger logger = (Logger) LoggerFactory.getLogger(Controller.class);
    ObjectMapper mapper = new ObjectMapper();

    // This sample shows an output of List of String
    // Sample GET call http://localhost:/list
    @GetMapping(path = "/list") // GET controller for /list
    @ResponseBody
    public Iterable<String> getListSample() throws Exception { // return type is Iterable/List of String
        List<String> strings = new ArrayList<>();

        strings.add("Hello");
        strings.add("World");
        strings.add("Howdy");
        logger.info("successfully called /list"); // sample logging
        return strings;
    }

    // This sample echoes back the value of param "message" and requires the "key"
    // with value "howdy" as sample input processing
    // also contains sample if-then-else and Map
    // sample GET call
    // http://localhost:8080/parameter?key=howdy&message=just%20a%20test
    // the GET or POST controller for /parameter
    @RequestMapping(path = "/parameter") // @RequestMapping annotation can accept POST, GET, and other types
    @ResponseBody // ResponseBody annotation allows you to output json easily
    public HashMap<String, String> getParamSample( // Iterable<?> means you can output any iterable class
            // this is a sample of an optional parameter, you can assign a default value in
            // case it doesn't exist
            @RequestParam(value = "message", required = false, defaultValue = "none") String message,
            // this is a sample of a required paramter, your payload must have the key
            // "howdy"
            @RequestParam(value = "key", required = true) String key) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("Your message", message);
        map.put("Your key", key);

        // create a simple conditional statement that outputs an error message if key is
        // not "howdy" (case insensitive)
        if (!key.equalsIgnoreCase("howdy")) {
            map.put("Result", "FAILED");
            logger.error("call FAILED"); // sample error logging
        } else {
            map.put("Result", "SUCCESS");
        }

        logger.info("successfully called /parameter");
        // the List called "strings" will output in JSON
        return map;
    }

    // this payload is used for inspecting payload and params
    @RequestMapping(value = "/inspect", produces = "application/json")
    public String inspectAll(
            @RequestBody String payload,
            @RequestParam(required = true) Map<String, String> requestParams)
            throws JsonMappingException, JsonProcessingException {
        String prettyPayloadStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readTree(payload));
        logger.info("successfully called /inspect with payload:");
        logger.info(prettyPayloadStr);
        logger.info("with params");
        logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestParams));
        return payload;
    }

    // this payload is used for inspecting params
    @RequestMapping(value = "/inspectparams")
    public String inspectParam(
            @RequestParam(required = true) Map<String, String> requestParams)
            throws JsonMappingException, JsonProcessingException {
        logger.info("with params");
        try {
            logger.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestParams));
        } catch (Exception e) {
            logger.error("error parsing requestParams: " + requestParams.toString());
        }
        return "Done";
    }
}
