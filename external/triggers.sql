-- TRIGGER CREATION STATEMENTS --
 -- article.comments related trigger functions
CREATE OR REPLACE FUNCTION comment_count_update_comment()
	RETURNS TRIGGER AS
$$
BEGIN
	IF OLD.article_id != NEW.article_id THEN
		UPDATE article SET comments = comments-1
			WHERE id = OLD.article_id;
		UPDATE article SET comments = comments+1
			WHERE id = NEW.article_id;
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION comment_count_insert_comment()
	RETURNS TRIGGER AS
$$
BEGIN
	UPDATE article SET comments = comments+1
		WHERE id = NEW.article_id;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION comment_count_delete_comment()
	RETURNS TRIGGER AS
$$
BEGIN
	UPDATE article SET comments = comments-1
		WHERE id = OLD.article_id;
	RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

-- category.articles related trigger functions
CREATE OR REPLACE FUNCTION article_count_update_article()
	RETURNS TRIGGER AS
$$
BEGIN
	IF OLD.category_id != NEW.category_id THEN
		UPDATE category SET articles = articles-1
			WHERE id = OLD.category_id;
		UPDATE category SET articles = articles+1
			WHERE id = NEW.category_id;
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION article_count_insert_article()
	RETURNS TRIGGER AS
$$
BEGIN
	UPDATE category SET articles = articles+1
		WHERE id = NEW.category_id;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION article_count_delete_article()
	RETURNS TRIGGER AS
$$
BEGIN
	UPDATE category SET articles = articles-1
		WHERE id = OLD.category_id;
	RETURN OLD;
END;
$$
LANGUAGE 'plpgsql';

-- Trigger definitions
CREATE TRIGGER commentUpdate
	AFTER UPDATE ON comment FOR EACH ROW
	EXECUTE PROCEDURE comment_count_update_comment();

CREATE TRIGGER commentInsert
	AFTER INSERT ON comment FOR EACH ROW
	EXECUTE PROCEDURE comment_count_insert_comment();

CREATE TRIGGER commentDelete
	AFTER DELETE ON comment FOR EACH ROW
	EXECUTE PROCEDURE comment_count_delete_comment();

CREATE TRIGGER articleUpdate
	AFTER UPDATE ON article FOR EACH ROW
	EXECUTE PROCEDURE article_count_update_article();

CREATE TRIGGER articleInsert
	AFTER INSERT ON article FOR EACH ROW
	EXECUTE PROCEDURE article_count_insert_article();

CREATE TRIGGER articleDelete
	AFTER DELETE ON article FOR EACH ROW
	EXECUTE PROCEDURE article_count_delete_article();
