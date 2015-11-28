# PowerImprove(自我能力提升,以及记录)

标签（空格分隔）： android handler

---
## 导语:
> 大神不愿意写,也不会去写上层,View层,而我这个小人物却对上层,View层乐不知彼.喜欢炫酷的代码实现,喜欢简洁的设计思想,也喜欢去做一些小小的架构设计,记录一些闪烁的小想法,本工程用来记录我小小想法的瞬间,以及优秀代码的收集,巴拉巴拉~~~
            --- 一个半完美主义者&轻微强迫症患者的述说 ---

>在思索中前进,在前进中沉沦

## 已做:

### 网络请求架构
封装的网络请求,暂时不太满意,持续优化中...
### 沉浸式布局
比较完善的解决沉寂式,以及键盘弹出问题(主要是思想内容,以后再写)

### SharedPreferences架构
喜欢枚举,喜欢它的简洁,以及可约束性,但是枚举在android上效率不高,正打算替换掉枚举,暂时还没有完整的解决方案

### Handler小架构,CommonHandler
优化繁琐的Handler的写法,灵光一闪,简约实现

### 多线程异步共享队列消息发送(MessageProcessorFactory)
练手

### BitmapLoader
用于本地直接生成bitmap,不能使用ImageLoader情况下的小小框架

### FileScanEngine
文件扫描的小小想法

### CommonObserver
公共的观察者模式

### 用于Adapter的回调的接口

 - OnItemCallback
 - OnRecycleCallback
 
### 关于adapter简洁美观写法的思想
 打算另写一篇文章

### BitmapDisplayer(实现Imagelaoder花样展示)
各种图像展示,圆角,圆形,图像偏移等

### NoFrameDialog(无边框)
dialog的基类

### 标准写法,规范语法

 - BaseFile
 - BaseNetJson
 - BaseUser
 - BaseActivity
 - BaseFragment

## 待做:

 - SharedPre架构调整有些想法,打算使用注解的形式,替代枚举
 - 将一些开发思想以独立的文章写出来(需要整理)
	