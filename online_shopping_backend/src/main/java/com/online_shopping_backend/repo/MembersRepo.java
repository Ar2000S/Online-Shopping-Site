package com.online_shopping_backend.repo;

import com.online_shopping_backend.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MembersRepo extends JpaRepository<Members, Integer> {

    @Query("SELECT MAX(m.memberNo) FROM Members m")
    Optional<Integer> findMaxMemberNo();
}