//-------------------【定义ztree开始】----------------------------------------------------------------------		
	var zTree1;
	var setting;
	setting = {
		checkable: false,
		async: true,
		asyncUrl: webroot+"/xajw/jwRoleAC!queryJwRoleTreeNodes.action",  //获取节点数据的URL地址
		asyncParam: ["name", "id"],  //获取节点数据时，必须的数据名称，例如：id、name
		callback:{
			click:	zTreeOnClick
		}
	};	
	var zNodes =[];
	//整个树加载
	$(document).ready(function(){
		refreshTree();
	});
	//整个树刷新
	function refreshTree(){
	//alert("1");alert("setting"+setting);
		zTree1 = $("#unittree").zTree(setting, zNodes);
	}
	//选中节点刷新
	function refreshNode(){
		var treeNode = getSelectNode();
		if(!treeNode){
			refreshTree();
		}else{
			zTree1.reAsyncChildNodes(treeNode, "refresh");
		}
	}
	//获取选中节点
	function getSelectNode(){
		return zTree1.getSelectedNode();
	}
	//获取选中节点id
	function getSelectNodeId(){
		if(!getSelectNode())return null;
		else return getSelectNode().id;
	}
	//节点单击事件
	function zTreeOnClick(event, treeId, treeNode) {
	}
	
	var parent=this.dialogArguments;
	function _btnSubmit(){
  		var tmp = zTree1.getCheckedNodes(true);
		var unitIDstr = "";
		var unitNamestr = "";
		for (var i=0; i<tmp.length; i++) {
			if(tmp[i].checkedOld != tmp[i].checked){
				unitIDstr+= tmp[i].id+",";
				unitNamestr+= tmp[i].name+ ",";
			}
		}
		
  		parent.setColumns(getSelectNodeId(),type);
  		window.close();
  	}