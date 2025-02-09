package com.faraz.Kanban.user;

import com.faraz.Kanban.stripe.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM USERS WHERE email = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);

    List<User> findBySubscriptionPlan(SubscriptionPlan subscriptionPlan);

}

