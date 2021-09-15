<html>
<head>
    <meta charset="utf-8">
    <title>错误提示</title>
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.0.1/css/bootstrap.css" rel="stylesheet">

    <script src="../../static/js/jquery-3.2.1.js" type="text/javascript" ></script>
    <script src="../../static/js/time.js" type="text/javascript" ></script>
</head>

<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    <#--取消订单失败 !${msg}-->
                    操作失败!${msg}
                </h4>
                <a href="#" class="alert-link" style='text-decoration:none;'>3</a><strong><span>后自动跳转</span></strong>
                <input type="hidden" value="${url}" id="hiddenUrl">
            </div>
        </div>
    </div>
</div>
</body>
</html>
