-- SQL Manager Lite for PostgreSQL 5.9.4.51539
-- ---------------------------------------
-- Host      : localhost
-- Database  : iblog
-- Version   : PostgreSQL 9.6.1, compiled by Visual C++ build 1800, 64-bit



SET search_path = public, pg_catalog;
DROP TRIGGER IF EXISTS articledelete ON public.article;
DROP TRIGGER IF EXISTS articleinsert ON public.article;
DROP TRIGGER IF EXISTS articleupdate ON public.article;
DROP TRIGGER IF EXISTS commentdelete ON public.comment;
DROP TRIGGER IF EXISTS commentinsert ON public.comment;
DROP TRIGGER IF EXISTS commentupdate ON public.comment;
DROP INDEX IF EXISTS public.comment_account_id_idx;
DROP INDEX IF EXISTS public.article_category_id_idx;
DROP INDEX IF EXISTS public.comment_article_id_idx;
DROP FUNCTION IF EXISTS public.article_count_delete_article ();
DROP FUNCTION IF EXISTS public.article_count_insert_article ();
DROP FUNCTION IF EXISTS public.article_count_update_article ();
DROP FUNCTION IF EXISTS public.comment_count_delete_comment ();
DROP FUNCTION IF EXISTS public.comment_count_insert_comment ();
DROP FUNCTION IF EXISTS public.comment_count_update_comment ();
DROP TABLE IF EXISTS public.comment;
DROP TABLE IF EXISTS public.account;
DROP TABLE IF EXISTS public.article;
DROP TABLE IF EXISTS public.category;
SET check_function_bodies = false;
--
-- Definition for function comment_count_update_comment (OID = 42328) : 
--
CREATE FUNCTION public.comment_count_update_comment (
)
RETURNS trigger
AS 
$body$
BEGIN
	IF OLD.article_id != NEW.article_id THEN
		UPDATE article SET comments = comments-1
			WHERE id = OLD.article_id;
		UPDATE article SET comments = comments+1
			WHERE id = NEW.article_id;
	END IF;
	RETURN NEW;
END;
$body$
LANGUAGE plpgsql;
--
-- Definition for function comment_count_insert_comment (OID = 42329) : 
--
CREATE FUNCTION public.comment_count_insert_comment (
)
RETURNS trigger
AS 
$body$
BEGIN
	UPDATE article SET comments = comments+1
		WHERE id = NEW.article_id;
	RETURN NEW;
END;
$body$
LANGUAGE plpgsql;
--
-- Definition for function comment_count_delete_comment (OID = 42330) : 
--
CREATE FUNCTION public.comment_count_delete_comment (
)
RETURNS trigger
AS 
$body$
BEGIN
	UPDATE article SET comments = comments-1
		WHERE id = OLD.article_id;
	RETURN OLD;
END;
$body$
LANGUAGE plpgsql;
--
-- Definition for function article_count_update_article (OID = 42331) : 
--
CREATE FUNCTION public.article_count_update_article (
)
RETURNS trigger
AS 
$body$
BEGIN
	IF OLD.category_id != NEW.category_id THEN
		UPDATE category SET articles = articles-1
			WHERE id = OLD.category_id;
		UPDATE category SET articles = articles+1
			WHERE id = NEW.category_id;
	END IF;
	RETURN NEW;
END;
$body$
LANGUAGE plpgsql;
--
-- Definition for function article_count_insert_article (OID = 42332) : 
--
CREATE FUNCTION public.article_count_insert_article (
)
RETURNS trigger
AS 
$body$
BEGIN
	UPDATE category SET articles = articles+1
		WHERE id = NEW.category_id;
	RETURN NEW;
END;
$body$
LANGUAGE plpgsql;
--
-- Definition for function article_count_delete_article (OID = 42333) : 
--
CREATE FUNCTION public.article_count_delete_article (
)
RETURNS trigger
AS 
$body$
BEGIN
	UPDATE category SET articles = articles-1
		WHERE id = OLD.category_id;
	RETURN OLD;
END;
$body$
LANGUAGE plpgsql;
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
-- Definition for trigger commentupdate (OID = 42334) : 
--
CREATE TRIGGER commentupdate
    AFTER UPDATE ON comment
    FOR EACH ROW
    EXECUTE PROCEDURE comment_count_update_comment ();
--
-- Definition for trigger commentinsert (OID = 42335) : 
--
CREATE TRIGGER commentinsert
    AFTER INSERT ON comment
    FOR EACH ROW
    EXECUTE PROCEDURE comment_count_insert_comment ();
--
-- Definition for trigger commentdelete (OID = 42336) : 
--
CREATE TRIGGER commentdelete
    AFTER DELETE ON comment
    FOR EACH ROW
    EXECUTE PROCEDURE comment_count_delete_comment ();
--
-- Definition for trigger articleupdate (OID = 42337) : 
--
CREATE TRIGGER articleupdate
    AFTER UPDATE ON article
    FOR EACH ROW
    EXECUTE PROCEDURE article_count_update_article ();
--
-- Definition for trigger articleinsert (OID = 42338) : 
--
CREATE TRIGGER articleinsert
    AFTER INSERT ON article
    FOR EACH ROW
    EXECUTE PROCEDURE article_count_insert_article ();
--
-- Definition for trigger articledelete (OID = 42339) : 
--
CREATE TRIGGER articledelete
    AFTER DELETE ON article
    FOR EACH ROW
    EXECUTE PROCEDURE article_count_delete_article ();
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
