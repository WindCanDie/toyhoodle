####################

#######################
[DATASOURCE]
  impala  jdbc:hive2://ip:21050/hnaudit;auth=noSasl  org.apache.hive.jdbc.HiveDriver
[PARAM]
	#################################################
	##			常用输入参数
	#################################################
  @BeginDate           TYPEAS        string
  @EndDate             TYPEAS        string
  @BeginTime           TYPEAS        string
  @EndTime             TYPEAS        string
  @proctiomes          TYPEAS        int
  @PkiIndustry         TYPEAS        int
  @DeviceIndustry      TYPEAS        int
  @WarnConfigId        TYPEAS        int
  @WarnType            TYPEAS        int
  @SmallType           TYPEAS        int
  @WarnLevel           TYPEAS        int
  @Flag                TYPEAS        int
  @Value1              TYPEAS        int
  @Value2              TYPEAS        int
  @Value3              TYPEAS        int
  @Value4              TYPEAS        int
  @Value5              TYPEAS        int
  @Value6              TYPEAS        int
  @Value7              TYPEAS        int
  @Value8              TYPEAS        int
  @Value9              TYPEAS        int
[ACTION]
###########################
# config
##############################
  QUERY impala AS Card
    SELECT executedate from Card WHERE id = 2
  ENDQUERY

  @BeginTime = Card.executedate
  @EndDate = DATEADD(@BeginTime,-1)

  QUERY impala  AS ConfigValue1
    SELECT configValue from APP_SystemConfigs WHERE  ConfigName = 'PkiIndustry'
  ENDQUERY

  @PkiIndustry = ConfigValue1.configvalue
  QUERY impala  AS ConfigValue2
    SELECT configValue from APP_SystemConfigs WHERE  ConfigName = 'DeviceIndustry'
  ENDQUERY

  @DeviceIndustry =ConfigValue2.configvalue
  @WarnType = 31
  @WarnLevel = 1
  QUERY impala  AS mycursor
    SELECT Id,Value1,Value2,Value3,Value4,Value5,Value6,Value7,Value8,Value9 from APP_WarnConfig WHERE  WarnType = 31
  ENDQUERY
   #################
   #
   ################
  FOREACH mycursor @WarnConfigId,@Value1,@Value2,@Value3,@Value4,@Value5,@Value6,@Value7,@Value8,@Value9
    IF @PkiIndustry == 1
    IF @Value1 > 0
    		 @BeginDate = @BeginTime
    		 @SmallType = 1
    	QUERY impala
        	insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
        	            WarnContent,
        				KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
        	select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
        						concat('查询社会人员共<span class="Jcont">',cast(IDnum as string),'</span>个'),
        						'',IDnum,0,PkiUserId,0,UpdateTime
        	from App_PkiDay
        					where UpdateTime = @BeginDate and IDnum >= @Value1
          ENDQUERY
    ENDIF
    IF @Value2 > 0
        @SmallType = 2
        QUERY impala
            insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
            						WarnContent,
            						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
            					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
            					concat('访问总次数共<span class="Jcont">',cast(LoginNum as string),'</span>次'),
            						'',LoginNum,0,PkiUserId,0,UpdateTime
            					from App_PkiDay
            					where UpdateTime = @BeginDate and LoginNum >= @Value2
        ENDQUERY
    ENDIF
    IF @Value4 > 0
        @BeginDate =  DATEADD(@EndDate,-@Value3)
        @SmallType = 3
        QUERY impala
             DELETE FROM App_WarnAudit WHERE  WarnConfigId = @WarnConfigId and SmallType = @SmallType and UpdateTime = to_date(date_add(@EndDate,-1))
        ENDQUERY
        QUERY impala
        	insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
        						WarnContent,
        						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
        					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
        						concat('持续<span class="Jcont">',cast(@Value3 as string),'</span>天查询社会人员超过',cast(@Value4 as string),'个'),
        						'',@Value3,0,PkiUserId,0,@EndDate
        					from (select PkiUserId,''
                                  					from
                                  						(select PkiUserId,UpdateTime
                                  						from App_PkiDay
                                  						where UpdateTime >= @BeginDate and UpdateTime <= @EndDate and IDnum >= @Value4) d
                                  					group by PkiUserId
                                  					having count(1) >= @Value3) u
                where not exists(select * from App_WarnAudit w where w.WarnConfigId = @WarnConfigId and w.SmallType = @SmallType and w.UpdateTime = to_date(date_add(@EndDate,-1)) and w.PkiUserId = u.PkiUserId)
        ENDQUERY
    ENDIF
    IF @Value5 > 0
     @SmallType = 4
           QUERY impala
               DELETE FROM App_WarnAudit WHERE  WarnConfigId = @WarnConfigId and SmallType = @SmallType and UpdateTime = to_date(date_add(@EndDate,-1))
           ENDQUERY
          QUERY impala
              insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
              						WarnContent,
              						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
              					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
              						concat('持续<span class="Jcont">',cast(@Value3 as string),'</span>天访问总数超过',cast(@Value5 as string),'次'),
              						'',@Value3,0,PkiUserId,0,@EndDate
              					from (select PkiUserId,''
                                      					from
                                      						(select PkiUserId,UpdateTime
                                      						from App_PkiDay
                                      						where UpdateTime >= @BeginDate and UpdateTime <= @EndDate and LoginNum >= @Value5) d
                                      					group by PkiUserId
                                      					having count(1) >= @Value3) u
              					where not exists(select * from App_WarnAudit w where w.WarnConfigId = @WarnConfigId and w.SmallType = @SmallType and w.UpdateTime = to_date(date_add(@EndDate,-1)) and w.PkiUserId = u.PkiUserId)
          ENDQUERY
    ENDIF
    IF @Value8 > 0
        @BeginDate = DATEADD(@EndDate,-@Value6)
        @SmallType = 5
        QUERY impala
            insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
            						WarnContent,
            						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
            					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
            					concat(cast(@Value6 as string),'天内累计<span class="Jcont">',cast(count(KeyWord) as string),'</span>天查询社会人员超过',cast(@Value8 as string),'个'),
            					group_concat(KeyWord,',') KeyWord ,
            					cast(count(KeyWord) as int),0,PkiUserId,0,@EndDate
            					from (select PkiUserId,cast(UpdateTime as string) as KeyWord
                                                                  					from App_PkiDay
                                                                  					where UpdateTime >= @BeginDate and UpdateTime <= @EndDate and IDnum >= @Value8) a
            					group by PkiUserId
            					having count(a.KeyWord) >= @Value7
        ENDQUERY
    ENDIF
    IF @Value9 > 0
        @SmallType = 6
         QUERY impala
              insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
            						WarnContent,
            						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
            					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
            					concat(cast(@Value6 as string),'天内累计<span class="Jcont">',cast(count(KeyWord) as string),'</span>天访问总数超过',cast(@Value9 as string),'次'),
            					group_concat(KeyWord,',') KeyWord ,
                                cast(count(KeyWord) as int),0,PkiUserId,0,@EndDate
            					from (select PkiUserId,cast(UpdateTime as string) as KeyWord
                                                                    					from App_PkiDay
                                                                    					where UpdateTime >= @BeginDate and UpdateTime <= @EndDate and LoginNum >= @Value9) a
            					group by PkiUserId
            					having count(a.KeyWord) >= @Value7
         ENDQUERY
    ENDIF
    ENDIF
    IF @DeviceIndustry == 1
        IF @Value1 > 0
            @SmallType = 7
            @BeginDate = @EndDate
            QUERY impala
                insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
                						WarnContent,
                						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
                					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
                						concat('查询社会人员共<span class="Jcont">',cast(IDnum as string),'</span>个'),
                						'',IDnum,UniqueId,'',0,UpdateTime
                					from App_DeviceDay
                					where UpdateTime =  @BeginDate  and IDnum >= @Value1
            ENDQUERY
        ENDIF
        IF @Value2 > 0
               @SmallType = 8
              QUERY impala
             insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
             						WarnContent,
             						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
             					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
             						concat('访问总次数共<span class="Jcont">',cast(LoginNum as string),'</span>次'),
             						'',LoginNum,UniqueId,'',0,UpdateTime
             					from App_DeviceDay
             					where UpdateTime = @BeginDate and LoginNum >= @Value2
             ENDQUERY
        ENDIF
        IF @Value4 > 0

             @SmallType = 9
             @BeginDate = DATEADD(@EndDate,-@Value3)
              QUERY impala
                    DELETE FROM App_WarnAudit WHERE  WarnConfigId = @WarnConfigId and SmallType = @SmallType and UpdateTime = @BeginDate
              ENDQUERY

              QUERY impala
                insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
                						WarnContent,
                						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
                					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
                						concat('持续<span class="Jcont">',cast(@Value3 as string),'</span>天查询社会人员超过',cast(@Value4 as string),'个'),
                						'',@Value3,UniqueId,'',0,@EndDate
                					from (select UniqueId,''
                                            from
                                              (select UniqueId,UpdateTime
                                               from App_DeviceDay
                                               where UpdateTime >= @BeginDate and UpdateTime <= @EndDate and IDnum >= @Value4) d
                                                group by UniqueId
                                                having count(1) >= @Value3) u
                					where not exists(select * from App_WarnAudit w where w.WarnConfigId = @WarnConfigId and w.SmallType = @SmallType and w.UpdateTime = to_date(date_add(@EndDate,-1)) and w.UniqueId = u.UniqueId)
             ENDQUERY
        ENDIF
        IF @Value5 > 0
            @SmallType = 10
            QUERY impala
             DELETE FROM App_WarnAudit where WarnConfigId = @WarnConfigId and SmallType = @SmallType and UpdateTime = to_date(date_add(@EndDate,-1))
            ENDQUERY
             QUERY impala
            	insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
            	            WarnContent,
            				KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
            	select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
            						concat('持续<span class="Jcont">',cast(@Value3 as string),'</span>天访问总数超过',cast(@Value5 as string),'次'),
            						'',@Value3,UniqueId,'',0,@EndDate
            	from (select UniqueId,''
                                        from
                                    (select UniqueId,UpdateTime
                                 from App_DeviceDay
                                  where UpdateTime >= @BeginDate and UpdateTime <= @EndDate and LoginNum >= @Value5) d
                                                     					group by UniqueId
                                                     					having count(1) >= @Value3) u
            	where not exists(select * from App_WarnAudit w where w.WarnConfigId = @WarnConfigId and w.SmallType = @SmallType and w.UpdateTime =to_date(date_add(@EndDate,-1)) and w.UniqueId = u.UniqueId)
            ENDQUERY
        ENDIF
        IF @Value8 > 0
               @BeginDate = DATEADD(@EndDate,-@Value6)
               @SmallType = 11
               QUERY impala
                    insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
                                      						WarnContent,
                                      						KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
                                      					select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
                                      					 concat(cast(3 as string),'天内累计<span class="Jcont">',cast(count(KeyWord) as string),'</span>天查询社会人员超过',cast(3 as string),'个'),
                                      					group_concat(KeyWord,',')  KeyWord ,
                                      					cast(count(KeyWord) as int),UniqueId,'',0,@EndDate
                                      					from (select UniqueId,cast(UpdateTime as string) as KeyWord
                                                              					from App_DeviceDay
                                                              					where UpdateTime >= @BeginDate and UpdateTime <= @EndDate and IDnum >= @Value8) a
                                      					group by UniqueId
                                      					having count(a.KeyWord) >= @Value7
               ENDQUERY
        ENDIF
        IF @Value9 > 0
            @SmallType = 12
            QUERY impala
                 insert into App_WarnAudit(WarnConfigId,WarnType,SmallType,WarnLevel,
                                                        WarnContent,
                                                        KeyWord,WarnNumber,UniqueId,PkiUserId,TitleId,UpdateTime)
                                                    select @WarnConfigId,@WarnType,@SmallType,@WarnLevel,
                                                    concat(cast(@Value6 as string),'天内累计<span class="Jcont">',cast(count(KeyWord) as string),'</span>天访问总数超过',cast(@Value9 as string),'次'),
                                                    group_concat(KeyWord,',')  KeyWord ,
                                                    cast(count(KeyWord) as int),UniqueId,'',0,@EndDate
                                                    from(select UniqueId,cast(UpdateTime as string) as KeyWord
                                                                                                          from App_DeviceDay
                                                                                                          where UpdateTime >= @BeginDate and UpdateTime <= @EndDate  and LoginNum >= @Value9) a
                                                    group by UniqueId
                                                    having count(a.KeyWord) >= @Value7
            ENDQUERY
        ENDIF
    ENDIF
  ENDFOREACH