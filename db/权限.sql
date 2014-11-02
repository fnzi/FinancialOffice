DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `mid` int(11) NOT NULL auto_increment,
  `mname` varchar(255) default NULL,
  `mlink` varchar(255) default NULL,
  `mmodule` varchar(255) default NULL,
  `keyid` varchar(255) default NULL,
  `enable` varchar(10) default NULL,
  PRIMARY KEY  (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `rid` int(11) NOT NULL auto_increment,
  `rname` varchar(255) default NULL,
  PRIMARY KEY  (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `uid` int(11) NOT NULL auto_increment,
  `username` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `realname` varchar(255) default NULL,
  `rid` int(11) default NULL,
  PRIMARY KEY  (`uid`),
  KEY `rid` (`rid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_refrolemenu`;
CREATE TABLE `sys_refrolemenu` (
  `mrid` int(11) NOT NULL auto_increment,
  `rid` int(11) default NULL,
  `mid` int(11) default NULL,
  PRIMARY KEY  (`mrid`),
  KEY `rid` (`rid`),
  KEY `mid` (`mid`),
  CONSTRAINT `refrolemenu_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`rid`),
  CONSTRAINT `refrolemenu_ibfk_2` FOREIGN KEY (`mid`) REFERENCES `sys_menu` (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

