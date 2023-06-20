package my.group.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import my.group.utility.MyLogger;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

public class Response {

    private String obj;
    private  String url;
    private  HttpStatus status;
    private String message;

    public Response(){}

    public Response(Object task, String url, HttpStatus status,String message) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            this.obj = mapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            Logger logger = new MyLogger().getLogger();
            logger.error("Unable to serialize task object:{}  to json format",task,e);
        }
        this.url = url;
        this.status = status;
        this.message=message;
    }

    public String getObj() {
        return obj;
    }

    public Response setObj(String obj) {
        this.obj = obj;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public Response setUrl(String url) {
        this.url = url;
        return this;
    }

    public Response setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "obj='" + obj + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
