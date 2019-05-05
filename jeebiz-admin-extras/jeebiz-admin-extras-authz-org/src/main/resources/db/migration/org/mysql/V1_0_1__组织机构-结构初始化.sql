
/* 组织机构核心表：组织机构表（包含机构，部门，岗位，以及上下级关联的字段）  、用户组织机构关联表*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for SYS_AUTHZ_COMPANY
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_COMPANY`;
CREATE TABLE `SYS_AUTHZ_COMPANY` (
  `COM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司ID编号',
  `COM_CODE` varchar(30) DEFAULT NULL COMMENT '公司编码',
  `COM_NAME` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `COM_REMARK` text COMMENT '公司说明',
  `COM_PARENT` int(11) DEFAULT NULL COMMENT '父级公司ID编号',
  `COM_USERID` varchar(32) DEFAULT NULL COMMENT '公司创建人ID',
  `COM_STATUS` varchar(1) DEFAULT '0' COMMENT '公司状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公司创建时间',
  PRIMARY KEY (`COM_ID`),
  UNIQUE KEY `COM_CODE` (`COM_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司信息表';


-- ----------------------------
-- Table structure for SYS_AUTHZ_COMPANY
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_COMPANY`;
CREATE TABLE `SYS_AUTHZ_COMPANY` (
  `COM_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司ID编号',
  `COM_CODE` varchar(30) DEFAULT NULL COMMENT '公司编码',
  `COM_NAME` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `COM_REMARK` text COMMENT '公司说明',
  `COM_PARENT` int(11) DEFAULT NULL COMMENT '父级公司ID编号',
  `COM_USERID` varchar(32) DEFAULT NULL COMMENT '公司创建人ID',
  `COM_STATUS` varchar(1) DEFAULT '0' COMMENT '公司状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '公司创建时间',
  PRIMARY KEY (`COM_ID`),
  UNIQUE KEY `COM_CODE` (`COM_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公司信息表';


-- ----------------------------
-- Table structure for SYS_AUTHZ_DEPARTMENT
-- ----------------------------
DROP TABLE IF EXISTS `SYS_AUTHZ_DEPARTMENT`;
CREATE TABLE `SYS_AUTHZ_DEPARTMENT` (
  `COM_ID` int(11) NOT NULL COMMENT '公司ID编号',
  `DEPT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID编号',
  `DEPT_CODE` varchar(30) DEFAULT NULL COMMENT '部门编码',
  `DEPT_NAME` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `DEPT_REMARK` text COMMENT '部门说明',
  `DEPT_PARENT` int(11) DEFAULT NULL COMMENT '父级部门ID编号',
  `DEPT_USERID` varchar(32) DEFAULT NULL COMMENT '部门创建人ID',
  `DEPT_STATUS` varchar(1) DEFAULT '0' COMMENT '部门状态（0:禁用|1:可用）',
  `TIME24` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '部门创建时间',
  PRIMARY KEY (`DEPT_ID`),
  UNIQUE KEY `DEPT_CODE` (`DEPT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';
