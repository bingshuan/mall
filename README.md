## 多种类第三方账号登录 —— 桥接模式与适配器模式

#### 适配器模式：

1. 既能够支持已有功能，也能够适配扩展功能
2. 适配的扩展功能还能够复用已有的方法

![image-20231030203123764](C:\Users\ShuangTian\AppData\Roaming\Typora\typora-user-images\image-20231030203123764.png)
#### 桥接模式：

简单来说就是将抽象部分和现实部分分离
![image-20231030205523313](C:\Users\ShuangTian\AppData\Roaming\Typora\typora-user-images\image-20231030205523313.png)


适配器的好处：不修改原有逻辑进行扩展，但随着第三方账号的增加，适配器的适配种类也会增多

桥接模式的好处： 所有功能更具有整体性，不需要额外添加适配组件，后续代码好维护，也好扩展。缺点是需要对已有的稳定功能进行重构，有一定风险
