# paymax-server-sdk-java

这是Paymax提供的Java语言的Server SDK, 用于帮助用户对接[Paymax API](https://github.com/paymax/paymax-doc/blob/master/API%E6%96%87%E6%A1%A3.md)。

1. 通过以下三个类,分别对接支付、退款和对账单下载。

	* [`Charge`](src/main/java/com/paymax/model/Charge.java)   用于发起支付订单和查询支付订单信息
	* [`Refund`](src/main/java/com/paymax/model/Refund.java)  用于发起退款订单和查询退款订单信息
	* [`StatementDownload`](src/main/java/com/paymax/model/StatementDownload.java)  用于发起对账单下载请求和接收返回信息

2. 我们为您写好了相应的示例, 参见以下三个类:

	* [`ChargeExample`](src/main/java/com/paymax/example/ChargeExample.java) 支付
	* [`RefundExample`](src/main/java/com/paymax/example/RefundExample.java) 退款
	* [`StatementDownloadExample`](src/main/java/com/paymax/example/StatementDownloadExample.java) 对账单下载


3. 为了方便进行测试, 我们已经在[`SignConfig`](src/main/java/com/paymax/config/SignConfig.java)中做好了一份配置, 您可以直接执行相应的示例进行体验进行体验。

4. 在发起真正的请求之前, 需要在[`SignConfig`](src/main/java/com/paymax/config/SignConfig.java)中配置自己的私钥、商户SecretKey、Paymax公钥。




访问 [Paymax-doc](http://paymax.github.io/paymax-doc/) 获取更多信息。

Paymax官网: https://paymax.cc/
