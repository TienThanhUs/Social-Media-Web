package com.hcmus.social_media_app.Repository;

import com.hcmus.social_media_app.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeJpaRepository extends JpaRepository<Like, Long> {
}
