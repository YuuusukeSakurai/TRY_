package com.nexus.whc.models;

import java.io.Serializable;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CalendarData implements Serializable {
	
	@Id
	private Integer seqId;
	private Integer day;
	private boolean holidayFlg;
	private String comment;
	private Integer weekStatus;

}
