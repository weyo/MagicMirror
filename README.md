MagicMirror 魔镜
===========

![v0.1](images/v0.1.png)

## 说明

本项目为个人定制的中文版魔镜。与[原版](https://github.com/MichMich/MagicMirror)的主要区别有：

- 全面修改默认程序环境为中文，包括：
	- 语言：中文
	- 时间：24小时制
	- 单位：公制
	- RSS：新浪财经新闻

- 增加人工智能助手功能<sup>beta</sup>

- 增加语音识别功能<sup>pre-alpha</sup>

- 增加实时空气质量展示<sup>beta</sup>

- 调整配置文件上传方式

- 基于 Java 重构，删除版本自动更新功能

## 使用方法

1. 将本项目 clone 到本地；

2. 将配置文件 `js/config-default.js` 的文件名修改为 `config.js`，或者复制该文件，将新文件重命名为 `config.js`；

3. 在 `config.js` 中根据个人需要修改以下配置信息：
	- 时间格式
	- 所在城市
	- APPID（在 OpenWeatherMap 网站上注册用户获取免费的 APPID）
	- AppKey（在 [PM25.in](http://pm25.in/api_doc) 网站上申请的 AppKey）
	- 欢迎词
	- 最大日程数量及个人日程表链接（iPhone 可以在 iCloud 中将某个日历设为公开日历得到）
	- 新闻 RSS （可自行搜索可用的新闻 RSS）

4. 编译后台程序（本项目 bin 目录中提供有预编译版本）并启动 Tomcat 服务器。

>完整的软硬件配置说明参考本人博客说明

## 鸣谢

---

>Updating...