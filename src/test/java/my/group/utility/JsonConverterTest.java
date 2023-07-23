package my.group.utility;

import my.group.dto.TaskDto;
import my.group.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterTest {
    static JsonConverter jsonConverter;
    @BeforeAll
    static void init(){
        jsonConverter = new JsonConverter();
    }

    @Test
    void createJsonFromObjects() {
        TaskDto taskDto = new TaskDto("Купить морковку","Завтра утром в Сильпо купить молодую морковку",
                LocalDateTime.of(2023,7,23,8,0));
        String jsonFromObj = jsonConverter.createJsonFromObjects(taskDto);
        String testJson = "{\"title\":\"Купить морковку\",\"description\":\"Завтра утром в Сильпо купить молодую морковку\",\"dateTimeToEndTask\":\"2023-07-23T08:00:00\"}";
        Assertions.assertEquals(jsonFromObj,testJson);
    }
}