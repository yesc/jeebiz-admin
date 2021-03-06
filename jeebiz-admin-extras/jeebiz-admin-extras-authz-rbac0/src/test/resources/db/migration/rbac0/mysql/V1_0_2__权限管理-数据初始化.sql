/* 
 * 功能菜单语句，因为系统最终引用的功能模块可不尽相同，可能存在主键冲突问题！
 * 这里给出菜单示例，具体F_ID请根据实际情况进行统一编排！
 */

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('19', '权限管理', '权限', 'perms', '', '1', '', '0', '1', '3');

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('20', '角色管理', '角色', 'role', '/authz/role/ui/list', '1', '', '19', '1', '31');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('20', '查看', '', '0', 'role:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('20', '增加', '', '0', 'role:new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('20', '删除', '', '0', 'role:del', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('20', '修改', '', '0', 'role:edit', 4);

INSERT INTO SYS_FEATURE_LIST (F_ID, F_NAME, F_ABB, F_CODE, F_URL, F_TYPE, F_ICON, F_PARENT, F_VISIBLE, F_ORDER) 
VALUES ('21', '用户管理', '用户', 'user', '/authz/user/ui/list', '1', '', '19', '1', '32');
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '查看', '', '0', 'user:list', 1);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '增加', '', '0', 'user:new', 2);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '删除', '', '0', 'user:del', 3);
INSERT INTO SYS_FEATURE_OPTS (F_ID, OPT_NAME, OPT_ICON, OPT_VISIBLE, OPT_PERMS, OPT_ORDER)
VALUES ('21', '修改', '', '0', 'user:edit', 4);

COMMIT;