--  插入微信数据库
-- 1.	服务号（订阅号）信息（ServiceInfo）
DROP TABLE IF EXISTS ServiceInfo;
CREATE TABLE ServiceInfo (
    ID          INT PRIMARY KEY AUTO_INCREMENT COMMENT '标识符id'
  , WebChatID   VARCHAR(64)  NOT NULL UNIQUE
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

INSERT INTO MenuButton(PID,ServiceWebChatID,Name,TYPE,KeyVaule,ViewUrl,Status) VALUES (NULL,'gh_b817172873c4','业界动态', NULL,NULL,NULL,'A001');

# INSERT INTO ActiveArticle (ewsMsgID, Title, Description, PicUrl, Url)VALUES (1, '测试主动推送图文', '测试主动推送图文的描述喔1', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',        'http://wenku.baidu.com/view/2c581b02b52acfc789ebc9a7.html');
# INSERT INTO ActiveArticle (NewsMsgID, Title, Description, PicUrl, Url)VALUES (1, '测试主动推送图文', '测试主动推送图文的描述喔2', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',        'http://wenku.baidu.com/view/2c581b02b52acfc789ebc9a7.html');
# INSERT INTO ActiveArticle (NewsMsgID, Title, Description, PicUrl, Url)VALUES (1, '测试主动推送图文', '测试主动推送图文的描述喔3', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',        'http://wenku.baidu.com/view/2c581b02b52acfc789ebc9a7.html');
# INSERT INTO ActiveArticle (NewsMsgID, Title, Description, PicUrl, Url)VALUES (3, '测试主动推送图文', '测试主动推送图文的描述喔4', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',        'http://wenku.baidu.com/view/2c581b02b52acfc789ebc9a7.html');
# INSERT INTO ActiveArticle (NewsMsgID, Title, Description, PicUrl, Url)VALUES (2, '测试主动推送图文', '测试主动推送图文的描述喔5', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',        'http://wenku.baidu.com/view/2c581b02b52acfc789ebc9a7.html');

INSERT INTO ActiveNewsMsg (ServiceWebChatID, Description) VALUES ('gh_b817172873c4', 'gh_b817172873c4的主动推送消息');
INSERT INTO ActiveNewsMsg (ServiceWebChatID, Description) VALUES ('gh_be3554dd14b6', 'gh_be3554dd14b6的主动推送消息');

INSERT INTO ServiceInfo (WebChatID, URL, TOKEN) VALUES ('gh_b817172873c4', 'http://bassice.vicp.net/webchat/token/gh_b817172873c4', 'gh_b817172873c4');
INSERT INTO ServiceInfo (WebChatID, URL, TOKEN, AppID, AppSecret) VALUES  ('gh_be3554dd14b6', 'http://bassice.vicp.net/webchat/token/gh_be3554dd14b6', 'gh_be3554dd14b6', 'wx39b31e79637b8e77',   'f4b1fab0c5653b2bd89b299f0446f79e');

# INSERT INTO FansGroup (ServiceWebChatID, GroupName, GroupType) VALUES ('gh_b817172873c4', '默认分组', 'Default');
# INSERT INTO FansGroup (ServiceWebChatID, GroupName, GroupType) VALUES ('gh_b817172873c4', '高级分组', 'Higher');
# INSERT INTO FansGroup (ServiceWebChatID, GroupName, GroupType) VALUES ('gh_b817172873c4', '低组分组', 'Lower');
# INSERT INTO FansGroup (ServiceWebChatID, GroupName, GroupType, status)
# VALUES ('gh_b817172873c4', '失效分组', 'Loser', 'A010');

# INSERT INTO FansInfo (WebChatID, FansGroupID, FanName, Address, FocusType) VALUES ('FanJasic', '1', 'Jasic', '广东广州1', 'F002');

# INSERT INTO FansBlackList (FansWebChatID, ServiceWebChatID, Desription) VALUES ('FanJasic', 'gh_b817172873c4', '必须加入黑名单，因为不喜欢这fans');

# INSERT INTO MassPushMsg (ServiceWebChatID, MsgID) VALUES ('gh_b817172873c4', 1);
# INSERT INTO MassPushMsg (ServiceWebChatID, MsgID) VALUES ('gh_be3554dd14b6', 1);
# INSERT INTO MassPushMsg (ServiceWebChatID, Type, MsgID) VALUES ('gh_be3554dd14b6', 'news', 1);

# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_b817172873c4', 'a', 'text', 1);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_b817172873c4', 'b', 'text', 3);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_b817172873c4', 'c', 'news', 1);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_be3554dd14b6', 'a', 'text', 1);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_be3554dd14b6', 'b', 'text', 3);
# INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_be3554dd14b6', 'c', 'news', 1);

INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, MsgID) VALUES ('gh_b817172873c4', 'DEFAULT', 'text', 2);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'SZZJ', 'text', 'CT02',1);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CJHY', 'text', 'CT02',2);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CXJL', 'text', 'CT02',3);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CXZJ', 'text', 'CT02',4);
INSERT INTO CmdConfig (ServiceWebChatID, Cmd, Type, CType, ServiceConfigID) VALUES ('gh_b817172873c4', 'CBHY', 'text', 'CT02',5);

INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('设置主叫', 'setCaller', '设置主叫服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('创建会议', 'createMeet', '创建会议服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('查询会议记录', 'queryRecord', '查询会议记录服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('查询最近记录', 'recentRecord', '查询最近记录服务');
INSERT INTO ServiceConfig (Name,ServiceUrl,Demo) VALUES ('重拨会议', 'redial', '重拨会议服务');

INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type, MsgID) VALUES ('gh_b817172873c4', 'text', 1);
# INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type, MsgID) VALUES ('gh_b817172873c4', 'news', 1);
# INSERT INTO SubcEventRespMsg (ServiceWebChatID, Type, MsgID) VALUES ('gh_be3554dd14b6', 'news', 1);


# INSERT INTO NewsMsg (Description) VALUES ('图文描述');
# INSERT INTO Article (NewsMsgID, Title, Description, PicUrl, Url) VALUES (1, '测试图文消息标题1', '测试图文消息的描述1', 'http://www.hackhome.com/newimg/20139/2013091252128393.png', 'http://baidu.com');
# INSERT INTO Article (NewsMsgID, Title, Description, PicUrl, Url) VALUES  (1, '测试图文消息标题2', '测试图文消息的描述2', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',   'http://baidu.com');
# INSERT INTO Article (NewsMsgID, Title, Description, PicUrl, Url) VALUES  (1, '测试图文消息标题2', '测试图文消息的描述3', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',   'http://baidu.com');
# INSERT INTO Article (NewsMsgID, Title, Description, PicUrl, Url) VALUES  (2, '测试图文消息标题2', '测试图文消息的描述3', 'http://nuomi.xnimg.cn/upload/deal/2013/7/V_L/310542-1373616839438.jpg',   'http://baidu.com');

# INSERT INTO Text (Content, Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://www.baidu.com', '关注回复文字描述');
# INSERT INTO Text (Content, Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://google.com.cn', '关注回复文字描述');
# INSERT INTO Text (Content, Description) VALUES ('非常欢迎关注此餐厅，更多服务请看http://hao123.com', '关注回复文字描述');
INSERT INTO Text (Content, Description) VALUES ('多方通话业务是针对个人用户推出的带有电话会议功能的电信增值业务，可以同时接入1～7个被叫，实现2～8方同时通话无需密码、使用方便；
应用在亲友叙情、家庭议事、同学聚会、同事交流方面上,<a href="http://jasic.vicp.net/webPhone/begin?fanId={fanId}&serviceId={serviceId}">立即发起</a>通话', '关注回复文字描述');
INSERT INTO Text (Description,Content) VALUES ('服务自动回复文字描述','不可识别指令
1.设置主叫:
  SZZJ,主叫号码;
2.创建会议:
  CJHY,被叫1,被叫2;
3.通话记录:
  CXJL,yyyymmdd,yyyymmdd;
4.最近记录:
  CXZJ,D,最近D天记录;
5.重拨会议:
  CBHY,重呼上次会议;'
);
# INSERT INTO ActiveText (ServiceWebChatID, Content, Description)VALUES ('gh_be3554dd14b6', '这个是主动推送消息1，更多服务请看http://bassice.vicp.net', '主动推送消息描述');
# INSERT INTO ActiveText (ServiceWebChatID, Content, Description)VALUES ('gh_be3554dd14b6', '这个是主动推送消息2，更多服务请看http://bassice.vicp.net', '主动推送消息描述');
# INSERT INTO ActiveText (ServiceWebChatID, Content, Description)VALUES ('gh_be3554dd14b6', '这个是主动推送消息3，更多服务请看http://bassice.vicp.net', '主动推送消息描述');
# INSERT INTO ActiveText (ServiceWebChatID, Content, Description)VALUES ('gh_be3554dd14b6', '这个是主动推送消息4，更多服务请看http://bassice.vicp.net', '主动推送消息描述');
