package com.gira.giraexamprep.repository;

import com.gira.giraexamprep.model.entity.ClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<ClassificationEntity, Long> {
}
