## EBlog 个人博客

## 技术选型
- SpringBoot 
- MyBatis Plus
- MySQL
- Redis
- Framework

## 更新日志
- 2020/09/29:  `环境初始搭建`
- 2020/10/01: `首页数据部分填充以及分页组件的添加`
- 2020/10/02: `本周热议功能实现`
- 2020/10/03: `阅读量定时同步到数据库`
- 2020/10/04: `用户基本设置、用户中心的我的发布和我的收藏、我的消息`
- 2020/10/05: `发布和编辑文章、使用webSocket实现消息即时通知`
- 2020/10/06: `使用es实现搜索功能，以及使用mq通知同步索引库`


## 启动步骤
- 数据库：新建一个eblog数据库，命令如下：
```sql
create database eblog
```
再执行项目目录SQL下的eblog.sql文件
- 环境：
    - Redis：安装并启动Redis服务并在application.yml中修改主机地址
    - RabbitMQ：安装并启动RabbitMQ服务并在application.yml中修改主机地址
    - ElasticSearch：安装并启动ElasticSearch服务并在application.yml中修改主机地址
 
 `注意`：在启动ElasticSearch的时候要先切换到普通用户再启动！
 
 - 刷新Maven
 - 找到主启动类EblogApplication.class运行即可！
    
