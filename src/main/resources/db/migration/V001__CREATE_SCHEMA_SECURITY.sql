CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE SCHEMA IF NOT EXISTS "security";

CREATE TABLE IF NOT EXISTS "security"."user" (
    guid UUID NOT NULL DEFAULT gen_random_uuid(),
    user_name VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    account_non_expired BOOLEAN DEFAULT TRUE,
    account_non_locked BOOLEAN DEFAULT TRUE,
    credentials_non_expired BOOLEAN DEFAULT TRUE,
    enabled BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_user PRIMARY KEY (guid),
    CONSTRAINT uk_user_name UNIQUE (user_name)
    );

CREATE TABLE IF NOT EXISTS "security"."permission" (
                                                       guid UUID NOT NULL DEFAULT gen_random_uuid(),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT pk_permission PRIMARY KEY (guid),
    CONSTRAINT uk_permission_description UNIQUE (description)
);

CREATE TABLE IF NOT EXISTS "security"."user_permission" (
    guid_user UUID NOT NULL,
    guid_permission UUID NOT NULL,
    CONSTRAINT pk_user_permission PRIMARY KEY (guid_user, guid_permission),
    CONSTRAINT fk_user_permission_user
    FOREIGN KEY (guid_user)
    REFERENCES "security"."user"(guid)
    ON DELETE CASCADE,
    CONSTRAINT fk_user_permission_permission
    FOREIGN KEY (guid_permission)
    REFERENCES "security"."permission"(guid)
    ON DELETE CASCADE
);

INSERT INTO "security"."permission" (description)
VALUES
    ('ADMIN'),
    ('MANAGER'),
    ('COMMON_USER');
