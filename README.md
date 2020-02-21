# weather forecast demo based on SOA
# 基于SOA的天气预报系统demo
This a weather forecast based on SOA, using the dubbo. 
It has some interesting functions:<br>
1.Voice Announcements of the weather<br>
2.Different weather to different music and background images<br>
3.Temperature line chart to the current city with 7 days<br>
4.A window to choose city<br>
5.Beautiful GUI<br>
This system only support Chinese due to the weather api.<br>
<p>

基于SOA的天气预报系统，它有下述的一些有趣的功能：
1. 语音播报当前天气状况
2. 不同的天气对应不同的背景图片和背景音乐
3. 7天气温折线图
4. 选择城市的窗口
5. 友好的GUI交互
## Requirements
### zoo-keeper
下载并安装 zookeeper:<br>
http://zookeeper.apache.org/releases.html#download <br>
对于zookeeper的安装，可以参考下述博客:<br>
https://www.cnblogs.com/Lzf127/p/7155316.html<br>

### Weather Api
代码基于一个免费的天气api:
https://www.tianqiapi.com/index/doc?version=v1<br>
使用该api需要先注册账号，然后修改代码文件中的 **appid** 与 **appsecret**(在代码文件JuheDemo.java)，这两个东西api网站会给给出。 <br>

### MySQL
代码使用了MySQL，建立一个简单的数据库。

### IntelliJ IDEA
代码使用IntelliJ IDEA，其他编译器并未尝试。

## Just begin
1.打开windows命令行，进入目录: zookeeper/bin,输入**zkserver.cmd**打开zookeeper注册中心.<br>
2.链接对应的包(jar)，包都已经在lib中给出<br>
3.使用MySQl执行给出的sql文件,文件名为city.sql，创建相应的数据库和数据表，更改database类的用户名和密码<br>
4.运行 **provider_begin** 即可启动provider.<br>
5.运行 **GUI** 即可启动consumer.<br>

## Samples
启动GUI后，窗口如下：<br>
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/initialization.png?raw=true" width="48%"/><br>
输入城市名字，即可查询天气，同时语音播报当前天气状况，之后播放音乐。<br>
<p>
不同的天气有不同的背景图，也播放不同的音乐，天气为晴天时，会播放周杰伦的《晴天》,雨天时，播放孙燕姿的《雨天》,阴天时，播放陈奕迅的《阴天快乐》<br>
不同天气的效果展示如下：<br>
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/sun.png?raw=true" width="48%"/>
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/rain.png?raw=true" width="48%"/><br>
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/cloud.png?raw=true" width="48%"/>
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/yin.png?raw=true" width="48%"/><br>
点击常用城市，可以选择许多城市，点击查询，即可查询。
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/citys.png?raw=true" width="48%"/><br>
点击折线图，即可展示气温折线图。
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/linechart.png?raw=true" width="30%"/><br>

## Technology
工程目录结构如下图：<br>
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/consumer.png?raw=true" width="35%"/><br>
<img src="https://github.com/ty625911724/weather_forecast/blob/master/Samples/provider.png?raw=true" width="35%"/><br>

该demo使用了下述的技术：<br>
多线程技术、数据库技术、SOA分布式系统<br>
由于这只是个demo，还有个别可以优化的地方，可以继续改进。

## Requirements
### zoo-keeper
Download and install zookeeper:<br>
http://zookeeper.apache.org/releases.html#download <br>
For the install process, you can read this blog:https://www.cnblogs.com/Lzf127/p/7155316.html<br>
### Weather Api
This code use a free api:
https://www.tianqiapi.com/index/doc?version=v1<br>
You need to register a account and change the **appid** and **appsecret**(in the JuheDemo.java) with the imformation given by this api website.<br>
### MySQL
### IntelliJ IDEA
This code is base on IntelliJ IDEA, and it does not test on other compiler. You can try to use other compilers, maybe change the path in the code.
## Just begin
1.Open the windows command, and enter the dir: zookeeper/bin, just use the command **zkserver.cmd** to open the zookeeper.<br>
2.Link the jar which have been given on lib.<br>
3.use MySQl execute the sql file named "city.sql",create the data base，change database class's useranme and password.<br>
4.Run the **provider_begin** to run the provider.<br>
5.Run the **GUI** to run the consumer.<br>
<p>
Since it is a demo, some part of the code can be further optimize.
