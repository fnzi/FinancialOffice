DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function` (
  `fid` int(11) NOT NULL auto_increment,
  `fname` varchar(255) default NULL,
  `flink` varchar(255) default NULL,
  `fmodule` varchar(255) default NULL,
  `keyid` varchar(255) default NULL,
  PRIMARY KEY  (`fid`)
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


DROP TABLE IF EXISTS `sys_refrolefunc`;
CREATE TABLE `sys_refrolefunc` (
  `frid` int(11) NOT NULL auto_increment,
  `rid` int(11) default NULL,
  `fid` int(11) default NULL,
  PRIMARY KEY  (`frid`),
  KEY `rid` (`rid`),
  KEY `fid` (`fid`),
  CONSTRAINT `refrolefunc_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`rid`),
  CONSTRAINT `refrolefunc_ibfk_2` FOREIGN KEY (`fid`) REFERENCES `sys_function` (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

