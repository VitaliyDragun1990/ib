package com.revenat.iblog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.io.FileUtils;


/**
 * This class is responsible for generating dummy data (categories, articles,
 * comments, accounts) and inserting them into the database.
 * 
 * http://www.blindtextgenerator.com/lorem-ipsum - text generator: Lorem ipsum
 * ,1003 words https://placeimg.com/1000/400/tech - image generator with content
 * http://names.mongabay.com/male_names.htm - English names http://uifaces.com/
 * - User avatar generator
 * 
 * @author Vitaly Dragun
 *
 */
public class DatabaseDummyDataGenerator {
	private static final String INSERT_INTO_COMMENT = "INSERT INTO comment (account_id,article_id,content,created) "
			+ "VALUES (?,?,?,?)";
	private static final String INSERT_INTO_ACCOUNT = "INSERT INTO account (email,name,avatar,created) VALUES (?,?,?,?)";
	private static final String INSERT_INTO_ARTICLE = "INSERT INTO article (id,title,url,logo,\"desc\",content,category_id,"
			+ "created,views) VALUES(?,?,?,?,?,?,?,?,?)";
	private static final String INSERT_INTO_CATEGORY = "INSERT INTO category (id, name, url) VALUES (?,?,?)";
	private static final String FILE_WITH_DUMMY_TEXT = "external\\dummy-text.txt";
	private static final String FILE_WITH_DUMMY_NAMES = "external\\dummy-names.txt";
	private static final int NUMBER_OF_CATEGORIES = 10;
	private static final int MIN_ARTICLES_PER_CATEGORY = 1;
	private static final int MAX_ARTICLES_PER_CATEGORY = 30;
	private static final int START_ARTICLE_ID = 200;
	private static final String MEDIA_DIR_ABSOLUTE_PATH = "E:\\java\\eclipse_workspace_02\\iblog\\src\\main\\webapp\\media";

	private static final String JDBC_URL = "jdbc:postgresql://localhost/iblog";
	private static final String JDBC_USERNAME = "iblog";
	private static final String JDBC_PASSWORD = "password";

	private static final String[] IMG_THEMES = { "arch", "nature", "tech", "animals" };
	private static final String[] DUMMY_NAMES = readDummyNames();
	private static final String DUMMY_TEXT = readDummyText();
	private static final List<String> SENTENCES = new ArrayList<>();
	private static final List<String> WORDS = new ArrayList<>();
	private static final Random rand = new Random();
	private static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

	private static int idArticle = START_ARTICLE_ID;
	private static int articleCount = 0;
	private static int accountCount = 0;

	private static void init() {
		String[] sentences = DUMMY_TEXT.split("\\.");
		for (String sentence : sentences) {
			sentence = sentence.trim();
			if (sentence.length() > 0) {
				SENTENCES.add(sentence + ".");
			}
		}
		String[] words = DUMMY_TEXT.split(" ");
		for (String word : words) {
			word = word.replace(",", "").replaceAll("\\.", "").replaceAll(";", "").trim().toLowerCase();
			if (!WORDS.contains(word) && word.length() >= 4) {
				WORDS.add(word);
			}
		}
		Collections.shuffle(SENTENCES);
		Collections.shuffle(WORDS);
		System.out.println("SENTENCES SIZE=" + SENTENCES.size());
		System.out.println("WORDS SIZE=" + WORDS.size());
	}

	private static void clearDatabase(Connection conn) throws SQLException {
		try (Statement st = conn.createStatement()) {
			st.executeUpdate("delete from comment");
			st.executeUpdate("delete from article");
			st.executeUpdate("delete from account");
			st.executeUpdate("delete from category");
			st.executeQuery("select setval('account_id_seq', 1, false)");
			st.executeQuery("select setval('comment_id_seq', 1, false)");
			st.executeQuery("select setval('article_id_seq', 1, false)");
			st.executeQuery("select setval('category_id_seq', 1, false)");
		}
		conn.commit();
		System.out.println("Database data deleted");
	}

	private static int generateArticleCount() {
		int diff = rand.nextInt(3);
		switch (diff) {
		case 0:
			return rand.nextInt(2 * MIN_ARTICLES_PER_CATEGORY) + MIN_ARTICLES_PER_CATEGORY;
		case 1:
			return rand.nextInt(MAX_ARTICLES_PER_CATEGORY / 2) + MIN_ARTICLES_PER_CATEGORY;
		case 2:
			return rand.nextInt(MAX_ARTICLES_PER_CATEGORY) + MAX_ARTICLES_PER_CATEGORY / 2;
		}
		throw new IllegalStateException("This exception is not possible");
	}
	
	private static List<CategoryItem> generateCategories(Connection conn) throws SQLException {
		List<CategoryItem> categories = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(INSERT_INTO_CATEGORY)) {
			for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
				String url = WORDS.get(i);
				String name = Character.toUpperCase(url.charAt(0)) + url.substring(1);
				int articles = generateArticleCount();
				
				ps.setInt(1, i + 1);
				ps.setString(2, name);
				ps.setString(3, "/" + url);
				ps.addBatch();
				
				categories.add(new CategoryItem(i+1, articles));
				articleCount += articles;
			}
			ps.executeBatch();
			conn.commit();
		}
		System.out.println("Categories created: " + categories.size());
		return categories;
	}
	
	private static String createArticleUrl(String title) {
		return "/" + title.replace(" ", "-").replaceAll("\\.", "").replaceAll(",", "").toLowerCase().trim();
	}
	
	private static String generateArticleMainImageLink(String theme) throws IOException {
		String uid = UUID.randomUUID().toString() + ".jpg";
		String fileName = MEDIA_DIR_ABSOLUTE_PATH + "\\" + theme + "\\" + uid;
		downloadImageFromInternet("https://placeimg.com/1000/400/" + theme, fileName);
		return "/media/" + theme + "/" + uid;
	}
	
	private static String generateAccountAvatar() throws IOException {
		String uid = UUID.randomUUID().toString() + ".jpg";
		String fileName = MEDIA_DIR_ABSOLUTE_PATH + "\\avatar\\" + uid;
		downloadImageFromInternet("https://placeimg.com/80/80/people", fileName);
		return "/media/avatar/" + uid;
	}
	
	private static String generateContentImageHtml(String theme) throws IOException {
		int w = rand.nextInt(5) * 100 + 200;
		int h = rand.nextInt(10) * 20 + 100;
		String uid = UUID.randomUUID().toString() + ".jpg";
		String fileName = MEDIA_DIR_ABSOLUTE_PATH + "\\" + theme + "\\" + uid;
		downloadImageFromInternet("https://placeimg.com/" + w + "/" + h + "/" + theme, fileName);
		return "<img class=\"float-center\" src=\"/media/" + theme + "/" + uid +  "\""
				+ "alt=\"" + w + "x" + "\"/><br/>\n";
	}
	
	private static LocalDateTime generateCreatedTimestamp() {
		LocalDateTime now = LocalDateTime.now();
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int hour = Math.min(now.getHour(), random.nextInt(0, 24));
		int minute = Math.min(now.getMinute(), random.nextInt(0, 60));
		int day = Math.min(now.getDayOfMonth(), random.nextInt(1, 30));
		int month = Math.min(now.getMonthValue(), random.nextInt(1, 13));
		int year = now.getYear() - random.nextInt(1);
		return LocalDateTime.of(year, month, day, hour, minute);
	}
	
	private static LocalDateTime randomizeTimeForCreated(LocalDateTime timestamp) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int hour = Math.max(timestamp.getHour() - random.nextInt(24), 0);
		int minute = Math.max(timestamp.getMinute() - random.nextInt(60), 0);
		return LocalDateTime.of(timestamp.getYear(), timestamp.getMonth(), timestamp.getDayOfMonth(), hour, minute);
	}
	
	private static String generateArticleDesc(String title) {
		int repeat = rand.nextInt(5) + 1;
		StringBuilder desc = new StringBuilder("<p>");
		for (int i = 0; i < repeat; i++) {
			desc.append(title).append(" ");
		}
		if (desc.length() >= 255 - 5) {
			desc.delete(255-5, desc.length());
		}
		return desc.toString().trim() + "</p>";
	}
	
	private static void appendParagraph(StringBuilder content, List<String> sentences) {
		int repeat = rand.nextInt(9) + 1;
		for (int i = 0; i < repeat; i++) {
			Collections.shuffle(sentences);
			content.append("<p>");
			List<String> paragraph = sentences.subList(0, rand.nextInt(sentences.size()));
			for (String sentence : paragraph) {
				content.append(sentence).append(" ");
			}
			if (!paragraph.isEmpty()) {
				content.deleteCharAt(content.length() - 1);
			}
			content.append("</p>\n");
		}
	}
	
	private static String generateArticleContent(String title, String desc, String theme) throws IOException {
		boolean withImg = rand.nextBoolean();
		int imgCount = 0;
		if (withImg) {
			imgCount = rand.nextInt(4) + 1;
		}
		int otherSentences = rand.nextInt(14) + 6;
		List<String> sentences = new ArrayList<>();
		sentences.add(title);
		while (sentences.size() != otherSentences + 1) {
			String sentence = SENTENCES.get(rand.nextInt(SENTENCES.size()));
			if (!sentences.contains(sentence)) {
				sentences.add(sentence);
			}
		}
		StringBuilder content = new StringBuilder(desc + "\n");
		if (withImg) {
			for (int j = 0; j < imgCount; j++) {
				appendParagraph(content, sentences);
				content.append(generateContentImageHtml(theme));
				appendParagraph(content, sentences);
			}
		} else {
			appendParagraph(content, sentences);
		}
		return content.toString();
	}
	
	private static List<ArticleItem> generateArticles(Connection conn, List<CategoryItem> categories)
			throws SQLException, IOException {
		List<ArticleItem> articles = new ArrayList<>();
		try (PreparedStatement ps = conn.prepareStatement(INSERT_INTO_ARTICLE)) {
			while (!categories.isEmpty()) {
				CategoryItem item = categories.get(rand.nextInt(categories.size()));
				String title = SENTENCES.get(rand.nextInt(SENTENCES.size()));
				if (title.length() > 80) {
					title = title.substring(0, 80);
				}
				String theme = IMG_THEMES[rand.nextInt(IMG_THEMES.length)];
				String desc = generateArticleDesc(title);
				String content = generateArticleContent(title, desc, theme);
				int comments = rand.nextInt(accountCount);
				LocalDateTime created = generateCreatedTimestamp();
				
				ps.setLong(1, idArticle);
				ps.setString(2, title);
				ps.setString(3, createArticleUrl(title));
				ps.setString(4, generateArticleMainImageLink(theme));
				ps.setString(5, desc);
				ps.setString(6, content);
				ps.setInt(7, item.id);
				ps.setObject(8, created);
				ps.setLong(9, rand.nextInt(10000));
				
				ps.addBatch();
				
				articles.add(new ArticleItem(idArticle, comments, created));
				idArticle++;
				item.articles--;
				if (item.articles == 0) {
					categories.remove(item);
				}
				if (idArticle % 20 == 0) {
					System.out.println("Generated " + (idArticle - START_ARTICLE_ID) + " articles from " + articleCount);
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
		}
		System.out.println("Articles created: " + articles.size());
		return articles;
	}
	
	private static void generateAccounts(Connection conn) throws SQLException, IOException {
		List<String> names = new ArrayList<>(Arrays.asList(DUMMY_NAMES));
		try (PreparedStatement ps = conn.prepareStatement(INSERT_INTO_ACCOUNT)) {
			for (int i = 0; i < accountCount; i++) {
				String name = names.remove(rand.nextInt(names.size()));
				ps.setString(1, name.toLowerCase() + "@test.com");
				ps.setString(2, name);
				ps.setString(3, generateAccountAvatar());
				ps.setObject(4, generateCreatedTimestamp());
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
			System.out.println("Accounts created: " + accountCount);
		}
	}
	
	public static String generateCommentContent() {
		int sentencesCount = rand.nextInt(6) + 1;
		List<String> sentences = new ArrayList<>(SENTENCES);
		Collections.shuffle(sentences);
		StringBuilder content = new StringBuilder(sentences.remove(0));
		for (int i = 0; i < sentencesCount - 1; i++) {
			content.append(" ").append(sentences.remove(0));
		}
		return content.toString();
	}
	
	private static void generateComments(Connection conn, List<ArticleItem> articles) throws SQLException {
		int count = 0;
		try (PreparedStatement ps = conn.prepareStatement(INSERT_INTO_COMMENT)) {
			for (ArticleItem item : articles) {
				if (item.comments > 0) {
					List<Integer> idAccounts = new ArrayList<>();
					for (int i = 0; i < accountCount; i++) {
						idAccounts.add(i + 1);
					}
					Collections.shuffle(idAccounts);
					
					LocalDateTime startTime = item.created;
					LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault());
					int intervalBetweenCommentsIndays = 
							(int) (((toEpochMilli(endTime) - toEpochMilli(startTime)) / item.comments) / MILLIS_IN_DAY) + 1;
					
					for (int i = 0; i < item.comments; i++) {
						ps.setLong(2, item.id);
						Integer idAccount = idAccounts.remove(rand.nextInt(idAccounts.size()));
						ps.setLong(1, idAccount.longValue());
						ps.setString(3, generateCommentContent());
						LocalDateTime created = 
								LocalDateTime.ofInstant(Instant.ofEpochMilli(toEpochMilli(startTime)
										+ rand.nextInt(intervalBetweenCommentsIndays) * MILLIS_IN_DAY),
								ZoneId.systemDefault());
						ps.setObject(4, randomizeTimeForCreated(created));
						startTime = 
								LocalDateTime.ofInstant(Instant.ofEpochMilli(toEpochMilli(startTime)
										+ intervalBetweenCommentsIndays * MILLIS_IN_DAY),
								ZoneId.systemDefault());
						ps.addBatch();
						count++;
					}
					ps.executeBatch();
					conn.commit();
				}
			}
			System.out.println("Comments created: " + count);
		}
	}
	
	private static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File[] dirContent = file.listFiles();
				if (dirContent != null) {
					for (File f : dirContent) {
						deleteFile(f);
					}
				}
				file.delete();
			}
		}
	}
	
	private static void deleteMediaDir() {
		deleteFile(new File(MEDIA_DIR_ABSOLUTE_PATH));
	}
	
	private static long toEpochMilli(LocalDateTime timestamp) {
		return timestamp.toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	private static void downloadImageFromInternet(String url, String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try (InputStream in = new URL(url).openStream()) {
			Files.copy(in, Paths.get(fileName));
		}
	}
	
	public static void main(String[] args) {
		init();
		deleteMediaDir();
		try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
			conn.setAutoCommit(false);
			clearDatabase(conn);
			List<CategoryItem> categories = generateCategories(conn);
			accountCount = (articleCount * 1) / 5;
			System.out.println("TODO: articleCount=" + articleCount + ", accountCount=" + accountCount);
			generateAccounts(conn);
			List<ArticleItem> articles = generateArticles(conn, categories);
			generateComments(conn, articles);
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getNextException() != null) {
				e.getNextException().printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class CategoryItem {
		private final int id;
		private int articles;
		
		private CategoryItem(int id, int articles) {
			this.id = id;
			this.articles = articles;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CategoryItem other = (CategoryItem) obj;
			return id == other.id;
		}
	}
	
	private static class ArticleItem {
		private final int id;
		private final int comments;
		private final LocalDateTime created;
		
		public ArticleItem(int id, int comments, LocalDateTime created) {
			this.id = id;
			this.comments = comments;
			this.created = created;
		}
	}

	private static String[] readDummyNames() {
		try {
			return FileUtils.readFileToString(new File(FILE_WITH_DUMMY_NAMES), Charset.forName("UTF-8")).split(",");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String readDummyText() {
		try {
			return FileUtils.readFileToString(new File(FILE_WITH_DUMMY_TEXT), Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
