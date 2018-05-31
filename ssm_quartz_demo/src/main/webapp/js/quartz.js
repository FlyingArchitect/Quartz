$(document).ready(function(){
	getList();
	$("#div")
	.on("click","#modfiy",modfiy)
	.on("click","#pause",pause)
	.on("click","#resume",resume)
	.on("click","#delete",deleteJob)
	.on("click","#add",add)
})
function getList(){
	var url="getList";
	$.getJSON(url,function(data){
		setTableBody(data);
	})
}
function setTableBody(data){
	var tBody = $("#tbodyid");
	tBody.empty();
	for(var i in data){
		var tr = $("<tr></tr>");
		var tds = 
			"<td>"+data[i].clz+"</td>"+
			"<td>"+data[i].jobName+"</td>"+
			"<td>"+data[i].jobGroupName+"</td>"+
			"<td>"+data[i].triggerName+"</td>"+
			"<td>"+data[i].triggerGroupName+"</td>"+
			"<td>"+data[i].cron+"</td>"+
			"<td>" +
			"<button type='button' id='pause' >暂停</button>" +
			"<button type='button' id='modfiy'>修改</button>" +
			"<button type='button' id='resume'>重启</button>" +
			"<button type='button' id='delete'>删除</button>" +
			"</td>";
		tr.append(tds);
		tBody.append(tr);
	}
}

function modfiy(){
	//修改用的添加的jsp，在添加的service中，有简单的对添加和修改的判断
	window.location.href="toAdd"
	
}
function pause(){
	var jobName =$(this).parent().siblings().eq(1).html();
	var jobGroupName = $(this).parent().siblings().eq(2).html();
	var JobDetailPo={"jobName":jobName,"jobGroupName":jobGroupName};
	var url='pause';
	$.post(url,JobDetailPo,function(data){
		if(data){
			getList();
		}
	})

}
function resume(){
	var jobName =$(this).parent().siblings().eq(1).html();
	var jobGroupName = $(this).parent().siblings().eq(2).html();
	var JobDetailPo={"jobName":jobName,"jobGroupName":jobGroupName};
	var url='resume';
	$.post(url,JobDetailPo,function(data){
		if(data){
			getList();
		}
	})
}
function deleteJob(){
	var jobName =$(this).parent().siblings().eq(1).html();
	var jobGroupName = $(this).parent().siblings().eq(2).html();
	var JobDetailPo={"jobName":jobName,"jobGroupName":jobGroupName};
	var url='delete';
	$.post(url,JobDetailPo,function(data){
		if(data){
			getList();
		}
	})
}
function add(){
	window.location.href="toAdd"
}
