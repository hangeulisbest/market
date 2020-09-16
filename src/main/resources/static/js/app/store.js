var store_main = {
    init : function (){
        var _this = this;

        $('#btn-store-save').on('click',function () {
            _this.store_save();
        });

        $('#btn-store-search').on('click',function () {
            _this.store_search(0);
        });

        $('#btn-store-update').on('click',function () {
            _this.store_update();
        })
    },

    store_save : function (){
        var data = {
            name : $('#name').val(),
            city : $('#city').val(),
            street : $('#street').val(),
            zipcode : $('#zipcode').val()
        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/stores',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('가게가 등록되었습니다.');
            window.location.href='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    store_search : function (curpage){
        var data = {
            storeName : $('#storeName').val(),
            categoryName : $('#categoryName').val()
        };

        $.ajax({
            type : 'POST',
            url : '/api/v5/stores?page='+curpage,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (data) {
            var template = "            {{#content}}\n" +
                "            <tr>\n" +
                "                <td><a href=\"/store/update/{{id}}\">{{id}}</a></td>\n" +
                "                <td>{{name}}</td>\n" +
                "                <td>{{city}}</td>\n" +
                "                <td>{{street}}</td>\n" +
                "                <td>{{zipcode}}</td>\n" +
                "            </tr>\n" +
                "            {{/content}}";
            var html =  Mustache.render(template, {content: data.content});
            $("#tbody").html(html);

            var pageArray = [];
            for(var i =1;i<data.totalPages+1;i++){
                pageArray.push(i);
            }
            var pageTemplate = "{{#pageArray}}<button type=\"button\" class=\"btn btn-default\" onclick=\"store_main.store_search({{.}}-1)\">{{.}}</button>{{/pageArray}}";
            var pageHtml = Mustache.render(pageTemplate,{pageArray : pageArray});
            $('#paging').html(pageHtml);
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },

    store_update : function (){
        var menu = []

        var names = $('.menu-name');
        var prices = $('.menu-price');

        for(var i =0; i<names.length;i++){
            menu.push({
                "name" : $(names[i]).val(),
                "price" : $(prices[i]).val()
            })
        }
        var data = {
            categoryName : $('#categoryName').val(),
            menuList : menu
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url : '/api/v1/stores/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('가게가 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    },

    store_update_menu_add : function (){
        var template = "                <div class=\"form-group\">\n" +
            "                    <label for=\"exampleInputPassword1\">가게 메뉴 추가</label>\n" +
            "                    <input type=\"text\" class=\"form-control menu-name\">\n" +
            "                    <input type=\"number\" class=\"form-control menu-price\">\n" +
            "                </div>";

        var num = $('#menu-number').val();

        $('#menu-box').empty();
        for(var i =0;i<num;i++){
            $('#menu-box').append(template);
        }
    }
};

store_main.init();