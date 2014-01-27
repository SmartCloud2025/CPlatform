
-- 1.	服务号（订阅号）信息（ServiceInfo）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ServiceInfo')
  DROP TABLE [ServiceInfo];
CREATE TABLE ServiceInfo (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , WebChatID VARCHAR(64)  NOT NULL UNIQUE
  , ServiceType VARCHAR(12) DEFAULT 'T001'
  , URL       VARCHAR(255) NOT NULL
  , Token     VARCHAR(255) NOT NULL
  , BoundDate DATETIME DEFAULT GETDATE()
  , Status    VARCHAR(12)  NOT NULL DEFAULT 'A001'
);

insert into ServiceInfo(WebChatID,URL,TOKEN) values('gh_b817172873c4','http://bassice.vicp.net/webchat/token/gh_b817172873c4','gh_b817172873c4')
insert into ServiceInfo(WebChatID,URL,TOKEN) values('gh_be3554dd14b6','http://bassice.vicp.net/webchat/token/gh_be3554dd14b6','gh_be3554dd14b6')

-- 2.	粉丝分组（FansGroup）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'FansGroup')
  DROP TABLE [FansGroup];
CREATE TABLE FansGroup (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64)  NOT NULL
  , GroupName VARCHAR(64) NOT NULL DEFAULT '默认分组'
  , GroupType VARCHAR(256)
  , CreateDate DATETIME DEFAULT GETDATE()
  , Priority INT
  , Status    VARCHAR(12)  NOT NULL DEFAULT 'A001'
  , UNIQUE (ServiceWebChatID,GroupName,Priority)
);

insert into FansGroup(ServiceWebChatID,GroupName,GroupType) values('gh_b817172873c4','默认分组','Default')
insert into FansGroup(ServiceWebChatID,GroupName,GroupType) values('gh_b817172873c4','高级分组','Higher')
insert into FansGroup(ServiceWebChatID,GroupName,GroupType) values('gh_b817172873c4','低组分组','Lower')
insert into FansGroup(ServiceWebChatID,GroupName,GroupType,status) values('gh_b817172873c4','失效分组','Loser','A010')



-- 3.	粉丝信息(FansInfo)
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'FansInfo')
  DROP TABLE [FansInfo];
CREATE TABLE FansInfo (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , WebChatID VARCHAR(64)  NOT NULL
  , FansGroupID INTEGER NOT NULL
  , FanName       VARCHAR(64)
  , Address       VARCHAR(255)
  , Sex           VARCHAR(10)
  , FocusTime DATETIME DEFAULT GETDATE()
  , FocusType VARCHAR(12) DEFAULT 'F001'
);

insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanJasic','1','Jasic','广东高州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanBassice','1','bassice','广东高州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanJBL','2','JBL','广东高州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FANck','1','ck','广东高州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FansBas','4','bas','广东高州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanJasic','4','Jasic','广东高州1','F001')


-- 4.	粉丝黑名单（FansBlackList）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'FansBlackList')
  DROP TABLE [FansBlackList];
CREATE TABLE FansBlackList (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , FansWebChatID VARCHAR(64)  NOT NULL
  , ServiceWebChatID VARCHAR(64)  NOT NULL
  , CreateDate DATETIME DEFAULT GETDATE()
  , Desription VARCHAR(255)
  , FrozenTimeStamp TIMESTAMP
);
insert into FansBlackList(FansWebChatID,ServiceWebChatID,Desription) values('FanJasic','gh_b817172873c4','必须加入黑名单，因为不喜欢这fans')


-- 5.	指令集配置（CmdConfig）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'CmdConfig')
  DROP TABLE [CmdConfig];
CREATE TABLE CmdConfig (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64)  NOT NULL
  , FansGroupId Int DEFAULT NULL
  , Cmd VARCHAR(32)
  , CmdContend VARCHAR
  , Type VARCHAR NOT NULL DEFAULT 'CT01'
  , Status VARCHAR(12) NOT NULL DEFAULT 'A001'
);


-- 6.	关注事件推送消息表（SubcEventRespMsg）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'SubcEventRespMsg')
  DROP TABLE [SubcEventRespMsg];
CREATE TABLE SubcEventRespMsg (
    ID          INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Type VARCHAR(32)   NOT NULL DEFAULT 'text'
  , MsgID INT NOT NULL
);

-- 7.	图文消息表（NewsMsg）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'NewsMsg')
  DROP TABLE [NewsMsg];
CREATE TABLE NewsMsg (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , Description VARCHAR(255)
);

-- 8.	文章描述表（Article）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'Article')
  DROP TABLE [Article];
CREATE TABLE Article (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , NewsMsgID INT
  , Title VARCHAR(255)
  , Description VARCHAR(255)
  , PicUrl VARCHAR(255)
  , Url VARCHAR (255)
);
-- 9.回复文字表（Text）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'Text')
  DROP TABLE [Text];
CREATE TABLE Text (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , Content VARCHAR(255)
  , Description VARCHAR(255)
);

INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type,MsgID) VALUES ('gh_b817172873c4', 'text',1);
INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type,MsgID) VALUES ('gh_b817172873c4', 'news',1);
INSERT INTO NewsMsg(Description) VALUES ('图文描述');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (1,'测试图文消息标题1','测试图文消息的描述1','http://www.hackhome.com/newimg/20139/2013091252128393.png','http://baidu.com');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (1,'测试图文消息标题2','测试图文消息的描述2','http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg','http://baidu.com');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (1,'测试图文消息标题2','测试图文消息的描述3','http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg','http://baidu.com');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (2,'测试图文消息标题2','测试图文消息的描述3','http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg','http://baidu.com');

INSERT INTO Text (Content,Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://www.baidu.com','关注回复文字描述');
INSERT INTO Text (Content,Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://google.com.cn','关注回复文字描述');
INSERT INTO Text (Content,Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://hao123.com','关注回复文字描述');
