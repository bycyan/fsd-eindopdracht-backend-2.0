package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.MessageInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.TaskInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.MessageOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.TaskOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.BadRequestException;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Message;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.Task;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.MessageRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    //INJECT
    private final MessageRepository messageRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    //CONSTRUCT
    public MessageService(MessageRepository messageRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    //////////////////////////////////////////////////////
    //SERVICES
    //////////////////////////////////////////////////////

    //Endpoint: /message/post/{projectId}/{userId}
    public MessageOutputDto postMessage(Long projectId, MessageInputDto messageInputDto, Long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException(""));

        Message message = new Message();
        message = transferMessageInputDtoToMessage(messageInputDto, message);
        message.setProject(project);

        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User not found"));
        message.setMessageAuthor(user);

        messageRepository.save(message);
        return transferMessageToMessageOutputDto(message);
    }


    //Endpoint: /message/{messageId}
    public MessageOutputDto getMessage(Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new RecordNotFoundException(""));
        return transferMessageToMessageOutputDto(message);
    }

    //Endpoint: /message/{messageId}
    public MessageOutputDto updateMessage (Long messageId, MessageInputDto messageInputDto) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new RecordNotFoundException(""));
        messageRepository.save(transferMessageInputDtoToMessage (messageInputDto, message));
        return transferMessageToMessageOutputDto(message);
    }

    //Endpoint: /message/{messageId}
    public void deleteMessage (Long messageId) throws BadRequestException {
        try{
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new RecordNotFoundException(""));
            messageRepository.delete(message);
        } catch (Exception e){
            throw new BadRequestException("");
        }
    }



    //////////////////////////////////////////////////////
    //TRANSFER METHODS
    //////////////////////////////////////////////////////

    public Message transferMessageInputDtoToMessage(MessageInputDto messageInputDto, Message message) {
        message.setMessageText(messageInputDto.messageText);
        return message;
    }

    public MessageOutputDto transferMessageToMessageOutputDto(Message message) {
        MessageOutputDto messageOutputDto = new MessageOutputDto();
        messageOutputDto.messageId = message.getMessageId();
        messageOutputDto.messageText = message.getMessageText();
        messageOutputDto.messageAuthor = UserServiceTransferMethod.transferUserToUserOutputDto(message.getMessageAuthor());
        return messageOutputDto;
    }

}
