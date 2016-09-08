<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [PowerImprove(自我能力提升,以及记录)](#powerimprove%E8%87%AA%E6%88%91%E8%83%BD%E5%8A%9B%E6%8F%90%E5%8D%87%E4%BB%A5%E5%8F%8A%E8%AE%B0%E5%BD%95)
  - [导语:](#%E5%AF%BC%E8%AF%AD)
  - [已做:](#%E5%B7%B2%E5%81%9A)
    - [网络请求架构](#%E7%BD%91%E7%BB%9C%E8%AF%B7%E6%B1%82%E6%9E%B6%E6%9E%84)
    - [沉浸式布局](#%E6%B2%89%E6%B5%B8%E5%BC%8F%E5%B8%83%E5%B1%80)
    - [SharedPreferences架构](#sharedpreferences%E6%9E%B6%E6%9E%84)
    - [Handler小架构,CommonHandler](#handler%E5%B0%8F%E6%9E%B6%E6%9E%84commonhandler)
    - [多线程异步共享队列消息发送(MessageProcessorFactory)](#%E5%A4%9A%E7%BA%BF%E7%A8%8B%E5%BC%82%E6%AD%A5%E5%85%B1%E4%BA%AB%E9%98%9F%E5%88%97%E6%B6%88%E6%81%AF%E5%8F%91%E9%80%81messageprocessorfactory)
    - [BitmapLoader](#bitmaploader)
    - [FileScanEngine](#filescanengine)
    - [CommonObserver](#commonobserver)
    - [用于Adapter的回调的接口](#%E7%94%A8%E4%BA%8Eadapter%E7%9A%84%E5%9B%9E%E8%B0%83%E7%9A%84%E6%8E%A5%E5%8F%A3)
    - [关于adapter简洁美观写法的思想](#%E5%85%B3%E4%BA%8Eadapter%E7%AE%80%E6%B4%81%E7%BE%8E%E8%A7%82%E5%86%99%E6%B3%95%E7%9A%84%E6%80%9D%E6%83%B3)
    - [BitmapDisplayer(实现Imagelaoder花样展示)](#bitmapdisplayer%E5%AE%9E%E7%8E%B0imagelaoder%E8%8A%B1%E6%A0%B7%E5%B1%95%E7%A4%BA)
    - [NoFrameDialog(无边框)](#noframedialog%E6%97%A0%E8%BE%B9%E6%A1%86)
    - [标准写法,规范语法](#%E6%A0%87%E5%87%86%E5%86%99%E6%B3%95%E8%A7%84%E8%8C%83%E8%AF%AD%E6%B3%95)
  - [待做:](#%E5%BE%85%E5%81%9A)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

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
	