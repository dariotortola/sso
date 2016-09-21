DROP TABLE APLICACIONES;
CREATE TABLE APLICACIONES (ID NUMBER(22) NOT NULL, VERSION NUMBER(22) DEFAULT 0   NOT NULL, CODIGO VARCHAR2(255) NOT NULL, DESCRIPCION VARCHAR2(255), ACCESS_TOKEN_VALIDITY NUMBER, ADDITIONAL_INFORMATION VARCHAR2(255), AUTHORIZED_GRANT_TYPES VARCHAR2(255), AUTHORITIES VARCHAR2(255), REFRESH_TOKEN_VALIDITY NUMBER(22), WEB_SERVER_REDIRECT_URI VARCHAR2(255), RESOURCE_IDS VARCHAR2(255), AUTO_APPROVE VARCHAR2(255), PASSWORD VARCHAR2(255), SCOPE VARCHAR2(255), PRIMARY KEY (ID), CONSTRAINT APLICACIONES_CODIGO_UNICO UNIQUE (CODIGO));
INSERT INTO APLICACIONES (ID, VERSION, CODIGO, DESCRIPCION, ACCESS_TOKEN_VALIDITY, ADDITIONAL_INFORMATION, AUTHORIZED_GRANT_TYPES, AUTHORITIES, REFRESH_TOKEN_VALIDITY, WEB_SERVER_REDIRECT_URI, RESOURCE_IDS, AUTO_APPROVE, PASSWORD, SCOPE) VALUES (1, 0, 'acme', null, null, null, null, null, null, null, null, null, 'fZ80/2eHbxRZTjfANmhoBI1IDmg=', 'all');
DROP TABLE LOGIN;
CREATE TABLE LOGIN (APLICACION NUMBER(22) NOT NULL, USUARIO NUMBER(22) NOT NULL, FECHA TIMESTAMP(6) NOT NULL, PRIMARY KEY (APLICACION, USUARIO));
INSERT INTO LOGIN (APLICACION, USUARIO, FECHA) VALUES (1, 1, TIMESTAMP '2016-09-21 13:13:34');
DROP TABLE PERFILES;
CREATE TABLE PERFILES (ID NUMBER(22) NOT NULL, VERSION NUMBER(22) NOT NULL, CODIGO VARCHAR2(255) NOT NULL, APLICACION NUMBER(22) NOT NULL, DESCRIPCION VARCHAR2(255), PRIMARY KEY (ID), CONSTRAINT PERMISO_UNICO_APLICACION UNIQUE (CODIGO, APLICACION));
INSERT INTO PERFILES (ID, VERSION, CODIGO, APLICACION, DESCRIPCION) VALUES (1, 7, 'USUARIO', 1, null);
DROP TABLE PERFIL_PERMISO;
CREATE TABLE PERFIL_PERMISO (PERFIL NUMBER(22) NOT NULL, PERMISO NUMBER(22) NOT NULL, PRIMARY KEY (PERFIL, PERMISO));
INSERT INTO PERFIL_PERMISO (PERFIL, PERMISO) VALUES (1, 1);
DROP TABLE PERMISOS;
CREATE TABLE PERMISOS (ID NUMBER(22) NOT NULL, VERSION NUMBER(22) NOT NULL, CODIGO VARCHAR2(255) NOT NULL, APLICACION NUMBER(22) NOT NULL, DESCRIPCION VARCHAR2(255), PRIMARY KEY (ID), CONSTRAINT CODIGO_UNICO_APLICACION UNIQUE (APLICACION, CODIGO));
INSERT INTO PERMISOS (ID, VERSION, CODIGO, APLICACION, DESCRIPCION) VALUES (1, 0, 'USER', 1, null);
DROP TABLE PETICIONES_RESET;
CREATE TABLE PETICIONES_RESET (ID NUMBER(22) NOT NULL, CLAVE VARCHAR2(255) NOT NULL, USUARIO NUMBER(22) NOT NULL, CADUCIDAD TIMESTAMP(6) DEFAULT SYSDATE   NOT NULL, PRIMARY KEY (ID));
DROP TABLE USUARIOS;
CREATE TABLE USUARIOS (ID NUMBER(22) NOT NULL, VERSION NUMBER(22) DEFAULT 0   NOT NULL, USERNAME VARCHAR2(255) NOT NULL, MD5 VARCHAR2(255), SHA1 VARCHAR2(255), EMAIL VARCHAR2(255), NOMBRE VARCHAR2(255), SHA1_BASE64 VARCHAR2(255), ACTIVO NUMBER(1) DEFAULT 0   NOT NULL, META4 VARCHAR2(255), PRIMARY KEY (ID), CONSTRAINT USUARIOS_USERNAME_UNICO UNIQUE (USERNAME));
INSERT INTO USUARIOS (ID, VERSION, USERNAME, MD5, SHA1, EMAIL, NOMBRE, SHA1_BASE64, ACTIVO, META4) VALUES (1, 0, 'dtortola', 'd8578edf8458ce06fbc5bb76a58c5ca4', 'b1b3773a05c0ed0176787a4f1574ff0075f7521e', 'dtortola@grupokonecta.com', null, 'sbN3OgXA7QF2eHpPFXT/AHX3Uh4=', 1, 'metado');
DROP TABLE USUARIO_PERFIL;
CREATE TABLE USUARIO_PERFIL (USUARIO NUMBER(22) NOT NULL, PERFIL NUMBER(22) NOT NULL, FECHA_DESDE TIMESTAMP(6), FECHA_HASTA TIMESTAMP(6), PRIMARY KEY (USUARIO, PERFIL));
INSERT INTO USUARIO_PERFIL (USUARIO, PERFIL, FECHA_DESDE, FECHA_HASTA) VALUES (1, 1, null, null);
ALTER TABLE LOGIN ADD CONSTRAINT LOGIN_APP FOREIGN KEY (APLICACION) REFERENCES APLICACIONES (ID);
ALTER TABLE LOGIN ADD CONSTRAINT LOGIN_USER FOREIGN KEY (USUARIO) REFERENCES USUARIOS (ID);
ALTER TABLE PERFILES ADD CONSTRAINT PERFILES_FK_APLICACION FOREIGN KEY (APLICACION) REFERENCES APLICACIONES (ID);
ALTER TABLE PERFIL_PERMISO ADD CONSTRAINT PERFIL_PERMISO_FK_PERFIL FOREIGN KEY (PERFIL) REFERENCES PERFILES (ID);
ALTER TABLE PERFIL_PERMISO ADD CONSTRAINT PERFIL_PERMISO_FK_PERMISO FOREIGN KEY (PERMISO) REFERENCES PERMISOS (ID);
ALTER TABLE PERMISOS ADD CONSTRAINT PERMISOS_FK_APLICACION FOREIGN KEY (APLICACION) REFERENCES APLICACIONES (ID);
ALTER TABLE PETICIONES_RESET ADD CONSTRAINT PETICIONES_RESET_FK1 FOREIGN KEY (USUARIO) REFERENCES USUARIOS (ID);
ALTER TABLE USUARIO_PERFIL ADD CONSTRAINT USUARIO_PERFIL_FK_PERFIL FOREIGN KEY (PERFIL) REFERENCES PERFILES (ID);
ALTER TABLE USUARIO_PERFIL ADD CONSTRAINT USUARIO_PERFIL_FK_USUARIO FOREIGN KEY (USUARIO) REFERENCES USUARIOS (ID);
