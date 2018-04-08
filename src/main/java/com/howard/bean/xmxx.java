/**  
* @Title: xmxx.java  
* @Package com.howard.bean  
* @Description: TODO(用一句话描述该文件做什么)  
* @author hongzheng  
* @date 2018年4月5日  
* @version V1.0  
*/  
package com.howard.bean;

/**  
* @ClassName: xmxx  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @Created by: biyyoung 
* @date 2018年4月5日  
*    
*/
public class xmxx {
	private int deptid;
	private String xmfrm;
	private int bmdm;
	private String xmdwmc;
	private String xmmc;
	private String frdm;
	private int jhztz;
	private int xmfl;
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}
	public String getXmfrm() {
		return xmfrm;
	}
	public void setXmfrm(String xmfrm) {
		this.xmfrm = xmfrm;
	}
	public int getBmdm() {
		return bmdm;
	}
	public void setBmdm(int bmdm) {
		this.bmdm = bmdm;
	}
	public String getXmdwmc() {
		return xmdwmc;
	}
	public void setXmdwmc(String xmdwmc) {
		this.xmdwmc = xmdwmc;
	}
	public String getXmmc() {
		return xmmc;
	}
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	public String getFrdm() {
		return frdm;
	}
	public void setFrdm(String frdm) {
		this.frdm = frdm;
	}
	public int getJhztz() {
		return jhztz;
	}
	public void setJhztz(int jhztz) {
		this.jhztz = jhztz;
	}
	public int getXmfl() {
		return xmfl;
	}
	public void setXmfl(int xmfl) {
		this.xmfl = xmfl;
	}
	@Override
	public String toString() {
		return "xmxx [deptid=" + deptid + ", xmfrm=" + xmfrm + ", bmdm=" + bmdm + ", xmdwmc=" + xmdwmc + ", xmmc="
				+ xmmc + ", frdm=" + frdm + ", jhztz=" + jhztz + ", xmfl=" + xmfl + "]";
	}
	public xmxx(int deptid, String xmfrm, int bmdm, String xmdwmc, String xmmc, String frdm, int jhztz, int xmfl) {
		super();
		this.deptid = deptid;
		this.xmfrm = xmfrm;
		this.bmdm = bmdm;
		this.xmdwmc = xmdwmc;
		this.xmmc = xmmc;
		this.frdm = frdm;
		this.jhztz = jhztz;
		this.xmfl = xmfl;
	}
	
	

	
	
}
