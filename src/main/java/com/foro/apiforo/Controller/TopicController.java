package com.foro.apiforo.Controller;

import com.foro.apiforo.domain.topic.DataTopicInsert;
import com.foro.apiforo.domain.topic.DataTopicResponse;
import com.foro.apiforo.domain.topic.Topic;
import com.foro.apiforo.domain.topic.TopicRepository;
import com.foro.apiforo.domain.user.User;
import com.foro.apiforo.domain.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<DataTopicResponse> insertTopic (@Valid @RequestBody DataTopicInsert insertDataTopic,
                                                          UriComponentsBuilder uriComponentsBuilder){
       User user = userRepository.findById(insertDataTopic.userId()).get();
       Topic topic = topicRepository.save(new Topic(insertDataTopic, user));
       DataTopicResponse responseDataTopic = new DataTopicResponse(topic);
       URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
       return ResponseEntity.created(url).body(responseDataTopic);
    }
}
