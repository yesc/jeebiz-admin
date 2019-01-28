/**
 * 用户管理功能
 */
layui.define([ 'table', 'form' ], function(exports) {
					
	var $ = layui.$, setter = layui.setter, admin = layui.admin, table = layui.table, form = layui.form;

	// 用户管理
	table.render({
		elem 		: '#LAY-user-list',
		url 		: setter.prefix + 'authz/user/list', // 用户查询接口
		contentType	: "application/json",
		page		: true,
	    height		: 'full-220',
	    limit		: 30,
	    method		: 'POST', //如果无需自定义HTTP类型，可不加该参数
	    request		: {
	    	pageName: 'pageNo', //页码的参数名称，默认：page
	    	limitName: 'limit' //每页数据量的参数名，默认：limit
	    },
	    response	: {
	    	countName: 'total', //规定数据总数的字段名称，默认：count
	    	dataName: 'rows', //规定数据列表的字段名称，默认：data
	        statusName: 'code', //规定数据状态的字段名称，默认：code
	        statusCode: 200, //规定成功的状态码，默认：0
	        msgName: 'message' //规定状态信息的字段名称，默认：msg
	    }, 
		cols 		: [ [ 
			{ type: 'checkbox', fixed : 'left' },  
			{ field: 'username', title : '用户名', minWidth : 100 }, 
			{ field: 'alias', title : '用户别名' }, 
			{ field: 'avatar', title : '头像', width: 100, templet: '#imgTpl' }, 
			{ field: 'phone', title: '手机'},
			{ field: 'email', title: '邮箱'},
			{ field: 'roleName', title: '已分配角色', minWidth : 150},
			{ field: 'gender', width: 80, title: '性别', templet: '#genderTpl'},
	        { field : 'status', title : '用户状态', unresize: true, minWidth : 80, align : 'center', templet: '#switchTpl'}, 
			{ title : '操作', minWidth : 150, align : 'center', fixed : 'right', toolbar : '#table-opt-list'}
		]],
	    skin: 'line'
	});
	
	//监听状态操作
	form.on('switch(statusKv)', function(obj){
		//layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
		var value = this.value, //得到修改后的值
			id = $(obj.elem).data("id");
	    // 提交更新
  		admin.req({
	        url			: setter.prefix + 'authz/user/status',
	        type 		: "post",
	        data		: {
	        	"id" 	: id,
   				"status": obj.elem.checked ? "1" : "0"
			},
	        success		: function(res){
	        	if(res.status == 'success'){
	        		layer.msg(res["message"]||"", {
	                   	icon: 1
	            	});
	        	} else {
	        		layer.msg(res["message"]||"", {
	                   	icon: 2
	            	});
	        	}
	        }
	    });
	});
	
	//监听单元格编辑
	table.on('renew(LAY-user-list)', function(obj){
		var value = obj.value, //得到修改后的值
			data = obj.data,   //得到所在行所有键值
			field = obj.field; //得到字段
		var rqData = {
        	"id" 	: data.id,
	   		"label" : field == "label" ? value : data.label,
	   		"key" 	: data.key,
	   		"value"	: field == "value" ? value : data.value,
			"status": data.status
		};
		//console.log(data);
		//console.log(rqData);
		// 提交更新
  		admin.req({
	        url			: setter.prefix + 'authz/user/update',
	        type 		: "POST",
	        contentType	: "application/json",
	        data		: JSON.stringify(rqData),
	        success		: function(res){
	        	if(res.status == 'success'){
	        		layer.msg(res["message"]||"", {
	                   	icon: 1
	            	});
	        	} else {
	        		layer.msg(res["message"]||"", {
	                   	icon: 2
	            	});
	        	}
	        }
	    });
		
	});
	
	//监听行工具事件
	table.on('tool(LAY-user-list)', function(obj){
	    var data = obj.data;
	    var id = data.id;
	    //console.log(obj)
	    if(obj.event === 'del'){
	    	/*layer.prompt({
		        formType: 1,
		        title	: '敏感操作，请验证口令'
		    }, function(value, index){
		        layer.close(index);
		        
		    });*/
	    	layer.confirm('您正在删除系统重要数据，确定继续操作吗？', function(index){
		      	admin.req({
		  	        url			: setter.prefix + 'authz/user/delete',
		  	        type 		: "post",
		  	        data		: {"ids" : id},
		  	        success		: function(res){
		  	        	if(res.status == 'success'){
		  	        		layer.msg(res["message"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        		table.reload('LAY-user-list'); //刷新表格
		  	        	} else {
		  	        		layer.msg(res["message"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        	}
		  	        	layer.close(index);
		  	        }
		      	});
	        });
	    	 
	    } else if(obj.event === 'renew'){
	    	layer.open({ 
    			type	: 2,
    			title	: '编辑用户',
    			content	: setter.prefix + 'authz/user/ui/renew/' + id,
    			area	: ['700px', '600px'],
    			btn		: ['确定', '取消'],
    			yes		: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-user-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'authz/user/renew',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		table.reload('LAY-user-list'); //刷新表格
		    		              	layer.close(index); //关闭弹层
		        	        	} else {
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 2
		        	            	});
		        	        	}
		        	        }
		        	    });
		            });  
		            
		            submit.trigger('click');
    			}
	        }); 
	    }
	});
	
	//事件
    var active = {
		batchdel: function(){
    		var checkStatus = table.checkStatus('LAY-user-list'),
    			checkData = checkStatus.data; //得到选中的数据
	        if(checkData.length === 0){
	        	return layer.msg('请选择数据');
	        }
	        layer.confirm('您正在删除系统重要数据，确定继续操作吗？', function(){
	      	  	var ids = [];
	      	  	for( i in checkData){
	      	  		ids.push(checkData[i].id);
	      	  	}
		      	admin.req({
		  	        url			: setter.prefix + 'authz/user/delete',
		  	        type 		: "post",
		  	        data		: {"ids" : ids.join(",")},
		  	        success		: function(res){
		  	        	if(res.status == 'success'){
		  	        		layer.msg(res["message"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        		table.reload('LAY-datasource-list'); //刷新表格
		  	        	} else {
		  	        		layer.msg(res["message"]||"", {
		  	                   	icon: 2
		  	            	});
		  	        	}
		  	        }
		      	});
	        });
	        
    	},
    	add: function(){
    		layer.open({ 
    			type	: 2,
    			title	: '添加用户',
    			content	: setter.prefix + 'authz/user/ui/new',
    			area	: ['700px', '600px'],
    			btn		: ['确定', '取消'],
    			yes		: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index],
		            	submitID = 'LAY-user-submit',
		            	submit = layero.find('iframe').contents().find('#'+ submitID);
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	var field = data.field; //获取提交的字段
		            	// 提交更新
		          		admin.req({
		        	        url			: setter.prefix + 'authz/user/new',
		        	        type 		: "post",
		        	        contentType	: "application/json",
		        	        dataType	: "json",
		        	        data		: JSON.stringify(field),
		        	        success		: function(res){
		        	        	if(res.status == 'success'){
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 1
		        	            	});
		        	        		table.reload('LAY-datasource-list'); //刷新表格
		    		              	layer.close(index); //关闭弹层
		        	        	} else {
		        	        		layer.msg(res["message"]||"", {
		        	                   	icon: 2
		        	            	});
		        	        	}
		        	        }
		        	    });
		            });  
		            
		            submit.trigger('click');
    			}
	        }); 
    	}
    }
	
	$('.layui-btn.layuiadmin-btn-admin').on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
    });

	exports('user', {})
});