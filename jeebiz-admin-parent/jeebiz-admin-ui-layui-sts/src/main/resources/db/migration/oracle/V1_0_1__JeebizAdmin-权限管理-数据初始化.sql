
DELETE FROM SYS_AUTHZ_ROLE_LIST;
INSERT INTO SYS_AUTHZ_ROLE_LIST ( R_ID, R_NAME, R_TYPE, R_INTRO, R_STATUS)
VALUES ( '1', '超级管理员', '1', '系统初始化的最高权限用户', '1' );

DELETE FROM SYS_AUTHZ_ROLE_PERMS;
INSERT INTO SYS_AUTHZ_ROLE_PERMS ( R_ID, PERMS) VALUES ( '1', '*');

DELETE FROM SYS_AUTHZ_USER_LIST;
INSERT INTO SYS_AUTHZ_USER_LIST ( U_ID, U_USERNAME, U_PASSWORD, U_SALT, U_SECRET, U_ALIAS, U_AVATAR, U_PHONE, U_EMAIL, U_REMARK, U_STATUS  )
VALUES ( '1', 'admin', 'MTIzNDU2', 'MTIzNDU2', 'MTIzNDU2', '超级管理员', '','13735896863', 'mhk@163.com', '默认最高权限管理员', '1');

DELETE FROM SYS_AUTHZ_USER_DETAIL;
INSERT INTO SYS_AUTHZ_USER_DETAIL ( U_ID, D_ID, D_BIRTHDAY, D_GENDER, D_IDCARD)
VALUES ( '1', '1', '1989-10-01', 'male', '411526198910010001');

DELETE FROM SYS_AUTHZ_USER_ROLE_RELATION;
INSERT INTO SYS_AUTHZ_USER_ROLE_RELATION ( U_ID, R_ID, R_PRTY)
VALUES ( '1', '1', '0');

COMMIT;