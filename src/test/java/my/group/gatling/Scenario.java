package my.group.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;

public class Scenario {

  public static   ScenarioBuilder addTaskScenario = CoreDsl.scenario("add task scenario")
            .exec(Steps.authReq);
}
