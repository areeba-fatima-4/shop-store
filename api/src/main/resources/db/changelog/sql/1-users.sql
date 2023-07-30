create type user_type as enum (
    'ADMIN',
    'SELLER',
    'BUYER'
);

CREATE TABLE users (
	"id" uuid NOT NULL UNIQUE,
	"first_name" varchar(100) NOT NULL,
	"last_name" varchar(100) NOT NULL,
	"phone" varchar(20) NULL,
	"email_address" varchar(50) NOT NULL UNIQUE,
	"password" varchar(50),
    "active" boolean DEFAULT true,
    "type" user_type not null,
    "created_by" int8 NULL,
	"created_date" timestamp NULL DEFAULT now(),
	"modified_by" int8 NULL,
	"modified_date" timestamp NULL DEFAULT now()
);
