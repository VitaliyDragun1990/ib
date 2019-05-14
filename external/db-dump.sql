-- SQL Manager Lite for PostgreSQL 5.9.4.51539
-- ---------------------------------------
-- Host      : localhost
-- Database  : iblog
-- Version   : PostgreSQL 9.6.1, compiled by Visual C++ build 1800, 64-bit



SET search_path = public, pg_catalog;
DROP INDEX IF EXISTS public.comment_account_id_idx;
DROP INDEX IF EXISTS public.article_category_id_idx;
DROP INDEX IF EXISTS public.comment_article_id_idx;
DROP TABLE IF EXISTS public.comment;
DROP TABLE IF EXISTS public.account;
DROP TABLE IF EXISTS public.article;
DROP TABLE IF EXISTS public.category;
SET check_function_bodies = false;
--
-- Structure for table category (OID = 42264) : 
--
CREATE TABLE public.category (
    id serial NOT NULL,
    name varchar(20) NOT NULL,
    url varchar(20) NOT NULL,
    articles integer DEFAULT 0 NOT NULL
)
WITH (oids = false);
--
-- Structure for table article (OID = 42275) : 
--
CREATE TABLE public.article (
    id bigserial NOT NULL,
    title varchar(255) NOT NULL,
    url varchar(255) NOT NULL,
    logo varchar(255) NOT NULL,
    "desc" varchar(255) NOT NULL,
    content text NOT NULL,
    category_id integer NOT NULL,
    created timestamp(0) without time zone DEFAULT now() NOT NULL,
    views bigint DEFAULT 0 NOT NULL,
    comments integer DEFAULT 0 NOT NULL
)
WITH (oids = false);
--
-- Structure for table account (OID = 42289) : 
--
CREATE TABLE public.account (
    id bigserial NOT NULL,
    email varchar(100) NOT NULL,
    name varchar(30) NOT NULL,
    avatar varchar(255),
    created timestamp(0) without time zone DEFAULT now() NOT NULL
)
WITH (oids = false);
--
-- Structure for table comment (OID = 42300) : 
--
CREATE TABLE public.comment (
    id bigserial NOT NULL,
    account_id bigint NOT NULL,
    article_id bigint NOT NULL,
    content text NOT NULL,
    created timestamp(0) without time zone DEFAULT now() NOT NULL
)
WITH (oids = false);
--
-- Definition for index comment_article_id_idx (OID = 42325) : 
--
CREATE INDEX comment_article_id_idx ON comment USING btree (article_id);
--
-- Definition for index article_category_id_idx (OID = 42326) : 
--
CREATE INDEX article_category_id_idx ON article USING btree (category_id);
--
-- Definition for index comment_account_id_idx (OID = 42327) : 
--
CREATE INDEX comment_account_id_idx ON comment USING btree (account_id);
--
-- Definition for index category_pkey (OID = 42269) : 
--
ALTER TABLE ONLY category
    ADD CONSTRAINT category_pkey
    PRIMARY KEY (id);
--
-- Definition for index category_url_key (OID = 42271) : 
--
ALTER TABLE ONLY category
    ADD CONSTRAINT category_url_key
    UNIQUE (url);
--
-- Definition for index article_pkey (OID = 42285) : 
--
ALTER TABLE ONLY article
    ADD CONSTRAINT article_pkey
    PRIMARY KEY (id);
--
-- Definition for index account_pkey (OID = 42294) : 
--
ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey
    PRIMARY KEY (id);
--
-- Definition for index account_email_key (OID = 42296) : 
--
ALTER TABLE ONLY account
    ADD CONSTRAINT account_email_key
    UNIQUE (email);
--
-- Definition for index comment_pkey (OID = 42308) : 
--
ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_pkey
    PRIMARY KEY (id);
--
-- Definition for index article_fk (OID = 42310) : 
--
ALTER TABLE ONLY article
    ADD CONSTRAINT article_fk
    FOREIGN KEY (category_id) REFERENCES category(id) ON UPDATE CASCADE ON DELETE RESTRICT;
--
-- Definition for index comment_fk (OID = 42315) : 
--
ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_fk
    FOREIGN KEY (account_id) REFERENCES account(id) ON UPDATE CASCADE ON DELETE RESTRICT;
--
-- Definition for index comment_fk1 (OID = 42320) : 
--
ALTER TABLE ONLY comment
    ADD CONSTRAINT comment_fk1
    FOREIGN KEY (article_id) REFERENCES article(id) ON UPDATE CASCADE ON DELETE RESTRICT;
--
-- Data for sequence public.category_id_seq (OID = 42262)
--
SELECT pg_catalog.setval('category_id_seq', 1, false);
--
-- Data for sequence public.article_id_seq (OID = 42273)
--
SELECT pg_catalog.setval('article_id_seq', 1, false);
--
-- Data for sequence public.account_id_seq (OID = 42287)
--
SELECT pg_catalog.setval('account_id_seq', 1, false);
--
-- Data for sequence public.comment_id_seq (OID = 42298)
--
SELECT pg_catalog.setval('comment_id_seq', 1, false);
--
-- Comments
--
COMMENT ON SCHEMA public IS 'standard public schema';
