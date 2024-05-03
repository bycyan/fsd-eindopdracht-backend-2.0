package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.TaskInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.TaskOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.BadRequestException;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.Task;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.TaskRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    //INJECT
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    //CONSTRUCT
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    //Endpoint: task/add/{projectId}
    public TaskOutputDto addTask(Long projectId, TaskInputDto taskInputDto, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException(""));

        Task task = new Task();
        task = transferTaskInputDtoToTask(taskInputDto, task);
        task.setTaskComplete(false);
        task.setProject(project);

        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        task.setTaskUser(user);

        taskRepository.save(task);
        return transferTaskToTaskOutputDto(task);
    }

    //Endpoint: /task/{taskId}
    public TaskOutputDto getTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RecordNotFoundException(""));
        return transferTaskToTaskOutputDto(task);
    }

    //Endpoint: /task/{taskId}
    public TaskOutputDto updateTask(Long taskId, TaskInputDto taskInputDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RecordNotFoundException(""));
        taskRepository.save(transferTaskInputDtoToTask(taskInputDto, task));
        return transferTaskToTaskOutputDto(task);
    }

    //Endpoint: /task/{taskId}
    public void deleteTask(Long taskId) throws BadRequestException {
        try{
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new RecordNotFoundException(""));
            taskRepository.delete(task);
        } catch (Exception e){
            throw new BadRequestException("");
        }
    }

    //////////////////////////////////////////////////////
    //TRANSFER METHODS
    //////////////////////////////////////////////////////

    public Task transferTaskInputDtoToTask(TaskInputDto taskInputDto, Task task) {
        task.setTaskName(taskInputDto.taskName);
        task.setTaskDue(taskInputDto.taskDue);
        task.setTaskComplete(taskInputDto.taskComplete);
        return task;
    }

    public TaskOutputDto transferTaskToTaskOutputDto(Task task) {
        TaskOutputDto taskOutputDto = new TaskOutputDto();
        taskOutputDto.taskId = task.getTaskId();
        taskOutputDto.taskName = task.getTaskName();
        taskOutputDto.taskDue = task.getTaskDue();
        taskOutputDto.taskComplete = task.isTaskComplete();
        taskOutputDto.taskUser = UserServiceTransferMethod.transferUserToUserOutputDto(task.getTaskUser());
        return taskOutputDto;
    }
}
