var app = new Vue({
    el: '#app',
    data: {
        name: '',
        bookDate: '',
        total: 0,
        rooms: '',
        pageInfo: {},
        pageNumber: 1,
        pageSize: 12,
    },
    methods: {
        getData: function () {
            this.bookDate = $("#bookDate").val();
            $.getJSON("/hotel/room/book/list", {
                name: this.name,
                bookDate: this.bookDate,
                pageNumber: this.pageNumber,
                pageSize: this.pageSize
            }, function (r) {
                app.total = r.data.total;
                app.rooms = r.data.records;
                app.pageInfo = r.data;
                app.page();
            });
        },
        page: function () {
            var options = {
                currentPage: app.pageInfo.current, //当前页
                totalPages: app.pageInfo.pages, //总页数
                numberofPages: 4, //显示的页数
                bootstrapMajorVersion: 3,
                alignment: 'center',
                size: 'large',
                shouldShowPage: true,
                itemTexts: function (type, page, current) { //修改显示文字
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "上一页";
                        case "next":
                            return "下一页";
                        case "last":
                            return "尾页";
                        case "page":
                            return page;
                    }
                },
                onPageClicked: function (event, originalEvent, type, page) {
                    app.pageNumber = page;
                    app.getData();
                }
            };
            $('#page').bootstrapPaginator(options);
        },
        reserve: function (id) {
            layer.open({
                type: 2,
                title: '预定',
                maxmin: true,
                shadeClose: false, // 点击遮罩关闭层
                area: ['800px', '520px'],
                content: 'hotel/room/book/get/' + id
            });
        },
        open:function (id) {
            layer.open({
                type: 2,
                title: '入住',
                maxmin: true,
                shadeClose: false, // 点击遮罩关闭层
                area: ['800px', '520px'],
                content: 'hotel/room/book/open/' + id
            });
        },
        change:function(id) {
            layer.open({
                type: 2,
                title: '换房',
                maxmin: true,
                shadeClose: false, // 点击遮罩关闭层
                area: ['800px', '520px'],
                content: 'hotel/room/book/change/' + id
            });
        },
        checkOut:function(id){
            layer.open({
                type: 2,
                title: '退房',
                maxmin: true,
                shadeClose: false, // 点击遮罩关闭层
                area: ['800px', '520px'],
                content: 'hotel/room/book/checkOut/' + id
            });
        },
        clear:function(id){
            layer.confirm("确认房间清扫完毕？", {
                btn: ['确认', '取消']
            }, function () {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: "/hotel/room/book/clear/" + id,
                    async: false,
                    error: function (request) {
                        alert("Connection error");
                    },
                    success: function (data) {
                        if (data.code == 0) {
                            layer.msg(data.msg)
                            reLoad();
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }, function () {
                //
            })
        },
        noshow: function (id) {
            layer.confirm("确认客人未抵达？", {
                btn: ['确认', '取消']
            }, function () {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: "/hotel/room/book/noshow/" + id,
                    async: false,
                    error: function (request) {
                        alert("Connection error");
                    },
                    success: function (data) {
                        if (data.code == 0) {
                            layer.msg(data.msg)
                            reLoad();
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }, function () {
                //
            })
        }
    },
    created: function () {
        this.getData();
    },
    mounted:function () {
        $("#bookDate").val(moment().format("YYYY-MM-DD"));
    }
});

function reLoad() {
    app.getData();
}