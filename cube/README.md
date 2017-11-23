## 逻辑sql处理插件
<p>定义几个模块[DATASOURCE]，[PARAM]，[ACTION]</p> 
 [DATASOURCE]: 自定义数据连接名称  url  驱动名称 <br />
 [PARAM]:自定义变量名称  TYPEAS   数据类型(string,double,long,int)  <br />
 [ACTION]:sql 执行逻辑阶段 语法有: <br />
  
  - 1.自定义变量使用@变量名<br/>
    变量赋值 @v = 1<br/>
  
  -  2.IF 用法<br/>
       IF @Value1 > 0<br/>
           ... <br/>
       ENDIF <br/>
 
  - 3.查询用法<br/>
     QUERY 自定义数据连接名称  AS 临时存储变量名 <br>
           SELECT configValue from APP_SystemConfigs<br>
     ENDQUERY
     
     给变量赋值查询结果<br/>
     @v = 临时存储变量名.configValue(若果有多条支取第一条)
    
     多条处理
     FOREACH 临时存储变量名
     
     ENDFOREACH
     
   - 4.自定义方法
       自定方法只可以在变量赋值是使用在FunctionUtil添加映射以后会到配置文件中
       @BeginDate = DATEADD(@EndDate,-1)
        
    
   有问题可以联系我QQ;491237260会进行改进
       
        
