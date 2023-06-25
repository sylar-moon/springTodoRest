package my.group.service;

import my.group.model.Response;
import my.group.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResponseService {

    @Autowired
    MessageSource messageSource;

    public List<Response> getListResponses(Iterable<Task> allTask, String url) {
        String message = getMessage("task.get.success",null);
        return StreamSupport.stream(allTask.spliterator(), false)
                .map(task -> new Response(task, url, HttpStatus.OK, message))
                .collect(Collectors.toList());
    }


    public String getMessage(String key, Object[] obj){
        return messageSource.getMessage(key, obj, LocaleContextHolder.getLocale());
    }
}
