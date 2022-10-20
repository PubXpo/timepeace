package com.tsheetweb.project.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT ca FROM Category ca WHERE ca.project.p_id= :projectID ORDER BY ca.categoryName ASC")
    List<Category> findProfileByProjectID(long projectID);
}
