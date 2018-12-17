$(function() {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method:'get',
                url:"/hotel/room/order/list",
                iconSize:'outline',
                toolbar: '#exampleToolbar',
                striped: true,
                dataType: "json",
                pagination:true,
                singleSelect:true,
                pageSize:10,
                pageNumber:1,
                showColumns:false,
                sidePagination:"server",
                queryParamsType:"",
                queryParams:function (params) {
                    return{
                      pageNumber:params.pageNumber,
                      pageSize:params.pageSize,
                      id:$('#id').val(),
                    };
                },
                responseHandler : function(res){
                    return {
                        "total": res.data.total,//总数
                        "rows": res.data.records   //数据
                    };
                },
                columns:[
                    {
                        field : 'id',
                        title : '编号'
                    },
                    {
                        field : 'roomName',
                        title : '房间'
                    },
                    {
                        field : 'customerName',
                        title : '客户姓名'
                    },
                    {
                        field : 'checkInDate',
                        title : '入住日期'
                    },
                    {
                        field : 'checkOutDate',
                        title : '离店日期'
                    },
                    {
                        field : 'expectPrice',
                        title : '应收',
                        align : 'center',
                        formatter:function (value,row,index) {
                            return (row.expectPrice/100).toFixed(2);
                        }
                    },
                    {
                        field : 'netPrice',
                        title : '实收',
                        align : 'center',
                        formatter:function (value,row,index) {
                            return (row.netPrice/100).toFixed(2);
                        }
                    },
                    {
                        field : 'foregift',
                        title : '押金',
                        align : 'center',
                        formatter:function (value,row,index) {
                            return (row.foregift/100).toFixed(2);
                        }
                    },
                    {
                        field : 'status',
                        title : '状态',
                        align : 'center',
                        formatter:function (value,row,index) {
                            if(row.status == 0){
                                return "未完成";
                            }
                            if(row.status == 1){
                                return "已完成";
                            }
                            if(row.status == 2){
                                return "换房";
                            }
                        }
                    }
                ]

        });
}

function reLoad() {
    var opt = {
        query : {
            id : $('#id').val()
        }
    }
    $('#exampleTable').bootstrapTable('refresh', opt);
}