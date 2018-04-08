package com.trump.auction.cust.util.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "mission_num", "dest_id" })
@XmlRootElement(name = "xml")
public class Dest {
	private String mission_num;
	private String dest_id;

	public String getMission_num() {
		return mission_num;
	}

	public String getDest_id() {
		return dest_id;
	}

	public void setMission_num(String missionNum) {
		mission_num = missionNum;
	}

	public void setDest_id(String destId) {
		dest_id = destId;
	}

	@Override
	public String toString() {
		return "Dest [dest_id=" + dest_id + ", mission_num=" + mission_num + "]";
	}

}
