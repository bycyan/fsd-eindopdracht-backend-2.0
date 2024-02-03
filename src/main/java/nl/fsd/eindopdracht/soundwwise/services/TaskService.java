package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.TaskInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.TaskOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.Task;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    //INJECT
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    //CONSTRUCT
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    //addTask
    public TaskOutputDto addTask(Long projectId, TaskInputDto taskInputDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException(""));

        Task task = new Task();
        task = transferTaskInputDtoToTask(taskInputDto, task);
        task.setTaskComplete(false);
        task.setProject(project);

        taskRepository.save(task);
        return transferTaskToTaskOutputDto(task);
    }

    //////////////////////////////////////////////////////
    //TRANSFER METHODS
    //////////////////////////////////////////////////////

    public Task transferTaskInputDtoToTask(TaskInputDto taskInputDto, Task task) {
        task.setTaskName(taskInputDto.taskName);
        return task;
    }

    public TaskOutputDto transferTaskToTaskOutputDto(Task task) {
        TaskOutputDto taskOutputDto = new TaskOutputDto();
        taskOutputDto.taskId = task.getTaskId();
        taskOutputDto.taskName = task.getTaskName();
        taskOutputDto.taskComplete = task.isTaskComplete();
        return taskOutputDto;
    }
}
