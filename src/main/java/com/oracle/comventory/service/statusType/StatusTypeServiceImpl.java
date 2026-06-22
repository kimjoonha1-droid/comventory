package com.oracle.comventory.service.statusType;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.comventory.domain.statusType.StatusType;
import com.oracle.comventory.dto.statusType.StatusTypeDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class StatusTypeServiceImpl implements StatusTypeService {
	
	 private final EntityManager em;

	@Override
	public List<StatusTypeDto> findByBcode(Integer bcode) {
		log.info("StatusTypeServiceImpl findByBcode start bcode -> " + bcode);

        String jpql = """
                select s
                from StatusType s
                where s.bcode = :bcode
                and s.mcode <> 999
                order by s.mcode
                """;

        TypedQuery<StatusType> query =
                em.createQuery(jpql, StatusType.class);

        query.setParameter("bcode", bcode);

        return query.getResultList()
                .stream()
                .map(StatusTypeDto::new)
                .collect(Collectors.toList());
    }
}
