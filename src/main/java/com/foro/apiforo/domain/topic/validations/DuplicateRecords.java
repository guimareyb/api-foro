package com.foro.apiforo.domain.topic.validations;

import com.foro.apiforo.domain.topic.DataTopicInsert;
import com.foro.apiforo.domain.topic.TopicRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DuplicateRecords implements TopicsValidator{

    @Autowired
    private TopicRepository repository;

    @Override
    public void validar(DataTopicInsert dataTopicInsert) {
        var duplicateMessage = repository.findDuplicateMessage(dataTopicInsert.message());
        var duplicateTittle = repository.findDuplicateTittle(dataTopicInsert.tittle());
        if (duplicateTittle && duplicateMessage){
            throw new ValidationException("There is already a topic with this title and message.");
        }
        if (duplicateTittle){
            throw new ValidationException("There is already a topic with this tittle.");
        }
        if (duplicateMessage){
            throw new ValidationException("There is already a topic with this message.");
        }
    }
}
