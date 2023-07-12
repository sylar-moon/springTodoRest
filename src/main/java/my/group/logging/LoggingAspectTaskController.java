package my.group.logging;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspectTaskController {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspectTaskController.class);

    @Before("execution(* my.group.controller.TaskController.getAllTasks())")
    public void logBeforeGetAllTasks() {
        logger.info("User is trying to get all tasks");
    }

    @Around("execution(* my.group.controller.TaskController.getAllTasks())")
    public Object logTimeGetAllTasks(ProceedingJoinPoint joinPoint) {
        return logTimeExecuteMethodAndReturnResult(joinPoint, "getAllTasks");
    }

    @Before("execution(* my.group.controller.TaskController.addTask(..))")
    public void logBeforeAddTask() {
        logger.info("User is trying to add task");
    }

    @Around("execution(* my.group.controller.TaskController.addTask(..))")
    public Object logTimeAddTask(ProceedingJoinPoint joinPoint) {
        return logTimeExecuteMethodAndReturnResult(joinPoint, "addTask");
    }

    @Before("execution(* my.group.controller.TaskController.deleteTask(..))")
    public void logBeforeDeleteTask() {
        logger.info("User is trying to delete task");
    }

    @Around("execution(* my.group.controller.TaskController.deleteTask(..))")
    public Object logTimeDeleteTask(ProceedingJoinPoint joinPoint) {
        return logTimeExecuteMethodAndReturnResult(joinPoint, "deleteTask");
    }

    @Before("execution(* my.group.controller.TaskController.updateTaskStatus(..))")
    public void logBeforeUpdateTaskStatus() {
        logger.info("User is trying to update task");
    }


    @Around("execution(* my.group.controller.TaskController.updateTaskStatus(..))")
    public Object logTimeUpdateTaskStatus(ProceedingJoinPoint joinPoint) {
        return logTimeExecuteMethodAndReturnResult(joinPoint, "updateTaskStatus");
    }

    @Before("execution(* my.group.controller.TaskController.getTaskById(..))")
    public void logBeforeGetTaskById() {
        logger.info("User is trying to get task by id");
    }

    @Around("execution(* my.group.controller.TaskController.getTaskById(..))")
    public Object logTimeGetTaskById(ProceedingJoinPoint joinPoint) {
        return logTimeExecuteMethodAndReturnResult(joinPoint, "getTaskById");
    }

    @Before("execution(* my.group.controller.TaskController.getAllTaskByPage(..))")
    public void logBeforeGetAllTaskByPage() {
        logger.info("User is trying to get all tasks by page");
    }

    @Around("execution(* my.group.controller.TaskController.getAllTaskByPage(..))")
    public Object logTimeGetAllTaskByPage(ProceedingJoinPoint joinPoint) {
        return logTimeExecuteMethodAndReturnResult(joinPoint, "getAllTaskByPage");
    }

    private Object logTimeExecuteMethodAndReturnResult(ProceedingJoinPoint joinPoint, String nameMethod) {
        Object result = null;
        try {
            LocalDateTime startTime = LocalDateTime.now();
            result = joinPoint.proceed();
            LocalDateTime endTime = LocalDateTime.now();
            Duration duration = Duration.between(startTime, endTime);
            logger.info("Time to execute {}: {}.millis", nameMethod, duration.toMillis());
        } catch (Throwable throwable) {
            logger.error("Unable to run {} method", nameMethod, throwable);
            System.exit(0);
        }
        return result;
    }

}