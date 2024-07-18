package controller;

import graphql.ExecutionInput;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GraphQLController {

    private final GraphQL graphQL;

    @Autowired
    public GraphQLController(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @PostMapping(value = "/graphql", consumes = "application/json", produces = "application/json")
    public Object graphql(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(query).build();
        return graphQL.execute(executionInput).toSpecification();
    }
}
