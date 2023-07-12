package my.group.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class GatlingTest extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http.baseUrl("http://springapp-env.eba-pch5mnqp.us-west-2.elasticbeanstalk.com/");

    public GatlingTest(){
        this.setUp(
          Scenario.addTaskScenario.injectOpen(
                  CoreDsl.atOnceUsers(1),
                  CoreDsl.nothingFor(3),
                  CoreDsl.rampUsersPerSec(1).to(5).during(10),
                  CoreDsl.constantUsersPerSec(3).during(10)
          ) .protocols(httpProtocol)
        );
    }
}