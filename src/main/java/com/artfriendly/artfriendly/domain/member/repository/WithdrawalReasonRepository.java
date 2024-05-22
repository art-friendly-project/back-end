package com.artfriendly.artfriendly.domain.member.repository;

import com.artfriendly.artfriendly.domain.member.entity.WithdrawalReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalReasonRepository extends JpaRepository<WithdrawalReason, Long> {
}
