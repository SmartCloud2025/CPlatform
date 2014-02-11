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
  , AppID VARCHAR(64)
  , AppSecret VARCHAR(64)
  , BoundDate DATETIME DEFAULT GETDATE()
  , Status    VARCHAR(12)  NOT NULL DEFAULT 'A001'
);

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
  , Type VARCHAR(32)  NOT NULL DEFAULT 'text'
  , MsgID INT NOT NULL
  , CType VARCHAR(12) NOT NULL DEFAULT 'CT01'
  , Status VARCHAR(12) NOT NULL DEFAULT 'A001'
);
ALTER TABLE CmdConfig
ADD CONSTRAINT UN_CmdConfig UNIQUE (ServiceWebChatID,Cmd)

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
  , ServiceWebChatID VARCHAR(64)
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
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
  , Priority INT
);

-- 9.回复文字表（Text）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'Text')
  DROP TABLE [Text];
CREATE TABLE Text (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64)
  , Content VARCHAR(255)
  , Description VARCHAR(255)
);

-- 11.	群发消息（MassPushMsg）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'MassPushMsg')
  DROP TABLE [MassPushMsg];
CREATE TABLE MassPushMsg (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Type VARCHAR(32)   NOT NULL DEFAULT 'text'
  , MsgID INT NOT NULL
  , SendDate DATETIME DEFAULT GETDATE()
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
  , Status VARCHAR(255) DEFAULT 'A001'
);

-- 12.	主动推送文本（ActiveText）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ActiveText')
  DROP TABLE [ActiveText];
CREATE TABLE ActiveText (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Content VARCHAR(255)
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
);

-- 13.	主动推送图像（ActiveImage）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ActiveImage')
  DROP TABLE [ActiveImage];
CREATE TABLE ActiveImage (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Media_id VARCHAR(255)
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
);

-- 14.	主动推送声音（ActiveVoice）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ActiveVoice')
  DROP TABLE [ActiveVoice];
CREATE TABLE ActiveVoice (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Media_id VARCHAR(255)
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
);

-- 15.	主动推送音乐（ActiveMusic）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ActiveMusic')
  DROP TABLE [ActiveMusic];
CREATE TABLE ActiveMusic (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Title VARCHAR(255)
  , Musicurl VARCHAR(255)
  , Hqmusicurl VARCHAR(255)
  , Thumb_media_id VARCHAR(255)
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
);

-- 16.	主动推送视频（ActiveVideo）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ActiveVideo')
  DROP TABLE [ActiveVideo];
CREATE TABLE ActiveVideo (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Media_id VARCHAR(255)
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
);


-- 17.	主动推送图文消息表（ActiveNewsMsg）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ActiveNewsMsg')
  DROP TABLE [ActiveNewsMsg];
CREATE TABLE ActiveNewsMsg (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , ServiceWebChatID VARCHAR(64)
  , Description VARCHAR(255)
  , CreateDate DATETIME DEFAULT GETDATE()
);

-- 18.	主动推送文章描述表（ActiveArticle）
IF EXISTS(SELECT
            *
          FROM INFORMATION_SCHEMA.TABLES
          WHERE TABLE_NAME = 'ActiveArticle')
  DROP TABLE [ActiveArticle];
CREATE TABLE ActiveArticle (
    ID       INT IDENTITY (1, 1) PRIMARY KEY
  , NewsMsgID INT
  , Title VARCHAR(255)
  , Description VARCHAR(255)
  , PicUrl VARCHAR(255)
  , Url VARCHAR (255)
  , Priority INT
);

insert into ServiceInfo(WebChatID,URL,TOKEN) values('gh_b817172873c4','http://bassice.vicp.net/webchat/token/gh_b817172873c4','gh_b817172873c4')
insert into ServiceInfo(WebChatID,URL,TOKEN) values('gh_be3554dd14b6','http://bassice.vicp.net/webchat/token/gh_be3554dd14b6','gh_be3554dd14b6')

insert into FansGroup(ServiceWebChatID,GroupName,GroupType) values('gh_b817172873c4','默认分组','Default')
insert into FansGroup(ServiceWebChatID,GroupName,GroupType) values('gh_b817172873c4','高级分组','Higher')
insert into FansGroup(ServiceWebChatID,GroupName,GroupType) values('gh_b817172873c4','低组分组','Lower')
insert into FansGroup(ServiceWebChatID,GroupName,GroupType,status) values('gh_b817172873c4','失效分组','Loser','A010')

insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanJasic','1','Jasic','广东广州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanBassice','1','bassice','广东广州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanJBL','2','JBL','广东广州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FANck','1','ck','广东广州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FansBas','4','bas','广东广州1','F002')
insert into FansInfo(WebChatID,FansGroupID,FanName,Address,FocusType) values('FanJasic','4','Jasic','广东广州1','F001')

insert into FansBlackList(FansWebChatID,ServiceWebChatID,Desription) values('FanJasic','gh_b817172873c4','必须加入黑名单，因为不喜欢这fans')

INSERT INTO MassPushMsg (ServiceWebChatID,MsgID) VALUES ('gh_b817172873c4',1);

INSERT INTO CmdConfig (ServiceWebChatID,Cmd,Type,MsgID) VALUES ('gh_b817172873c4','a','text',1);
INSERT INTO CmdConfig (ServiceWebChatID,Cmd,Type,MsgID) VALUES ('gh_b817172873c4','b','text',3);
INSERT INTO CmdConfig (ServiceWebChatID,Cmd,Type,MsgID) VALUES ('gh_b817172873c4','c','news',1);
INSERT INTO CmdConfig (ServiceWebChatID,Cmd,Type,MsgID) VALUES ('gh_be3554dd14b6','c','news',1);

INSERT INTO CmdConfig (ServiceWebChatID,Cmd,Type,MsgID,CType) VALUES ('gh_b817172873c4','d','text',1,'CT02');
INSERT INTO CmdConfig (ServiceWebChatID,Cmd,Type,MsgID,CType) VALUES ('gh_be3554dd14b6','d','text',1,'CT02');

INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type,MsgID) VALUES ('gh_b817172873c4', 'text',3);
INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type,MsgID) VALUES ('gh_b817172873c4', 'news',1);
INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type,MsgID) VALUES ('gh_be3554dd14b6', 'text',1);

INSERT INTO NewsMsg(Description) VALUES ('图文描述');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (1,'测试图文消息标题1','测试图文消息的描述1','http://www.hackhome.com/newimg/20139/2013091252128393.png','http://baidu.com');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (1,'测试图文消息标题2','测试图文消息的描述2','http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg','http://baidu.com');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (1,'测试图文消息标题2','测试图文消息的描述3','http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg','http://baidu.com');
INSERT INTO Article (NewsMsgID,Title,Description,PicUrl,Url) VALUES (2,'测试图文消息标题2','测试图文消息的描述3','http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg','http://baidu.com');

INSERT INTO Text (Content,Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://www.baidu.com','关注回复文字描述');
INSERT INTO Text (Content,Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://google.com.cn','关注回复文字描述');
INSERT INTO Text (Content,Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://hao123.com','关注回复文字描述');