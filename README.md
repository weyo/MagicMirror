MagicMirror 魔镜
===========

![logo](pics/logo_git_o.png)

![v0.2](pics/v0.2.png)


## 说明

本项目为私人定制版魔镜。与[原版](https://github.com/MichMich/MagicMirror)的主要区别有：

- 基于 Java Servlet 重构，全面修改默认程序环境为中文，包括：
	- 语言：中文
	- 时间：24小时制
	- 单位：公制
	- RSS：新浪财经新闻

- 增加人工智能助手功能<sup>beta</sup>

- 增加语音识别功能（暂只支持 Android 平台）

- 增加实时空气质量展示

- 调整配置文件上传方式

- 取消版本自动更新功能

<img src="pics/v0.3.png" width = "360" height = "476" alt="pic3" align=center />

## 使用方法

1. 将本项目 clone 到本地，在 IDE 中导入其中的 `server` 目录；

2. 将配置文件 `server/src/main/webapp/resources/js/config-default.js` 的文件名修改为 `config.js`，或者复制该文件，将新文件重命名为 `config.js`；

3. 在 `config.js` 中根据个人需要修改以下配置信息：
	- 时间格式
	- 所在城市
	- APPID（在 OpenWeatherMap 网站上注册用户获取免费的 APPID）
	- AppKey（在 [PM25.in](http://pm25.in/api_doc) 网站上申请的 AppKey）
	- Key（在 [图灵机器人](http://www.tuling123.com) 网站上申请的 Key）
	- 欢迎词
	- 最大日程数量及个人日程表链接（iPhone 可以在 iCloud 中将某个日历设为公开日历得到）
	- 新闻 RSS （可自行搜索可用的新闻 RSS）
	- ...

4. 根据个人爱好修改 AI 及用户头像；

5. 编译后台程序（本项目 bin 目录中提供有预编译版本 `server.war`，该 war 包移除了 `config.js`，需要按照步骤2、3手动配置），将生成的 war 包放入 Tomcat 的 webapps 目录下，执行 Tomcat 的 bin 目录下的 startup.sh 启动 Tomcat 服务器。

6. 启动魔小镜 APP 进行语音输入。

>完整的软硬件配置说明请参考本人博客说明：
>
>- [魔镜制作——硬件篇](http://weyo.me/pages/techs/magic-mirror-hardware/)
>- [魔镜制作——软件篇](http://weyo.me/pages/techs/magic-mirror-software/)

<img src="pics/screenshot.jpg" width = "360" height = "640" alt="pic4" align=center />

## 其他说明

1. APP 源码（`android` 目录）中移除了编译所需的讯飞 SDK，如果需要在 Android Studio 中导入项目，需要手动建立 `android/app/libs` 目录（并在其中放入讯飞 SDK 提供的 `Msc.jar` 和 `Sunflower.jar`）和 `android/app/src/main/jniLibs` 目录（并在其中放入讯飞 SDK 提供的 .so 文件），同时需要将 `android/app/src/main/AndroidManifest.xml` 和 `android/app/src/main/res/values/strings.xml` 中的 appkey 修改为在讯飞开放平台申请的 appkey；

2. 由于某些众所周知的原因，日历功能暂只支持 iPhone，而且功能不是很稳定；

3. 默认关闭空气质量功能（减少对 PM25.in 网站的无效请求），需要开启请向 [PM25.in](http://pm25.in/api_doc) 网站申请 AppKey 并谨慎使用；

4. 由于天气数据来自国外的 OpenWeatherMap 网站，部分地区数据可能不够准确，请以 CCTV 天气预报或手机上其他天气 APP 为准（要你何用 - =；

5. 语音识别功能暂只支持 Android 手机；

6. 除了 APP 提供的实时语音识别功能，另外提供了手工访问接口（访问 http://<服务器IP>:8080/server/talk.html 网页），可以手动输入和 AI 对话。

## 鸣谢

感谢以下单位为本项目提供的支持（排名不分先后）：

- [OpenWeatherMap](http://openweathermap.org/)
- [PM25.in](http://pm25.in/api_doc)
- [图灵机器人](http://www.tuling123.com)
- [讯飞开放平台](http://www.xfyun.cn/)

