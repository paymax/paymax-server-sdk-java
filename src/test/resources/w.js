/**
 * Created by CJ on 14/06/2017.
 */


var abc = function () {
    function onBridgeReady() {
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest',
            {},
            function (res) {
                if (res.err_msg == "get_brand_wcpay_request：ok") {
                }
            }
        );
    }

    if (typeof WeixinJSBridge == "undefined") {
        if (document.addEventListener) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    } else {
        onBridgeReady();
    }
};
abc();