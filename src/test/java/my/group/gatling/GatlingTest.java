package my.group.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GatlingTest extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http.baseUrl("http://localhost:5000/");

    public GatlingTest(){
        this.setUp(
          Scenario.addTaskScenario.injectOpen(
                  CoreDsl.constantUsersPerSec(1).during(1)
          ) .protocols(httpProtocol)
        );
    }
}