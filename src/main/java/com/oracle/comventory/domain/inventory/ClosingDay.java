package com.oracle.comventory.domain.inventory;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "closing_day")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClosingDay {
	@Id
	private String closing_ymd;
	private int closing_status;
	private int reg_emp_no;
	@CreatedDate
	private LocalDateTime reg_date;
}
