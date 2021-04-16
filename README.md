# mall
在macrozheng/mall项目基础上，附加代码、优化，采用附加代码方式目的是不修改任何原始代码，新增代码独立存放于原始代码同级目录下。增加基于vue的webapp端及移动端App方案。

### 1. 部署mysql、redis（暂时未部署ElasticSearch、MinIO，其中MinIO跟aliyun的oss应该是相同用途的，用于管理图片等文件）

### 2. 运行模式

#### 2.1 后台，springboot框架
		
##### 2.1.1 开发者模式（或运行时指定“java -jar xxx.jar --server.port=8090 --spring.profiles.active=dev”）
			
`src/main/resources/application.yml中的“spring.profiles.active=dev”`
	
##### 2.1.2 发布模式（或运行时指定“java -jar xxx.jar --server.port=8090 --spring.profiles.active=prod”）
			
src/main/resources/application.yml中的“spring.profiles.active=prod”
	
#### 2.2 前台（管理员端），vue框架
		
##### 2.2.1 开发者模式（仅可运行时指定）
			
在项目根目录下（package.json文件所在目录）执行“cnpm run dev”，该命令使用开发者配置（./config/dev.env.js）在本地启动一个服务器以支撑该前端页面的运行

##### 2.2.2 发布模式
			
在项目根目录下（package.json文件所在目录）执行“cnpm run build”，该命令会使用发布配置（./config/prod.env.js）打包该项目，打包后存放于“./dist”目录下
	
	
### 3. 打包配置与上线部署详情（编译jar时，使用mysql5与mysql8时，代码需要做些微调）

#### 3.1 后台配置（mysql8）
			
##### 3.1.1 修改mall-master根目录下的pom.xml，将mysql-connector的版本改为8.0.16（或者其他可支持mysql8的connector）
	
```	
...
<mysql-connector.version>8.0.16</mysql-connector.version>
...	
```

##### 3.1.2 修改src/main/resources/application-prod.yml文件：
```		
spring:
	datasource:
		url: jdbc:mysql://127.0.0.1:3306/test_mall?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
		username: root
		password: 123456
	...
		redis:
		host: localhost # Redis服务器地址
		database: 10 # Redis数据库索引（默认为0）
		port: 6379 # Redis服务器连接端口
		password: fashiongo # Redis服务器连接密码（默认为空）
		timeout: 300ms # 连接超时时间（毫秒）
	...
```
	
#### 3.2 后台配置（mysql5）
			
##### 3.2.1 修改mall-master根目录下的pom.xml，将mysql-connector的版本改为5.1.28（或者其他可支持mysql5的connector）
```
...
<mysql-connector.version>5.1.28</mysql-connector.version>
...
```
##### 3.2.2 修改src/main/resources/application-prod.yml文件：
```
spring:
	datasource:
		url: jdbc:mysql://127.0.0.1:3306/test_mall?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
		username: root
		password: 123456
	...
		redis:
		host: localhost # Redis服务器地址
		database: 10 # Redis数据库索引（默认为0）
		port: 6379 # Redis服务器连接端口
		password: # Redis服务器连接密码（默认为空）
		timeout: 300ms # 连接超时时间（毫秒）
	...
```
#### 3.3 前台配置（vue）
		
##### 3.3.1 修改打包配置（只应用于cnpm run build）

```
修改项目根目录下的“./build/utils.js”，添加“publicPath: '../../'”： # 若不做此配置，打包后icon图标丢失（因woff等字体文件引用路径问题引起），参考：https://www.cnblogs.com/fanlu/p/11002744.html

	...
	// Extract CSS when that option is specified
    // (which is the case during production build)
    if (options.extract) {
      return ExtractTextPlugin.extract({
        use: loaders,
        fallback: 'vue-style-loader',
        publicPath: '../../'
      })
    } else {
	...
```

##### 3.3.2 修改默认请求URL头部
```
修改项目根目录下的“./config/dev.env.js”或“./config/prod.env.js”：
	...
	module.exports = merge(prodEnv, {
	  NODE_ENV: '"development"',	# “prod.env.js”中此值为“production”
	  BASE_API: '"http://127.0.0.1:8090"'  # 修改此处的URL指向springboot后台项目监听的URL
	})
	...
```

### 4. 启动（注：当前使用java版本1.8，mall后台代码已允许跨域请求功能）

`nohup java -jar xxx.jar --server.port=8090 --spring.profiles.active=prod 1>> ./stdout.log 2>> ./stdout.log &`


			