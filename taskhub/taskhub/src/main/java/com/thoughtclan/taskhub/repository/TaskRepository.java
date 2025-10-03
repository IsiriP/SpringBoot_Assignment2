package com.thoughtclan.taskhub.repository;

import com.thoughtclan.taskhub.model.Task;
import com.thoughtclan.taskhub.model.enums.TaskStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDeveloperId(Long developerId);
    List<Task> findByProjectId(Long projectId);

    long countByDeveloperIdAndStatus(Long developerId, TaskStatus status);

    // Overdue tasks: exclude tasks that are DONE by using a parameter
    @Query("""
        SELECT t
        FROM Task t
        WHERE t.dueDate < :date
          AND t.status <> :doneStatus
        """)
    List<Task> findOverdueTasks(@Param("date") LocalDate date,
                                @Param("doneStatus") TaskStatus doneStatus);

    // Returns (developerId, overdueCount) ordered desc. Use pageable to limit top N.
    @Query("""
        SELECT t.developer.id AS devId, COUNT(t) AS overdueCount
        FROM Task t
        WHERE t.dueDate < :date
          AND t.status <> :doneStatus
        GROUP BY t.developer.id
        ORDER BY COUNT(t) DESC
        """)
    List<Object[]> findDeveloperIdAndOverdueCount(@Param("date") LocalDate date,
                                                  @Param("doneStatus") TaskStatus doneStatus,
                                                  Pageable pageable);
}
