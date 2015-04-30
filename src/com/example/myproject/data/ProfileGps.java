package com.example.myproject.data;

public class ProfileGps {
	private int pid;
	private String pname;
	private double platitude;
	private double plongitude;
	private boolean pro_enable;
	private boolean rem_enable;
	private int pvolume;
	private int pcoverage_radius;
	private String preminder;
	private String pringtone;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getPlatitude() {
		return platitude;
	}
	public void setPlatitude(double platitude) {
		this.platitude = platitude;
	}
	public double getPlongitude() {
		return plongitude;
	}
	public void setPlongitude(double plongitude) {
		this.plongitude = plongitude;
	}
	
	public boolean isPro_enable() {
		return pro_enable;
	}
	public void setPro_enable(boolean pro_enable) {
		this.pro_enable = pro_enable;
	}
	public boolean isRem_enable() {
		return rem_enable;
	}
	public void setRem_enable(boolean rem_enable) {
		this.rem_enable = rem_enable;
	}
	public int getPvolume() {
		return pvolume;
	}
	public void setPvolume(int pvolume) {
		this.pvolume = pvolume;
	}
	public int getPcoverage_radius() {
		return pcoverage_radius;
	}
	public void setPcoverage_radius(int pcoverage_radius) {
		this.pcoverage_radius = pcoverage_radius;
	}
	public String getPreminder() {
		return preminder;
	}
	public void setPreminder(String preminder) {
		this.preminder = preminder;
	}
	
	public String getPringtone() {
		return pringtone;
	}
	public void setPringtone(String pringtone) {
		this.pringtone = pringtone;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return pname;
	}

}
