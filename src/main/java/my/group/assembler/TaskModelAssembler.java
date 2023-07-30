package my.group.assembler;

import my.group.controller.TaskController;
import my.group.dto.TaskDtoResponse;
import my.group.model.Task;
import my.group.service.TaskService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {
    private final Logger logger = LoggerFactory.getLogger(TaskModelAssembler.class);

    @NotNull
    @Override
    public EntityModel<Task> toModel(@NotNull Task entity) {
        EntityModel<Task> entityModel = EntityModel.of(entity,linkTo(methodOn(TaskController.class).getTaskById(entity.getId())).withSelfRel());
    logger.info("toModel : {}",entityModel);
   return entityModel;
     }
}
