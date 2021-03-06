package com.kingtone.jw.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.kingtone.jw.service.dao.ContactsDAO;
import com.kingtone.jw.service.domain.QueryEnvelop;
import com.kingtone.jw.service.util.CommonTool;
import com.kingtone.ssi.log.SSILogger;
import com.kingtone.ssi.multids.CustomerContextHolder;
import com.kingtone.ssi.multids.CustomerType;

/**
 * 新通讯录实现类（结合OA系统和警务通系统的通讯录）
 * 16个局领导的通讯录不公开
 * @author ylp
 * 
 */
public class ContactsDAOImpl extends SimpleJdbcDaoSupport implements ContactsDAO {

	//查询OA所有机构    S
	private final String SQL_OA_JW_UNIT ="select u.orgid deptId,u.orgname unit_name from jwt_conf.sys_org u where u.orgid<>'590' ";
	
	private final String SQL_OA_FIND_USER="select u.fullname name,u.reg_runid deptid,u.username userid,u.mobilephone mobile,u1.orgname orgName,u.userno account,u.telephone unitTel,u.titleofpost duty,u.policePhone from "
		+"jwt_conf.sys_user u ,jwt_conf.sys_org u1 where u.reg_runid=u1.orgid and (u.userno not in ('160001','160008','160003','160005','160011','160012','160033','001870','160039','162162','160009','160398','160023') or u.userno is null) "
		+"and 1=1 ";
	private final String SQL_OA_FIND_USER_COUNT="select count(*) from "
		+"jwt_conf.sys_user u ,jwt_conf.sys_org u1 where u.reg_runid=u1.orgid and (u.userno not in ('160001','160008','160003','160005','160011','160012','160033','001870','160039','162162','160009','160398','160023') or u.userno is null) "
		+"and 1=1 ";
	/**
	 * 首页信息和搜索信息（含分页）
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@Override
	public QueryEnvelop getDepartment(QueryEnvelop request) throws Exception {
		QueryEnvelop response = new QueryEnvelop();

		@SuppressWarnings("unchecked")
		Map<String, String> condition = request.getCondition();
		String unitName = condition.get("UNIT_NAME");
		String mobile = condition.get("MOBILE");
		String name = condition.get("NAME");
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		CustomerContextHolder.setCustomerType(CustomerType.valueOf("bizDS"));
		
		if ( !StringUtils.isEmpty(mobile) || !StringUtils.isEmpty(name)) {// 有参数，查寻功能
			
			String searchSql = SQL_OA_FIND_USER;
			String countSql = SQL_OA_FIND_USER_COUNT;

			if (!StringUtils.isEmpty(mobile)) {
//				searchSql += " and u.mobilephone like '%" + mobile + "%' ";
//				countSql += " and u.mobilephone like '%" + mobile + "%' ";
				
				String sqlp="select count(*) from jwt_conf.sys_user where policephone like '%" + mobile + "%' ";
				String sqlt="select count(*) from jwt_conf.sys_user where telephone like '%" + mobile + "%' ";
				int p = this.getJdbcTemplate().queryForInt(sqlp);
				int t = this.getJdbcTemplate().queryForInt(sqlt);
				if(p>0){
					searchSql += " and u.policephone like '%" + mobile + "%' ";
					countSql += " and u.policephone like '%" + mobile + "%' ";
				}else if(t>0){
					searchSql += " and u.telephone like '%" + mobile + "%' ";
					countSql += " and u.telephone like '%" + mobile + "%' ";
				}else{
					searchSql += " and u.mobilephone like '%" + mobile + "%' ";
					countSql += " and u.mobilephone like '%" + mobile + "%' ";
				}
				
			}
			if (!StringUtils.isEmpty(name)) {
				searchSql += " and u.fullname like '%" + name + "%' ";
				countSql += " and u.fullname like '%" + name + "%' ";
			}

			//searchSql += " order by b.orgname desc";
			//countSql += " order by b.orgname desc";

			ParameterizedRowMapper<Map<String, String>> searchMapper = new ParameterizedRowMapper<Map<String, String>>() {
				public Map<String, String> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Map<String, String> map = new HashMap<String, String>();
					map.put("NAME", rs.getString("name"));       //人员名字
					map.put("USERID", rs.getString("userId"));   //人员ID
					map.put("MOBILE", rs.getString("mobile"));   //人员的私人电话
					map.put("ORGNAME", rs.getString("orgName")); //人员所属的单位
					map.put("ACCOUNT", rs.getString("account"));//警号      //
					map.put("UNITTEL", rs.getString("unitTel"));//办公电话   //
					map.put("POLICEPHONE", rs.getString("policePhone"));//警务通号码  //
					map.put("DUTY", rs.getString("duty"));    //职务   //
					list.add(map);
					return map;
				}
			};

			int totalSize = this.getJdbcTemplate().queryForInt(countSql);// 总记录条数
			int pageNum = request.getPageNum();// 当前查询页码
			int pageSize = request.getPageSize();// CommonTool.pagesize;//每页显示记录条数
			int totalPage = CommonTool.getTotalPage(totalSize, pageSize,
					pageNum);// 总页数

			String sql = CommonTool.getPageSql(searchSql, pageNum, totalPage,
					totalSize, pageSize);

			this.getJdbcTemplate().query(sql, searchMapper);

			response.setPageNum(pageNum);
			response.setTotalNum(totalSize);
			response.setTotalPage(totalPage);
			response.setPageSize(pageSize);

		} else{
			String orgSql = SQL_OA_JW_UNIT;
			if (!StringUtils.isEmpty(unitName)) {
				orgSql += " and u.orgname like '%" + unitName + "%' ";//单位待定				
			}else{
				orgSql += " and u.suporgid='590' ";
			}
			ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {
				public Map<String, String> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Map<String, String> map = new HashMap<String, String>();
					map.put("DEPTID", rs.getString("deptId")); //单位ID
					map.put("UNIT_NAME", rs.getString("unit_name")); //单位名称
					list.add(map);
					return map;
				}
			};
			
			this.getJdbcTemplate().query(orgSql, mapper);
			int totalPage=1;//由于搜索机构没有分页，所以将总页面设置为1
			response.setTotalPage(totalPage);
		}
		
		
		response.setList(list);
		return response;
	}

	/**
	 * 查询人员信息信息（含分页）
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	//三级科室和人员显示
	private final String SOL_OA_ORG_USER1="select b.orgname unit_name,b.orgid deptid,a.fullname name,a.username userid,a.mobilephone mobile,a.orgname,a.userno account,a.telephone unitTel,a.policePhone,a.titleofpost duty from (  " 
		+"select u.username,u.fullname,u.userno,u.telephone,u.mobilephone,u1.orgname,u.titleofpost,u.policephone,u.reg_runid from jwt_conf.sys_user u ,jwt_conf.sys_org u1 where u.reg_runid=u1.orgid and u.reg_runid=? and "
		+"u.userno not in ('160001','160008','160003','160005','160011','160012','160033','001870','160039','162162','160009','160398','160023') or u.userno is null ) a full join "
		+"(select t.orgname,t.orgid from jwt_conf.sys_org t where t.suporgid=?) b on a.reg_runid=b.orgid order by b.orgname ";
	
	private final String SOL_OA_ORG_USER1_COUNT="select count(*) from (  " 
		+"select u.username,u.fullname,u.userno,u.telephone,u.mobilephone,u1.orgname,u.titleofpost,u.policephone,u.reg_runid from jwt_conf.sys_user u ,jwt_conf.sys_org u1 where u.reg_runid=u1.orgid and u.reg_runid=? and "
		+"u.userno not in ('160001','160008','160003','160005','160011','160012','160033','001870','160039','162162','160009','160398','160023') or u.userno is null ) a full join "
		+"(select t.orgname,t.orgid from jwt_conf.sys_org t where t.suporgid=?) b on a.reg_runid=b.orgid order by b.orgname  ";
	
	public QueryEnvelop getEmployeeByDepId(QueryEnvelop request) throws Exception {
		QueryEnvelop response = new QueryEnvelop(); 
		
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();	
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {
			public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String, String> map = new HashMap<String, String>();
				map.put("UNIT_NAME", rs.getString("unit_name")); //单位名称
				map.put("DEPTID", rs.getString("deptId")); 		 //单位ID
				map.put("NAME", rs.getString("name"));       //人员名字
				map.put("USERID", rs.getString("userId"));   //人员ID
				map.put("MOBILE", rs.getString("mobile"));   //人员的私人电话
				map.put("ORGNAME", rs.getString("orgName")); //人员所属的单位
				map.put("ACCOUNT", rs.getString("account"));//警号      //
				map.put("UNITTEL", rs.getString("unitTel"));//办公电话   //
				map.put("POLICEPHONE", rs.getString("policePhone"));//警务通号码  //
				map.put("DUTY", rs.getString("duty"));    //职务   //
				list.add(map);
				return map;
			}
		};
		
		Map<String, String> condition = request.getCondition();
		String deptid = condition.get("deptId");
		//String userid = condition.get("userId");
		
		if (!StringUtils.isEmpty(deptid)) {
			CustomerContextHolder.setCustomerType(CustomerType.valueOf("bizDS"));
			int totalSize = this.getJdbcTemplate().queryForInt(
					SOL_OA_ORG_USER1_COUNT, new Object[] { deptid,deptid });// 总记录条数
			int pageNum = request.getPageNum();// 当前查询页码
			int pageSize = request.getPageSize();// CommonTool.pagesize;//每页显示记录条数
			int totalPage = CommonTool.getTotalPage(totalSize, pageSize, pageNum);// 总页数

			String sql = CommonTool.getPageSql(SOL_OA_ORG_USER1, pageNum,totalPage, totalSize, pageSize);

			SSILogger.Bizlogger.debug(SOL_OA_ORG_USER1);
			this.getJdbcTemplate().query(sql, new Object[] { deptid,deptid }, mapper);
			
			response.setPageNum(pageNum);
			response.setTotalNum(totalSize);
			response.setTotalPage(totalPage);
			response.setPageSize(pageSize);
		}

		response.setList(list);

		return response;
	}

}
