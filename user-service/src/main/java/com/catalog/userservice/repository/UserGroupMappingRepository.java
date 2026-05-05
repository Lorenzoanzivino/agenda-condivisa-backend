package com.catalog.userservice.repository;

import com.catalog.userservice.entity.UserGroupMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupMappingRepository extends JpaRepository<UserGroupMappingEntity, String> {
    List<UserGroupMappingEntity> findByGroupId(String groupId);

    List<UserGroupMappingEntity> findByUserId(String userId);

    boolean existsByUserIdAndGroupId(String userId, String groupId);
}