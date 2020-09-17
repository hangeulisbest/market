var order_main = {
    init : function (){
      var _this = this;

      $('#btn-order-save').on('click',function (){
         _this.order_save();
      });

      $('#btn-order-search').on('click',function (){
          _this.order_search(0);
      });

      $('#btn-order-delivery-compl').on('click',function (){
          _this.order_delivery_compl();
      });

      $('#btn-order-cancel').on('click',function (){
          _this.order_cancel();
      })

    },
    order_save : function (){
        var ordermenu = [];
        var menuid = $('.menu-id');
        var menucount = $('.menu-count');

        for(var i =0;i<menuid.length ;i++){
            ordermenu.push({
                "menuId" : $(menuid[i]).val(),
                "count" : $(menucount[i]).val()
            })
        }

        var data = {
            userId : $('#userId').val(),
            deliveryCity : $('#city').val(),
            deliveryStreet : $('#street').val(),
            deliveryZipcode : $('#zipcode').val(),
            orderMenuList : ordermenu
        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/orders',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('주문이 등록되었습니다.');
            window.location.href='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });


    },
    order_search : function (curpage){
        var data = {
            userId : $('#userId').val(),
            orderStatus : $('#orderStatus').val()
        };

        console.log(data.orderStatus);

        $.ajax({
            type : 'POST',
            url : '/api/v3/orders?page='+curpage,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function (data) {
            var template = "            {{#content}}\n" +
                "            <tr>\n" +
                "                <td><a href=\"/order/update/{{orderId}}\">{{orderId}}</a></td>\n" +
                "                <td>{{userId}}</td>\n" +
                "                <td>{{userName}}</td>\n" +
                "                <td>{{orderDate}}</td>\n" +
                "                <td>{{deliveryStatus}}</td>\n" +
                "                <td>{{orderStatus}}</td>\n" +
                "            </tr>\n" +
                "            {{/content}}";
            var html =  Mustache.render(template, {content: data.content});
            $("#tbody").html(html);

            var pageArray = [];
            for(var i =1;i<data.totalPages+1;i++){
                pageArray.push(i);
            }
            var pageTemplate = "{{#pageArray}}<button type=\"button\" class=\"btn btn-default\" onclick=\"order_main.order_search({{.}}-1)\">{{.}}</button>{{/pageArray}}";
            var pageHtml = Mustache.render(pageTemplate,{pageArray : pageArray});
            $('#paging').html(pageHtml);
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },

    order_delivery_compl : function (){
        var id = $('#orderId').val();

        $.ajax({
            type : 'PUT',
            url : '/api/v1/orders/'+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function () {
            alert('배달 완료 처리 하였습니다.');
            window.location.href='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });


    },

    order_cancel : function (){
        var id = $('#orderId').val();

        $.ajax({
            type : 'DELETE',
            url : '/api/v1/orders/'+id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function () {
            alert('주문 취소 하였습니다.');
            window.location.href='/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    order_menu_add : function (){
        var template = "                <div class=\"form-group\">\n" +
            "                    <label for=\"exampleInputPassword1\">주문 메뉴 추가(메뉴 아이디,개수)</label>\n" +
            "                    <input type=\"number\" class=\"form-control menu-id\">\n" +
            "                    <input type=\"number\" class=\"form-control menu-count\">\n" +
            "                </div>";

        var num = $('#menu-number').val();

        $('#menu-box').empty();
        for(var i =0;i<num;i++){
            $('#menu-box').append(template);
        }
    }
}

order_main.init();