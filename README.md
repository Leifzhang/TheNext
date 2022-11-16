# TheNext

取名来自奥特曼奈克萨斯，不要问为是什么，就是特摄迷。

主要就是想用kotlin写一个命令行工具，然后可以一键生成一些简单的模板工程，之后把模板压缩进二进制产物中去，然后可以持续化的辅助开发进行迭代。

技术栈选型原因，python写的太拉胯了，而且pip导包其实不太适合开发，而且py2 py3 之间差异较大。另外kotlin作为最进场开发的语言没有什么技术栈障碍，同时大部分机器都是有java环境的。

# 方案

kotlin + gradle application plugin 之后通过gradle  assembleDist 任务生成对应的二进制压缩包。

然后只需要对这个压缩包按照倒bin目录下即可生成对应的脚本。 

而测试阶段则使用unittest进行，可以快速的验证代码是否有效，而且可以很方便的进行参数定义和调试。

# 命令行支持

0. help 你懂得

```shell
impact --help 
```

1.创建一个模块

```shell
impact module  -file xxx -name xxx -group xxxx
```

2. 创建一个工程
```shell
impact project  -file xxx -name xxx 
```

