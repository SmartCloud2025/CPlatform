--  插入微信数据库
-- 1.	服务号（订阅号）信息（ServiceInfo）
DROP TABLE IF EXISTS ServiceInfo;
CREATE TABLE ServiceInfo (
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '标识符id'
  , WebChatID   VARCHAR(64)
  , ServiceType VARCHAR(12) DEFAULT 'T001'
  , URL         VARCHAR(255) NOT NULL
  , Token       VARCHAR(255) NOT NULL
  , AppID       VARCHAR(64)
  , AppSecret   VARCHAR(64)
  , BoundDate   DATETIME
  , Status      VARCHAR(12)  NOT NULL DEFAULT 'A001'
);

-- 2.	粉丝分组（FansGroup）
DROP TABLE IF EXISTS FansGroup;
CREATE TABLE FansGroup (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , GroupName        VARCHAR(64) NOT NULL DEFAULT '默认分组'
  , GroupType        VARCHAR(256)
  , CreateDate       DATETIME
  , Priority         INT
  , Status           VARCHAR(12) NOT NULL DEFAULT 'A001'
  , UNIQUE (ServiceWebChatID, GroupName, Priority)
);

-- 3.	粉丝信息(FansInfo)
DROP TABLE IF EXISTS FansInfo;
CREATE TABLE FansInfo (
    ID          INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , WebChatID   VARCHAR(64) NOT NULL
  , FansGroupID INTEGER     NOT NULL
  , FanName     VARCHAR(64)
  , Address     VARCHAR(255)
  , Sex         VARCHAR(10)
  , FocusTime   DATETIME
  , FocusType   VARCHAR(12) DEFAULT 'F001'
);

-- 4.	粉丝黑名单（FansBlackList）
DROP TABLE IF EXISTS FansBlackList;
CREATE TABLE FansBlackList (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , FansWebChatID    VARCHAR(64) NOT NULL
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , CreateDate       DATETIME
  , Desription       VARCHAR(255)
  , FrozenTimeStamp  TIMESTAMP
);

-- 5.	指令集配置（CmdConfig）
DROP TABLE IF EXISTS CmdConfig;
CREATE TABLE CmdConfig (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , FansGroupId      INT DEFAULT NULL
  , Cmd              VARCHAR(32)
  , Seperator        VARCHAR(32)
  , Type             VARCHAR(32) NOT NULL DEFAULT 'text'
  , MsgID            INT
  , CType            VARCHAR(12) NOT NULL DEFAULT 'CT01'
  , ServiceConfigID  INT
  , Status           VARCHAR(12) NOT NULL DEFAULT 'A001'
);
ALTER TABLE CmdConfig
ADD CONSTRAINT UN_CmdConfig UNIQUE (ServiceWebChatID, Cmd);


-- 6.	指令服务配置(ServiceConfig)
DROP TABLE IF EXISTS ServiceConfig;
CREATE TABLE ServiceConfig (
    ID   INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , Name VARCHAR(64)
  , ServiceUrl VARCHAR(256)
  , Demo VARCHAR(32)
);

-- 7.	关注事件推送消息表（SubcEventRespMsg）
DROP TABLE IF EXISTS SubcEventRespMsg;
CREATE TABLE SubcEventRespMsg (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Type             VARCHAR(32) NOT NULL DEFAULT 'text'
  , MsgID            INT         NOT NULL
);

-- 8.	图文消息表（NewsMsg）
DROP TABLE IF EXISTS NewsMsg;
CREATE TABLE NewsMsg (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64)
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
);

-- 9.	文章描述表（Article）
DROP TABLE IF EXISTS Article;
CREATE TABLE Article (
    ID          INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , NewsMsgID   INT
  , Title       VARCHAR(255)
  , Description VARCHAR(255)
  , PicUrl      VARCHAR(255)
  , Url         VARCHAR(255)
  , Priority    INT
);

-- 11.	主动推送消息（MassPushMsg）
DROP TABLE IF EXISTS MassPushMsg;
CREATE TABLE MassPushMsg (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , ToWebChatID      VARCHAR(64)
  , Type             VARCHAR(32) NOT NULL DEFAULT 'text'
  , MsgID            INT         NOT NULL
  , SendDate         DATETIME
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
  , Status           VARCHAR(255) DEFAULT 'A001'
);

-- 12.	主动推送文本（ActiveText）
DROP TABLE IF EXISTS ActiveText;
CREATE TABLE ActiveText (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Content          VARCHAR(255)
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
);

-- 13.	主动推送图像（ActiveImage）
DROP TABLE IF EXISTS ActiveImage;
CREATE TABLE ActiveImage (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Media_id         VARCHAR(255)
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
);

-- 14.	主动推送声音（ActiveVoice）
DROP TABLE IF EXISTS ActiveVoice;
CREATE TABLE ActiveVoice (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Media_id         VARCHAR(255)
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
);

-- 15.	主动推送音乐（ActiveMusic）
DROP TABLE IF EXISTS ActiveMusic;
CREATE TABLE ActiveMusic (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Title            VARCHAR(255)
  , Musicurl         VARCHAR(255)
  , Hqmusicurl       VARCHAR(255)
  , Thumb_media_id   VARCHAR(255)
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
);

-- 16.	主动推送视频（ActiveVideo）
DROP TABLE IF EXISTS ActiveVideo;
CREATE TABLE ActiveVideo (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Media_id         VARCHAR(255)
  , Title            VARCHAR(255)
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
);

-- 17.	主动推送图文消息表（ActiveNewsMsg）
DROP TABLE IF EXISTS ActiveNewsMsg;
CREATE TABLE ActiveNewsMsg (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64)
  , Description      VARCHAR(255)
  , CreateDate       DATETIME
);

-- 18.	主动推送文章描述表（ActiveArticle）
DROP TABLE IF EXISTS ActiveArticle;
CREATE TABLE ActiveArticle (
    ID          INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , NewsMsgID   INT
  , Title       VARCHAR(255)
  , Description VARCHAR(255)
  , PicUrl      VARCHAR(255)
  , Url         VARCHAR(255)
  , Priority    INT DEFAULT 0
);

-- 19.	会话信息记录表（RecvSessionRecord）
DROP TABLE IF EXISTS RecvSessionRecord;
CREATE TABLE RecvSessionRecord (
    ID          INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , FromUser    VARCHAR(255)
  , ToUser      VARCHAR(255)
  , MsgType     VARCHAR(255)
  , Description VARCHAR(255)
  , Date        DATETIME
  , Year        INT
  , Month       INT
  , Day         INT
  , Hour        INT
);
-- 20.回复文字表（Text）
DROP TABLE IF EXISTS Text;
CREATE TABLE Text (
    ID               INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , ServiceWebChatID VARCHAR(64)
  , Content          VARCHAR(512)
  , Description      VARCHAR(255)
);

-- 21.	菜单按扭（MenuButton）
DROP TABLE IF EXISTS MenuButton;
CREATE TABLE MenuButton (
    ID        INT AUTO_INCREMENT COMMENT '标识符id' PRIMARY KEY
  , PID       INT
  , ServiceWebChatID VARCHAR(64) NOT NULL
  , Name      VARCHAR(32)
  , Type      VARCHAR(12)
  , KeyVaule   VARCHAR(128)
  , ViewUrl   VARCHAR(256)
  , Status    VARCHAR(12)
  , Demo      VARCHAR(512)
);

-- 插入基础数据
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('设置主叫', 'setCaller', '设置主叫服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('创建会议', 'createMeet', '创建会议服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('查询会议记录', 'queryRecord', '查询会议记录服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('查询最近记录', 'recentRecord', '查询最近记录服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('重拨会议', 'redial', '重拨会议服务');

INSERT INTO Text (Content, Description) VALUES ('多方通话业务是针对个人用户推出的带有电话会议功能的电信增值业务，可以同时接入1～7个被叫，实现2～8方同时通话无需密码、使用方便；应用在亲友叙情、家庭议事、同学聚会、同事交流方面上,<a href="http://61.144.17.150/WebPhone/begin?fanId={fanId}&serviceId={serviceId}">立即发起</a>通话', '关注回复文字描述');
INSERT INTO Text (Description,Content) VALUES ('服务自动回复文字描述','不可识别指令
1.设置主叫:SZZJ,主叫号码;
2.创建会议:CJHY,被叫1,被叫2;
3.通话记录:CXJL,yyyymmdd,yyyymmdd;
4.最近记录:CXZJ,D,最近D天记录;
5.重拨会议:CBHY,重呼上次会议;
<a href="http://61.144.17.150/WebPhone/begin?fanId={fanId}&serviceId={serviceId}">进入微会议系统</a>');

INSERT INTO Text (Content, Description) VALUES ( '<a href="http://61.144.17.150/WebPhone/begin?fanId={fanId}&serviceId={serviceId}">电信后付费用户电话会议系统</a>',
  '菜单后付费用户事件');
INSERT INTO Text (Content, Description) VALUES ( '<a href="http://61.144.17.150/WebPhone/begin?fanId={fanId}&serviceId={serviceId}">电信预付费用户电话会议系统</a>',
  '菜单预付费用户事件');

-- 插入《J创摄影》服务号配置
INSERT INTO ServiceInfo (WebChatID, URL, TOKEN) VALUES ('', 'http://jasic.vicp.net/webchat/token/test', 'test');

INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type, MsgID) VALUES ('gh_b817172873c4', 'text', 1);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_b817172873c4', 'DEFAULT', 'text', 2);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'SZZJ', 'text', 'CT02',1);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CJHY', 'text', 'CT02',2);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CXJL', 'text', 'CT02',3);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CXZJ', 'text', 'CT02',4);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CBHY', 'text', 'CT02',5);


# 插入《J创摄影--扫描的》服务号配置
INSERT INTO ServiceInfo (WebChatID, URL, TOKEN, AppID, AppSecret) VALUES  ('', 'http://jasic.vicp.net/webchat/token/gh_6d57b35a4c30', 'token', 'wx39b31e79637b8e77',   'f4b1fab0c5653b2bd89b299f0446f79e');
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_6d57b35a4c30', 'DEFAULT', 'text', 2);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_6d57b35a4c30', 'SZZJ', 'text', 'CT02',1);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_6d57b35a4c30', 'CJHY', 'text', 'CT02',2);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_6d57b35a4c30', 'CXJL', 'text', 'CT02',3);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_6d57b35a4c30', 'CXZJ', 'text', 'CT02',4);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_6d57b35a4c30', 'CBHY', 'text', 'CT02',5);
INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type, MsgID) VALUES ('gh_6d57b35a4c30', 'text', 1);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_6d57b35a4c30', 'postpay', 'text', 3);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_6d57b35a4c30', 'recharge', 'text', 4);


#
# INSERT INTO ServiceInfo (WebChatID, URL, TOKEN) VALUES  ('99ce0457219a1b39c3061cd18d77b7a0', 'http://61.144.17.150/CPlatform/webchat/token/99ce0457219a1b39c3061cd18d77b7a0', '99ce0457219a1b39c3061cd18d77b7a0');
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('99ce0457219a1b39c3061cd18d77b7a0', 'DEFAULT', 'text', 2);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('99ce0457219a1b39c3061cd18d77b7a0', 'SZZJ', 'text', 'CT02',1);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('99ce0457219a1b39c3061cd18d77b7a0', 'CJHY', 'text', 'CT02',2);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('99ce0457219a1b39c3061cd18d77b7a0', 'CXJL', 'text', 'CT02',3);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('99ce0457219a1b39c3061cd18d77b7a0', 'CXZJ', 'text', 'CT02',4);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('99ce0457219a1b39c3061cd18d77b7a0', 'CBHY', 'text', 'CT02',5);
# INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type, MsgID) VALUES ('99ce0457219a1b39c3061cd18d77b7a0', 'text', 1);
#
# INSERT INTO ServiceInfo (WebChatID, URL, TOKEN) VALUES  ('4bad9b5591ec44c0c3061cd18d77b7a0', 'http://61.144.17.150/CPlatform/webchat/token/4bad9b5591ec44c0c3061cd18d77b7a0', '4bad9b5591ec44c0c3061cd18d77b7a0');
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('4bad9b5591ec44c0c3061cd18d77b7a0', 'DEFAULT', 'text', 2);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('4bad9b5591ec44c0c3061cd18d77b7a0', 'SZZJ', 'text', 'CT02',1);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('4bad9b5591ec44c0c3061cd18d77b7a0', 'CJHY', 'text', 'CT02',2);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('4bad9b5591ec44c0c3061cd18d77b7a0', 'CXJL', 'text', 'CT02',3);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('4bad9b5591ec44c0c3061cd18d77b7a0', 'CXZJ', 'text', 'CT02',4);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('4bad9b5591ec44c0c3061cd18d77b7a0', 'CBHY', 'text', 'CT02',5);
# INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type, MsgID) VALUES ('4bad9b5591ec44c0c3061cd18d77b7a0', 'text', 1);
#
# -- 配置微会议服务号ID
# INSERT INTO Text (Content, Description) VALUES ('<a href="http://61.144.17.150/WebPhone/begin?fanId={fanId}&serviceId={serviceId}">创建会议</a>', '关注回复文字描述');
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_05a4e161ab63', 'CreateMeet', 'text', 3);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_05a4e161ab63', 'BookMeet', 'text', 3);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_05a4e161ab63', 'Contacts', 'text', 3);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_05a4e161ab63', 'Priority', 'text', 3);

-- INSERT INTO MenuButton(PID,ServiceWebChatID,Name,TYPE,KeyVaule,ViewUrl,Status) VALUES (NULL,'gh_b817172873c4','业界动态', NULL,NULL,NULL,'A001');

-- INSERT INTO ActiveNewsMsg (ServiceWebChatID, Description) VALUES ('gh_b817172873c4', 'gh_b817172873c4的主动推送消息');
-- INSERT INTO ActiveNewsMsg (ServiceWebChatID, Description) VALUES ('gh_be3554dd14b6', 'gh_be3554dd14b6的主动推送消息');
