package com.changfa.frame.service.util;

/**
 * 
 * Title: RequestHead <br>
 * Description: <br>
 * Created by Baixd @since 2015年5月11日 <br>
 * Copyright (c) 2015 CATTSoft
 */
public class RequestHead {
	
	private Client client;
	private Equipement equipement;
	private Other other;
	private Personal personal;
	
	public RequestHead(Client client, Equipement equipement, Other other, Personal personal){
		this.client = client;
		this.equipement = equipement;
		this.other = other;
		this.personal = personal;
	}
	
	public Client getClient(){
		return client;
	}
	
	public Equipement getEquipement(){
		return equipement;
	}

	public Other getOther(){
		return other;
	}
	
	public Personal getPersonal(){
		return personal;
	}
	public static class Client {
		
		private String applicationId;
		private String netType;
		private int versionCode;
		private String versionName;
		
		public String getApplicationId() {
			return applicationId;
		}
		public void setApplicationId(String applicationId) {
			this.applicationId = applicationId;
		}
		public String getNetType() {
			return netType;
		}
		public void setNetType(String netType) {
			this.netType = netType;
		}
		public int getVersionCode() {
			return versionCode;
		}
		public void setVersionCode(int versionCode) {
			this.versionCode = versionCode;
		}
		public String getVersionName() {
			return versionName;
		}
		public void setVersionName(String versionName) {
			this.versionName = versionName;
		}

	}

	public static class Equipement {
		private String deviceId;
		private String phoneNum;
		private String name;
		private String osName;
		private String osVersion;
		private String version;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public String getPhoneNum() {
			return phoneNum;
		}

		public void setPhoneNum(String phoneNum) {
			this.phoneNum = phoneNum;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOsName() {
			return osName;
		}

		public void setOsName(String osName) {
			this.osName = osName;
		}

		public String getOsVersion() {
			return osVersion;
		}

		public void setOsVersion(String osVersion) {
			this.osVersion = osVersion;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

	}

	public static class Other {
		private String country;
		private String lan;
		private String timeZone;

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getLan() {
			return lan;
		}

		public void setLan(String lan) {
			this.lan = lan;
		}

		public String getTimeZone() {
			return timeZone;
		}

		public void setTimeZone(String timeZone) {
			this.timeZone = timeZone;
		}

	}

	public static class Personal {
		private String userId;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

	}
}
