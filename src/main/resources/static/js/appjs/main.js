room = {};

var app = new Vue({
    el: '#app',
    data: {
        total: 0,
        rooms: '',
        pageInfo : {},
        pageNumber:1,
        pageSize: 12,
    },
    methods: {
        getData: function () {
            $.getJSON("/hotel/room/listBook", {
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
                type : 2,
                title : '预定',
                maxmin : true,
                shadeClose : false, // 点击遮罩关闭层
                area : [ '800px', '520px' ],
                content : 'hotel/room/book/'+id
            });
        }
    },
    created: function () {
        this.getData();
    }


});

function reLoad() {
    app.getData();
}