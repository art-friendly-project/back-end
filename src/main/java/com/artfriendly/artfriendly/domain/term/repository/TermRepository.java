package com.artfriendly.artfriendly.domain.term.repository;

import com.artfriendly.artfriendly.domain.term.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
