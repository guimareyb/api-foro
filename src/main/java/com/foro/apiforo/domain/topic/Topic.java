package com.foro.apiforo.domain.topic;

import com.foro.apiforo.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="topics")
@Entity(name ="Topic")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private String tittle;

    private String message;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private Course course;

    private boolean flag;

    public Topic(DataTopicInsert dataTopicInsert, User user){
        this.flag = true;
        this.user = user;
        this.tittle = dataTopicInsert.tittle();
        this.message = dataTopicInsert.message();
        this.creationDate = dataTopicInsert.creationDate();
        this.course = dataTopicInsert.course();
    }

    public void updateData(DataTopicUpdate updateDataTopic){
        if (updateDataTopic.tittle() !=null){
            this.tittle = updateDataTopic.tittle();
        }
        if (updateDataTopic.message() !=null){
            this.message = updateDataTopic.message();
        }
        if (updateDataTopic.course() !=null){
            this.course = updateDataTopic.course();
        }
    }

    public void disableTopic(){
        this.flag = false;
    }
}
