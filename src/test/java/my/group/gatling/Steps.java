package my.group.gatling;

import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.CoreDsl;

import java.util.Base64;

import static io.gatling.javaapi.core.CoreDsl.StringBody;

public class Steps {
    public static ChainBuilder taskReq = CoreDsl.exec(
            HttpDsl.http("addTask")
                    .post("/api/public/tasks")
                    .header("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(("admin" + ":" + "admin").getBytes()))
                    .header("Content-Type", "application/json")
                    .body(StringBody("{\n" +
                            "  \"title\": \"Вигуляти пса\",\n" +
                            "  \"description\": \"Після роботи вигуляти пса\",\n" +
                            "  \"dateTimeToEndTask\": \"2023-07-09 18:30:00\"\n" +
                            "}"))
                    .check(HttpDsl.status().is(200))
    );
}
