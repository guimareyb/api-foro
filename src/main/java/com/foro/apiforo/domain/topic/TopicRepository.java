package com.foro.apiforo.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//queries para la base de datos
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByFlagTrue(Pageable pageable);

    @Query("""
            select t.flag from Topic t
            where t.id =:idTopic
            """)
    Boolean findActiveById(Long idTopic);

}
