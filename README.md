# 燕习（YsuSelfStudy）

 **燕山大学的空教室查询系统，另外可以登录教务系统获取最新的个人考试情况。** 

![输入图片说明](https://images.gitee.com/uploads/images/2019/0326/093612_a38218e6_1760493.png "home_tiny.png")

## 项目特点
---
- 每日同步空教室的数据
- 每日一张必应美图
- 查询成绩
- QQ登录
- 考试查询
- 实验课查询
- 失物通知
- 图书馆座位预约
- 查看校历


## 界面预览
---
![输入图片说明](https://images.gitee.com/uploads/images/2019/0414/232302_7a769d2c_1760493.png "544484577191775cfb07ab83d4d20a2.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0414/232240_9c8339d3_1760493.png "6c97fbc6d453d4f081a1e68697ebc0e.png")
![输入图片说明](https://images.gitee.com/uploads/images/2019/0414/232343_b63ce656_1760493.png "微信图片_20190414230758.png")

## 编译环境
---
Android Studio 3.3.2

项目最低适配 Android 6.0 （SDK 23）

Jdk 11
## 更新日志
---
**2019.4.15**
- 修复了注销QQ登录后，再次打开软件登录状态还有效的问题。

**2019.4.13**
- 新增了查看实验课的功能。
- 发布了正式版。

**2019.4.7**
- 修复软件在性能低的手机上加载数据库时间过长的问题。

**2019.4.6**
- 新增查看校历功能。
- 软件界面大量美化。
- 软件目前比较稳定，准备测试版。

**2019.4.5**
- 新增了查询成绩的功能。
- 图书馆界面状态栏调整。

**2019.4.3**
- 新增了 QQ 的持久化登录。
- 新增了个人界面展示，时间轴，MiPush 的消息展示。

**2019.3.30**
- 调整了 我的考试 界面
- 新增了开启第一屏

**2019.3.29**
- 更新了服务器端代码，大幅度删减冗余数据，现在加载只需要 2 秒！

 **2019.3.26** 
 - 新增了检查更新
 - 修复了不经登录打开查询考试界面的 BUG
 
 **2019.3.25** 
- 美化了教务登录界面，未来登录界面将单纯的作为跳转界面。
- 登录界面可以保存正确的账号信息。
- 取消了图片上显式的文字，未来考虑添加到第一屏上去。

 **2019.3.24** 
- 增加了登录教务系统的功能

**2019.3.19** 
- 强制竖屏。
- 新增了下拉刷新功能。

 **2019.3.18** 
- 修复 Recycler View 在滑动时返回会崩溃的问题。主要是因为在返回时也需要对 Adapter 进行刷新。
- 发现了服务端存在着数据不完全的偶现 Bug，已经定位该问题，预计明天修复。（已修复）

**2019.3.15** 
- 修复了第二天每次打开软件数据库都得加载的问题。现在只有每天的第一次会加载了。
- 还需要设置无网络的提示功能。
- 还需要设置全部强制竖屏功能。

 **2019.3.14** 
- 更新了 QQ 登录功能。
- 新增了空教室查询系统。

 **2019.3.13** 
- 调整了爬虫方法，不再从源地址爬。更改方案为从自己的服务器上获取数据，服务器每天爬一次。
- 准备开始数据库的设计。

**2019.3.12**
- 增加了侧边栏
- 二级界面美化

 **2019.3.7** 
- 新增了基本的布局。

## 未来更新
---
* ~~图书馆的登陆接口~~
* ~~开启第一屏的欢迎~~
* ~~加速空教室的获取速度~~
* ~~状态栏的调整~~
* 分日期查询空教室
* 部分界面接口不要写死了，比如学期。

## 鸣谢
---
[Okhttp](https://github.com/square/okhttp) 网络爬虫

[SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout) 下拉刷新

[jsoup](https://github.com/jhy/jsoup) 网络爬虫

[BasePop](https://github.com/razerdp/BasePopup) 底部弹出框

[AnimatedSvgView](https://github.com/jaredrummler/AnimatedSvgView) 第一屏

[Glide](https://github.com/bumptech/glide) 加载图片

[glide-transformations](https://github.com/wasabeef/glide-transformations) 毛玻璃特效

[gesture-imageview](https://github.com/jasonpolites/gesture-imageview) 手势放大
