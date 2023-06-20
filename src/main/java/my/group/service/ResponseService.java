package my.group.service;

import my.group.model.Response;
import my.group.model.Task;
import my.group.utility.MyLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResponseService {

    private final Logger logger = new MyLogger().getLogger();

    @Autowired
    MessageSource messageSource;

    public List<Response> getListResponses(Iterable<Task> allTask, String url) {
        String message = getResponseMessage("task.get.success",null);
        return StreamSupport.stream(allTask.spliterator(), false)
                .map(task -> new Response(task, url, HttpStatus.OK, message))
                .collect(Collectors.toList());
    }


    public String getResponseMessage(String key,Object[] obj){
        return messageSource.getMessage(key, obj, LocaleContextHolder.getLocale());
    }
}
