package com.oracle.comventory.dto.statusType;



import com.oracle.comventory.domain.statusType.StatusType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusTypeDto {
	

    private Integer bcode;
    private Integer mcode;
    private String  code_contents;
    
    

    public StatusTypeDto(StatusType statusType) {
        this.bcode = statusType.getBcode();
        this.mcode = statusType.getMcode();
        this.code_contents = statusType.getCodeContents();
    }
}