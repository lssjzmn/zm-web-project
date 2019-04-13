var loginUrl = "/entry/requestLogin.html";

var loginEvent = function () {
    $.ajax({
        url: loginUrl,
        type: "get",
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            var ret = data.info;
            if (ret === "login success.") {
                window.location.href = "/entry/welcome/10086.html";
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert("登录请求失败！");
        }
    });
}