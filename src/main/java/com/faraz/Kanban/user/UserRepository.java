package com.faraz.Kanban.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM USERS WHERE email = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);

}

