package com.kingtone.jw.service.bs.apk.impl;

import java.util.List;
import java.util.ResourceBundle;

import com.kingtone.jw.biz.common.domain.JwUpdate;
import com.kingtone.jw.service.bs.apk.ApkCheckBS;
import com.kingtone.jw.service.dao.LogDAO;
import com.kingtone.ssi.log.SSILogger;

/**
 * <p>apk包版本检测业务实现类</p>
 *
 * @author: 邹甲乐(zoujiale@kingtoneinfo.com)
 * @date: Jan 5, 2012
*/
public class ApkCheckBSImpl implements ApkCheckBS{
	
	private LogDAO logDAO;

	/* (non-Javadoc)
	 * @see com.kingtone.jw.service.bs.apk.ApkCheckBS#checkApk(java.lang.String)
	 */
	public String checkApk(String currentVersion) {
		String backInfo = "not";
		double newVersionD = 1.0;
		double currentVersionD = 1.0;
		String updateURL = "";
		try {
			List jwUpdateList = logDAO.queryUpdateInfo();
			if (jwUpdateList != null && jwUpdateList.size() > 0) {
				updateURL = ResourceBundle.getBundle("configes").getString(
						"apk_updateURL");
				JwUpdate jwUpdate = (JwUpdate) jwUpdateList.get(0);
                updateURL+=jwUpdate.getName_copy();
				currentVersionD = Double.parseDouble(currentVersion);
				newVersionD = Double.parseDouble(jwUpdate.getVersion());
				if (newVersionD > currentVersionD) {
					// 返回信息为"版本号|版本下载地址"
					backInfo = newVersionD+"|"+updateURL;
				}
			}
		} catch (Exception e) {
			SSILogger.Bizlogger.info("检测更新异常" + e, e);
			e.printStackTrace();
		}
		return backInfo;
	}

	public void setLogDAO(LogDAO logDAO) {
		this.logDAO = logDAO;
	}

}
