package com.foro.apiforo.domain.topic;

import com.foro.apiforo.domain.topic.validations.TopicsValidator;
import com.foro.apiforo.domain.user.User;
import com.foro.apiforo.domain.user.UserRepository;
import com.foro.apiforo.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private List<TopicsValidator> validators;

    public DataTopicResponse inserTopic(DataTopicInsert dataTopicInsert){
        if (!userRepository.findById(dataTopicInsert.userId()).isPresent()){
            throw new IntegrityValidation("This id for the user was not found");
        }

        validators.forEach(v -> v.validar(dataTopicInsert));

        User user = userRepository.findById(dataTopicInsert.userId()).get();
        Topic topic = topicRepository.save(new Topic(dataTopicInsert, user));
        return new DataTopicResponse(topic);
    }

}
