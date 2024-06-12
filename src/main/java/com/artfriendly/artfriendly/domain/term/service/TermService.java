package com.artfriendly.artfriendly.domain.term.service;

import com.artfriendly.artfriendly.domain.term.entity.Term;
import com.artfriendly.artfriendly.domain.term.repository.TermRepository;
import com.artfriendly.artfriendly.global.exception.common.BusinessException;
import com.artfriendly.artfriendly.global.exception.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TermService {
    private final TermRepository termRepository;

    @Transactional
    public void initTerms() {
        List<Term> terms = new ArrayList<>();
        List<String> termDescriptions = List.of("서비스 이용약관 동의", "개인정보 수집 및 이용 동의", "위치 정보 이용약관 동의");

        for (String termDescription : termDescriptions) {
            Term term = Term.builder()
                    .description(termDescription)
                    .build();

            terms.add(term);
        }

        termRepository.saveAll(terms);
    }

    public Term getTerm(long termId) {
        return termRepository.findById(termId).orElseThrow(() -> new BusinessException(ErrorCode.TERM_NOT_FOUND));
    }
}
