package com.bc.xx.repository.specification;

import com.bc.xx.model.Games;
import com.bc.xx.model.TasksQueue;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by niu on 2017/4/5.
 */
public class TasksQueueSpecifications {

    public static Specification<TasksQueue> filterByDevicesId(final String deviceId) {

        return new Specification<TasksQueue>() {
            @Override
            public Predicate toPredicate(Root<TasksQueue> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("deviceId"), deviceId);
            }
        };
    }

}
