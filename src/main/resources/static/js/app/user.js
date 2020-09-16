var user_main = {
    init : function (){
        var _this = this;

        $('#btn-user-save').on('click',function () {
            _this.user_save();
        });

        $('#btn-user-search').on('click',function () {
            _this.user_search(0);
        });

        $('#btn-user-update').on('click',function () {
            _this.user_update();
        });

        $('#btn-user-delete').on('click',function () {
            _this.user_delete();
        });

    },

    user_save : function (){
        var data = {
            name : $('#name').val(),
            city : $('#city').val(),
            street : $('#street').val(),
            zipcode : $('#zipcode').val()
        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/users',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('회원이 등록되었습니다.');
            window.location.href='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    user_search : function (curpage){
        var data = {
            name : $('#name').val(),
            city : $('#city').val(),
        };

        $.ajax({
            type : 'POST',
            url : '/api/v2/users?page='+curpage,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (data) {
            var template = "            {{#content}}\n" +
                "            <tr>\n" +
                "                <td><a href=\"/user/update/{{id}}\">{{id}}</a></td>\n" +
                "                <td>{{name}}</td>\n" +
                "                <td>{{city}}</td>\n" +
                "                <td>{{street}}</td>\n" +
                "                <td>{{zipcode}}</td>\n" +
                "            </tr>\n" +
                "            {{/content}}";
            var html =  Mustache.render(template, {content: data.content});
            console.log(data.totalPages);
            $("#tbody").html(html);

            var pageArray = [];
            for(var i =1;i<data.totalPages+1;i++){
                pageArray.push(i);
            }
            var pageTemplate = "{{#pageArray}}<button type=\"button\" class=\"btn btn-default\" onclick=\"user_main.user_search({{.}}-1)\">{{.}}</button>{{/pageArray}}";
            var pageHtml = Mustache.render(pageTemplate,{pageArray : pageArray});
            $('#paging').html(pageHtml);
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },

    user_update : function (){
        var data = {
            city : $('#city').val(),
            street : $('#street').val(),
            zipcode : $('#zipcode').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url : '/api/v1/users/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('회원이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    },

    user_delete : function (){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url : '/api/v1/users/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('회원이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    }

};

user_main.init();