package com.oracle.comventory.domain.statusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "STATUS_TYPE")
@Getter
@ToString
@NoArgsConstructor
@IdClass(StatusTypeId.class)
public class StatusType {

    @Id
    @Column(name = "BCODE")
    private Integer bcode;

    @Id
    @Column(name = "MCODE")
    private Integer mcode;

    @Column(name = "CODE_CONTENTS")
    private String codeContents;
}