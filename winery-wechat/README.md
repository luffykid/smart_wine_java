# springBoot开发框
### 技术框架
 SpringBoot2.1.6 Mybatis3.5.2 Redis2.9.1 Shiro1.4
### 标识关键字
（1）admin：标识后台管理系统端

（2）wx：标识微信端

### 接口返回规则
（1）接口请求失败的返回

    {
        "result": "fail",
        "errorCode": "1001",
        "errorMsg": "账号不存在" 
    }

（2）接口请求成功的返回；data中为返回JSON数据

    {
          "result": "success",
          "data": { }
    }

### 接口JWT令牌【默认登录方式获取Token】

（1）接口调用方获取公钥【/admin/common/getRsaPubKey】

（2）接口调用方登录并返回Token【/admin/anon/login】

（3）需要令牌的接口，将token放入请求头

### 服务端接口规则

（1）公共接口：/admin/common/*

（2）访客身份接口：/admin/anon/*

（3）需令牌接口：/admin/auth/*
