package com.oracle.comventory.service.statusType;

import java.util.List;

import com.oracle.comventory.dto.statusType.StatusTypeDto;

public interface StatusTypeService {
	
	List<StatusTypeDto> findByBcode(Integer bcode);

}
