
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ref_adjust_report`
-- ----------------------------
DROP TABLE IF EXISTS `ref_adjust_report`;
CREATE TABLE `ref_adjust_report` (
  `rarid` int(11) NOT NULL AUTO_INCREMENT,
  `adjustid` int(11) DEFAULT NULL COMMENT '调整id',
  `reportbatchno` varchar(255) DEFAULT NULL COMMENT '日报中data_type为30状态\r\nbatch_no为唯一关联条件',
  PRIMARY KEY (`rarid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `sys_adjust`
-- ----------------------------
DROP TABLE IF EXISTS `sys_adjust`;
CREATE TABLE `sys_adjust` (
  `adjustId` int(11) NOT NULL AUTO_INCREMENT,
  `reportTime` varchar(255) DEFAULT NULL COMMENT '业务日期',
  `adjustSystem` varchar(255) DEFAULT NULL COMMENT '调整业务系统名称',
  `adjustBusiness` varchar(255) DEFAULT NULL COMMENT '调整的业务',
  `reportId` varchar(255) DEFAULT NULL COMMENT 'reportId',
  `reportName` varchar(255) DEFAULT NULL COMMENT '报表名称',
  `adjustReason` varchar(255) DEFAULT NULL COMMENT '调整的原因',
  `uid` int(11) DEFAULT NULL COMMENT '调整人id',
  `adjuster` varchar(255) DEFAULT NULL COMMENT '调整人',
  `adjustTime` varchar(255) DEFAULT NULL COMMENT '调整时间',
  `oldValue` varchar(255) DEFAULT NULL COMMENT '调整前金额',
  `newValue` varchar(255) DEFAULT NULL COMMENT '调整后金额',
  `flag` varchar(255) DEFAULT NULL COMMENT '收款/付款',
  `status` varchar(255) DEFAULT NULL COMMENT '调整状态:\r\n------31：(初始插入)\r\n------32：(审核之后)\r\n------33：(生成凭证之后)\r\n------39：(初始化删除)',
  `checkerId` int(11) DEFAULT NULL COMMENT '审核人',
  `checker` varchar(255) DEFAULT NULL COMMENT '审核人',
  `checkMsg` varchar(255) DEFAULT NULL COMMENT '审核message',
  PRIMARY KEY (`adjustId`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `sys_custom`
-- ----------------------------
DROP TABLE IF EXISTS `sys_custom`;
CREATE TABLE `sys_custom` (
  `cmid` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(125) DEFAULT NULL COMMENT '商家编号/营运单位代码',
  `name` varchar(125) DEFAULT NULL COMMENT '商家名称/营运单位名称',
  `custom` varchar(125) DEFAULT NULL COMMENT 'A3客户代码',
  PRIMARY KEY (`cmid`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_custom
-- ----------------------------
INSERT INTO `sys_custom` VALUES ('1', '000102054991070', '天津市西青区翠鲜缘水果批发中心', '255');
INSERT INTO `sys_custom` VALUES ('2', '000101017320102', '滨江购物中心-津城通售卡', '121');
INSERT INTO `sys_custom` VALUES ('3', '000101017320104', '滨江商厦-津城通售卡', '142');
INSERT INTO `sys_custom` VALUES ('4', '000101027220013', '米兰购物-津城通售卡', '146');
INSERT INTO `sys_custom` VALUES ('5', '000302050000003', '农业银行—津城通售卡', '156');
INSERT INTO `sys_custom` VALUES ('6', '000302050000007', '中信银行-津城通售卡', '178');
INSERT INTO `sys_custom` VALUES ('7', '000302050000008', '邮储银行-津城通售卡', '187');
INSERT INTO `sys_custom` VALUES ('8', '000302050000011', '建设银行-售卡', '210');
INSERT INTO `sys_custom` VALUES ('9', '000302050000012', '华夏银行', '196');
INSERT INTO `sys_custom` VALUES ('10', '000302050000013', '民生银行—售卡', '214');
INSERT INTO `sys_custom` VALUES ('11', '000302050000016', '农村商业银行—售卡', '218');
INSERT INTO `sys_custom` VALUES ('12', '000302050000020', '北京银行-售卡', '220');
INSERT INTO `sys_custom` VALUES ('13', '000302050000026', '工商银行-售卡', '228');
INSERT INTO `sys_custom` VALUES ('14', '000302050000028', '浦发银行-售卡', '242');
INSERT INTO `sys_custom` VALUES ('15', '000404150000001', '邮局', '050');
INSERT INTO `sys_custom` VALUES ('16', '000801019990237', '自建网点—友谊路', '6001');
INSERT INTO `sys_custom` VALUES ('17', '000801019990240', '自建网点—奥城', '6002');
INSERT INTO `sys_custom` VALUES ('18', '000801019990243', '自建网点—朗香街', '6003');
INSERT INTO `sys_custom` VALUES ('19', '000801019990244', '自建网点—金融街', '6004');
INSERT INTO `sys_custom` VALUES ('20', '000801019990246', '自建网点—宜白路', '6006');
INSERT INTO `sys_custom` VALUES ('21', '000801019990247', '自建网点—王串场', '6007');
INSERT INTO `sys_custom` VALUES ('22', '000801019990250', '自建网点—城市之星', '6010');
INSERT INTO `sys_custom` VALUES ('23', '000801019990253', '自建网点—三源益康', '6011');
INSERT INTO `sys_custom` VALUES ('24', '000801019990254', '自建网点—华盛广场', '6012');
INSERT INTO `sys_custom` VALUES ('25', '000801019990255', '自建网点—奥林匹克大厦', '6015');
INSERT INTO `sys_custom` VALUES ('26', '000801019990256', '自建网点—金纬路', '6013');
INSERT INTO `sys_custom` VALUES ('27', '000801019990257', '自建网点—黄海路', '6014');
INSERT INTO `sys_custom` VALUES ('28', '000801019990258', '自建网点—广开二马路', '6018');
INSERT INTO `sys_custom` VALUES ('29', '000801019990260', '自建网点—芥园道茶城', '6017');
INSERT INTO `sys_custom` VALUES ('30', '000801019990261', '自建网点—赛象酒店', '6020');
INSERT INTO `sys_custom` VALUES ('31', '000801019990276', '伽佑城—友谊路', '280');
INSERT INTO `sys_custom` VALUES ('32', '34234234234', '最新商家1', '123433333');

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '部门编号',
  `name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `department` varchar(255) DEFAULT NULL COMMENT 'A3部门代码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_gl_trntm`
-- ----------------------------
DROP TABLE IF EXISTS `sys_gl_trntm`;
CREATE TABLE `sys_gl_trntm` (
  `gtid` int(11) NOT NULL AUTO_INCREMENT,
  `tr_note` varchar(255) DEFAULT NULL COMMENT '摘要',
  `ac_code` varchar(255) DEFAULT NULL COMMENT '科目代码',
  `tr_cr` varchar(255) DEFAULT NULL COMMENT '凭证贷方',
  `tr_de` varchar(255) DEFAULT NULL COMMENT '凭证借方',
  `tr_custom` varchar(255) DEFAULT NULL COMMENT '客户代码',
  `adjustId` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`gtid`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `mname` varchar(255) DEFAULT NULL,
  `mlink` varchar(255) DEFAULT NULL,
  `mmodule` varchar(255) DEFAULT NULL,
  `keyid` varchar(255) DEFAULT NULL,
  `enable` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('10', '日报同步', null, 'R', null, null);
INSERT INTO `sys_menu` VALUES ('20', '手工调整', null, '-1', null, null);
INSERT INTO `sys_menu` VALUES ('21', '手工调整（当事人）', '/toPage/adjust1', '20', null, null);
INSERT INTO `sys_menu` VALUES ('22', '手工调整（审核）', '/toPage/adjust2', '20', null, null);
INSERT INTO `sys_menu` VALUES ('23', '手工调整（财务核算）', '/toPage/adjust3', '20', null, null);
INSERT INTO `sys_menu` VALUES ('24', '客户代码维护', '/toPage/customer', '30', null, null);
INSERT INTO `sys_menu` VALUES ('25', '部门代码维护', '/toPage/department', '30', null, null);
INSERT INTO `sys_menu` VALUES ('30', '系统维护', null, '-1', null, null);
INSERT INTO `sys_menu` VALUES ('31', '用户维护', '/toPage/user', '30', '', null);
INSERT INTO `sys_menu` VALUES ('32', '角色维护', '/toPage/role', '30', '', null);
INSERT INTO `sys_menu` VALUES ('33', '小额消费商户日结日报', '/doJsp/reportViewService.action?report=MerchantSettleReport', 'REPORT', 'MerchantSettleReport', 'true');
INSERT INTO `sys_menu` VALUES ('34', '小额消费商户周结日报', '/doJsp/reportViewService.action?report=MerchantSettleWeekReport', 'REPORT', 'MerchantSettleWeekReport', 'true');
INSERT INTO `sys_menu` VALUES ('35', '小额消费商户月结日报', '/doJsp/reportViewService.action?report=MerchantSettleMonthReport', 'REPORT', 'MerchantSettleMonthReport', 'true');
INSERT INTO `sys_menu` VALUES ('36', '津城通售卡统计结算报表', '/doJsp/reportViewService.action?report=JCTSaleSettleReport', 'REPORT', 'JCTSaleSettleReport', 'true');
INSERT INTO `sys_menu` VALUES ('40', '核帐', null, '-1', null, null);
INSERT INTO `sys_menu` VALUES ('41', '津城通卡结算服务费日报', '/doJsp/reportViewService.action?report=JCTServiceSettleReport', 'REPORT', 'JCTServiceSettleReport', 'true');
INSERT INTO `sys_menu` VALUES ('110', '日报导入状态', '/toPage/adjust1', '10', null, null);
INSERT INTO `sys_menu` VALUES ('111', '实名VIP城通标准卡充值日报', '/doJsp/reportViewService.action?report=RealVIPRechargeDaily', 'REPORT', 'RealVIPRechargeDaily', 'true');
INSERT INTO `sys_menu` VALUES ('112', '客服网点个性城通卡售卡结算报表', '/doJsp/reportViewService.action?report=PrivateSettleReport', 'REPORT', 'PrivateSettleReport', 'true');

-- ----------------------------
-- Table structure for `sys_refrolemenu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_refrolemenu`;
CREATE TABLE `sys_refrolemenu` (
  `mrid` int(11) NOT NULL AUTO_INCREMENT,
  `rrid` int(11) DEFAULT NULL,
  `rmid` int(11) DEFAULT NULL,
  PRIMARY KEY (`mrid`),
  KEY `rid` (`rrid`),
  KEY `mid` (`rmid`),
  CONSTRAINT `refrolemenu_ibfk_1` FOREIGN KEY (`rrid`) REFERENCES `sys_role` (`rid`),
  CONSTRAINT `refrolemenu_ibfk_2` FOREIGN KEY (`rmid`) REFERENCES `sys_menu` (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_refrolemenu
-- ----------------------------
INSERT INTO `sys_refrolemenu` VALUES ('1', '2', '10');
INSERT INTO `sys_refrolemenu` VALUES ('2', '1', '20');
INSERT INTO `sys_refrolemenu` VALUES ('3', '1', '31');
INSERT INTO `sys_refrolemenu` VALUES ('4', '1', '32');
INSERT INTO `sys_refrolemenu` VALUES ('5', '1', '30');
INSERT INTO `sys_refrolemenu` VALUES ('6', '1', '21');
INSERT INTO `sys_refrolemenu` VALUES ('7', '1', '22');
INSERT INTO `sys_refrolemenu` VALUES ('8', '1', '23');
INSERT INTO `sys_refrolemenu` VALUES ('9', '1', '24');
INSERT INTO `sys_refrolemenu` VALUES ('10', '1', '25');
INSERT INTO `sys_refrolemenu` VALUES ('11', '1', '110');

-- ----------------------------
-- Table structure for `sys_report_status`
-- ----------------------------
DROP TABLE IF EXISTS `sys_report_status`;
CREATE TABLE `sys_report_status` (
  `rsid` int(11) NOT NULL AUTO_INCREMENT,
  `reportId` varchar(255) DEFAULT NULL,
  `reportName` varchar(255) DEFAULT NULL,
  `reportTime` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rsid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_report_status
-- ----------------------------
INSERT INTO `sys_report_status` VALUES ('1', 'MerchantSettleReport', '小额消费商户日结日报', '2015-02-02', '成功');
INSERT INTO `sys_report_status` VALUES ('2', 'MerchantSettleReport', '小额消费商户日结日报', '2015/02/02 11:12:46', '成功');
INSERT INTO `sys_report_status` VALUES ('3', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/02 04:01:51', '失败');
INSERT INTO `sys_report_status` VALUES ('4', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/02 05:00:30', '失败');
INSERT INTO `sys_report_status` VALUES ('5', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 09:00:45', '失败');
INSERT INTO `sys_report_status` VALUES ('6', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 09:00:54', '失败');
INSERT INTO `sys_report_status` VALUES ('7', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 10:00:46', '失败');
INSERT INTO `sys_report_status` VALUES ('8', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 10:00:49', '失败');
INSERT INTO `sys_report_status` VALUES ('9', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 10:00:57', '失败');
INSERT INTO `sys_report_status` VALUES ('10', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 11:00:00', '失败');
INSERT INTO `sys_report_status` VALUES ('11', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 11:00:05', '失败');
INSERT INTO `sys_report_status` VALUES ('12', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 11:01:14', '成功');
INSERT INTO `sys_report_status` VALUES ('13', 'RealVIPRechargeDaily', '实名VIP城通标准卡充值日报', '2015/02/03 11:00:20', '失败');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '操作员1');
INSERT INTO `sys_role` VALUES ('2', '管理员');
INSERT INTO `sys_role` VALUES ('3', '财务人员');
INSERT INTO `sys_role` VALUES ('10', '操作员3');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  `urid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `rid` (`rid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'abc', '123', '操作员', '1', null);
INSERT INTO `sys_user` VALUES ('2', 'sys', '123', '管理员', '2', null);
INSERT INTO `sys_user` VALUES ('3', 'ss', 'ss', 'ss', '1', null);
INSERT INTO `sys_user` VALUES ('4', 'abcd', '123', '131', '1', '1');
INSERT INTO `sys_user` VALUES ('5', 'sdf', '123', 'dasd', '1', null);
INSERT INTO `sys_user` VALUES ('6', 'jhg', '123', 'kjh', '1', null);
INSERT INTO `sys_user` VALUES ('7', 'asd', '123', '321', '1', null);
INSERT INTO `sys_user` VALUES ('8', 'qwe', '123', 'zs', '1', null);
INSERT INTO `sys_user` VALUES ('9', 'qwe', '123', 'zs', '1', null);
