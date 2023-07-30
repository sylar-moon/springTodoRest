package my.group.utility;

import my.group.dto.TaskDtoRequest;
import my.group.dto.TaskDtoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class JsonConverterTest {
    static JsonConverter jsonConverter;
    @BeforeAll
    static void init(){
        jsonConverter = new JsonConverter();
    }

    @Test
    void createJsonFromObjects() {
        TaskDtoRequest taskDtoRequest = new TaskDtoRequest("Купить морковку","Завтра утром в Сильпо купить молодую морковку",
                LocalDateTime.of(2023,7,23,8,0));
        String jsonFromObj = jsonConverter.createJsonFromObjects(taskDtoRequest);
        String testJson = "{\"title\":\"Купить морковку\",\"description\":\"Завтра утром в Сильпо купить молодую морковку\",\"dateTimeToEndTask\":\"2023-07-23T08:00:00\"}";
        Assertions.assertEquals(jsonFromObj,testJson);
    }
}