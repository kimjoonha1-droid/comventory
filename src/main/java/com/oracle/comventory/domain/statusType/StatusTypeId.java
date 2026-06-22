package com.oracle.comventory.domain.statusType;


import java.io.Serializable;

import lombok.Data;

@Data
public class StatusTypeId implements Serializable {

    private Integer bcode;
    private Integer mcode;
}