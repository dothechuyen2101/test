package com.banking.core.persistence.repository;

import com.banking.core.persistence.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * Base repository interface for all banking entities.
 * Provides JPA Repository + Specification Executor for dynamic queries.
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

    /**
     * Find entity by ID, excluding soft-deleted records.
     * Subclasses for SoftDeletableEntity should override with @Query.
     */
    @Override
    Optional<T> findById(String id);

    /**
     * Check if entity exists by ID.
     */
    @Override
    boolean existsById(String id);
}
