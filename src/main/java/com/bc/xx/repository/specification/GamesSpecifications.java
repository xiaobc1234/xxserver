package com.bc.xx.repository.specification;

import com.bc.xx.model.Games;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by niu on 2017/4/5.
 */
public class GamesSpecifications {

    public static Specification<Games> filterByDelete(final Integer delFlag) {

        return new Specification<Games>() {
            @Override
            public Predicate toPredicate(Root<Games> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.le(root.<Number>get("delFlag"), delFlag);
            }
        };
    }

}
