<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>警务用户添加</title>
    <link href="<%=path%>/platform/images/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path%>/flexgrid/lib/jquery/jquery.js"></script>
  <script type="text/javascript" src="<%=path%>/config.js"></script>
   <script type="text/javascript" src="<%=path%>/check.js"></script>
   	<script type="text/javascript" src="<%=path%>/platform/js/ssi_calender.js"></script>
   	<script type="text/javascript" src="<%=path%>/common.js"></script>
<script type="text/javascript">
var parent=this.dialogArguments;
 $(document).ready(function(){
 	getJwjzCodeList("jz","jz","jwUser.jz","100");
 	getJwRoleTypeCodeList("jw_role","jw_role","jwUser.jw_role","100");
    $("#btn1").click(function(){
    
		//给隐藏域赋值
    	$("#hid1").val(parent.getSelectNodeId());
    	
    	$("#xzqh").val(parent.getSelectNode().xzqh);
    	
    	postdata(); 
    	                                             
    });      
    $("#btn2").click(function(){
		window.close();
    });  
 }); 
function postdata(){ 
 if(!isNull($("#name").val(),"用户姓名")) {
		document.getElementById("btn1").disabled = false;
		return;
	}
	if(!lengthCheck($("#name").val(),"用户姓名",30)){
     return false;
   } 
	if(!isNull($("#account").val(),"用户账号")) {
		document.getElementById("btn1").disabled = false;
		return;
	}  
	if(!lengthCheck($("#account").val(),"用户账号",30)){
     return false;
   } 
	if(!isNull($("#psw").val(),"用户密码")) {
		document.getElementById("btn1").disabled = false;
		return;
	}
	  
	// **********************普通密码验证*******************************
	if(!lengthCheck($("#psw").val(),"用户密码",30)){
		return false;
	} 
	// **********************普通密码验证*******************************
	// **********************九点锁屏密码验证****add by 陈萧如************ 
	// 九点锁屏密码最长为9
	/*
	if(!lengthCheck($("#psw").val(),"用户密码",9)){
		return false;
	} 
	// 九点锁屏密码必须为数字
	if(!isNumberWithoutZero($("#psw").val(),"用户密码")){
		return false;
	}
	// 九点锁屏密码数字不能重复
	//if(!repeatCheck($("#psw").val())){
	//	alert("用户密码设置数字不能重复！");
	//	return false;
	//}
	*/
	// **********************九点锁屏密码验证**************************** 
	if(!isNull($("#sjhm").val(),"手机号码")) {
		document.getElementById("btn1").disabled = false;
		return;
	} 
	if(!isNumber($("#sjhm").val(),"手机号码")) {
		document.getElementById("btn1").disabled = false;
		return;
	} 
	if(!lengthCheck($("#sjhm").val(),"手机号码",30)){
     return false;
   } 
	
	if(!isNull($("#sjcm").val(),"手机串码")) {
		document.getElementById("btn1").disabled = false;
		return;
	} 
	if(!lengthCheck($("#sjcm").val(),"手机串码",30)){
     return false;
   } 
	if(!isNull($("#jmkh").val(),"加密卡号")) {
		document.getElementById("btn1").disabled = false;
		return;
	}  
	if(!lengthCheck($("#jmkh").val(),"加密卡号",30)){
     return false;
   } 
	if(!isNull($("#jz").val(),"警种")) {
		document.getElementById("btn1").disabled = false;
		return;
	}  
	
	if(!isNull($("#description").val(),"描述信息")) {
		document.getElementById("btn1").disabled = false;
		return;
	}  
    if(!lengthCheck($("#description").val(),"描述信息",300)){
     return false;
   } 
	var postData = $("#form1").serialize();  
	
		                         
   $.ajax({
		url: webroot+'/xajw/jwUserAC!addJwUser.action',
		type: 'POST',
		data:postData,
		dataType: 'json',
	    error: function(){
	    	document.getElementById("btn1").disabled = false;
			alert("添加失败");
		},
		success: function(data){
		if(data.accountInfo=="1"){
		    alert("用户账号不能重复!");
		}else{
			document.getElementById("btn1").disabled = false;
				 alert("添加成功");
				parent.refreshGrid();
	        	window.close();}
			
	    }
	});       
 }	
     
</script>
 
    
  </head>
  <body>
  	<form id="form1">
  	<br>
  	<table>
	  	<colgroup>
	  		<col width="10%" align="center"/>
	  		<col width="40%" align="right"/>
			<col width="40%" align="left" />
			<col width="10%" align="right" />
	  	</colgroup>
	  
	
	  	<tr><td></td><td><span class="xing">*</span>&nbsp;&nbsp;用户姓名：</td><td><input  type="text" id= "name" name="jwUser.name" size="50%"/></td></tr>
	  	<tr><td></td>
		  	<td>&nbsp;&nbsp;&nbsp;&nbsp;性别：</td>
		  	<td>
	  			<select id ="xb"  name="jwUser.xb" style="width:100%">
		    		<option value="F">男</option>
         	<option value="M">女</option>
	    		</select>
		  	</td>
	  	</tr>
	  	<tr><td></td><td><span class="xing">*</span>&nbsp;&nbsp;用户账号：</td><td><input  type="text" id= "account" name="jwUser.account" size="50%"/></td></tr>
	  	<tr><td></td><td><span class="xing">*</span>&nbsp;&nbsp;用户密码：</td><td><input  type="password" id= "psw" name="jwUser.psw" size="50%"/></td></tr>
	  	<tr><td></td><td><span class="xing">*</span>&nbsp;&nbsp;手机号码：</td><td><input  type="text" id= "sjhm" name="jwUser.sjhm" size="50%"/></td></tr>
	  	<tr><td></td><td><span class="xing">*</span>&nbsp;&nbsp;手机串号：</td><td><input  type="text" id ="sjcm" name="jwUser.sjcm" size="50%"/></td></tr>
	  	<tr><td></td><td><span class="xing">*</span>&nbsp;&nbsp;加密卡号：</td><td><input  type="text" id= "jmkh" name="jwUser.jmkh" size="50%"/></td></tr>
	  	<tr><td></td>
	  		<td>&nbsp;&nbsp;&nbsp;&nbsp;警务角色：</td>
	  		<td>
	  		<!--input  type="text" id= "jz" name="jwUser.jz" size="50%"/-->
	  			<div id="jw_role_div"></div>
	  		</td>
	  	</tr>
	  	<tr><td></td>
	  		<td>&nbsp;&nbsp;&nbsp;&nbsp;警种：</td>
	  		<td>
	  		<!--input  type="text" id= "jz" name="jwUser.jz" size="50%"/-->
	  			<div id="jz_div"></div>
	  		</td>
	  	</tr>
	  	<tr><td></td><td></td><td><input  type="hidden" id= "xzqh" name="jwUser.xzqh" size="50%"/></td></tr>
	  	<tr><td></td><td><span class="xing">*</span>&nbsp;&nbsp;描述信息：</td><td><textarea id= "description" name="jwUser.description" style="width:100%;" rows="9"></textarea></td></tr>
  		
	  	<tr><td></td><td></td>
	  		<td align="center"><input type="button" id="btn1"  value="保 存"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  		<input type="button" id="btn2"  value="取 消"/></td>
	  	</tr>
  	</table>
	</form>  
  </body>
</html>
