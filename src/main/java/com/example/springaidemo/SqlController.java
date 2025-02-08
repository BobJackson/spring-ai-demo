package com.example.springaidemo;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SqlController {

    private final OllamaApi api;

    public SqlController(OllamaApi api) {
        this.api = api;
    }

    @GetMapping("/sql/generate")
    public OllamaApi.GenerateResponse embed(@RequestParam(value = "prompt", defaultValue = "Give me taxis with more than 2 passengers")
                                                String prompt) {

        String a = "IFS(EmpYears(#3195908715114927996#)>=20,15,EmpYears(#3195908715114927996#)>=10,10,5)";
        String b = "IFS(EmpYears(#3195908715114927996#)>=20, 15, EmpYears(#3195908715114927996#)>=10,10,5)";

        String system = """
                Here is the database schema that the SQL query will run on:
                CREATE TABLE taxi (
                    VendorID bigint,
                    tpep_pickup_datetime timestamp,
                    tpep_dropoff_datetime timestamp,
                    passenger_count double,
                    trip_distance double,
                    fare_amount double,
                    extra double,
                    tip_amount double,
                    tolls_amount double,
                    improvement_surcharge double,
                    total_amount double,
                );""";
        OllamaApi.GenerateRequest request = OllamaApi.GenerateRequest.builder(prompt)
                .withStream(false)
                .withModel("duckdb-nsql")
                .withSystem(system)
                .build();
        return api.generate(request);
    }
}
