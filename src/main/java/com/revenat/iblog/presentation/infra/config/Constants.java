package com.revenat.iblog.presentation.infra.config;

public final class Constants {
	public static final int SESSION_MAX_INACTIVE_INTERVAL = 30 * 60;
	public static final int ITEMS_PER_PAGE = 10;
	public static final int MAX_COMMENTS_PER_PAGE = 10;
	public static final int MAX_PAGINATION_BUTTONS_PER_PAGE = 9;

	public static class Attribute {
		public static final String CURRENT_PAGE = "currentPage";
		public static final String STATUS_CODE = "statusCode";
		public static final String ERROR_MESSAGE = "errorMessage";
		public static final String CATEGORY_MAP = "CATEGORY_MAP";
//		public static final String SELECTED_CATEGORY_URL = "selectedCategoryUrl";
		public static final String ARTICLES = "articles";
		public static final String SEARCH_QUERY = "searchQuery";
		public static final String ARTICLE_COUNT = "articleCount";
		public static final String PAGINATION = "pagination";
		public static final String ARTICLE = "article";
		public static final String SELECTED_CATEGORY = "selectedCategory";
		public static final String IS_NEWS_PAGE = "isNewsPage";
		public static final String COMMENTS = "comments";
		public static final String SOCIAL_GOOGLEPLUS_CLIENT_ID = "social_googleplus_clientId";
		public static final String CONTACT_REQUEST_SUCCESS = "CONTACT_REQUEST_SUCCESS";
		public static final String SUCCESS = "success";

		private Attribute() {
		}
	}

	public static class Directory {
		public static final String JSP = "/WEB-INF/jsp/";
		public static final String FRAGMENT = JSP + "fragment/";
		public static final String PAGE = JSP + "page/";

		private Directory() {
		}
	}

	public static class Page {
		public static final String TEMPLATE = "page-template.jsp";
		public static final String ERROR = "error.jsp";
		public static final String NEWS = "news.jsp";
		public static final String ARTILCE = "article.jsp";
		public static final String CONTACT = "contact.jsp";
		public static final String ABOUT = "about.jsp";
		public static final String SEARCH = "search.jsp";

		private Page() {
		}
	}

	public static class Fragment {
		public static final String COMMENTS = "comments.jsp";
		
		private Fragment() {
		}
	}

	public static class URL {
		public static final String NEWS = "/news";
		public static final String NEWS_BY_CATEGORY = "/news/*";
		public static final String ARTICLE = "/article/*";
		public static final String CONTACT = "/contact";
		public static final String ABOUT = "/about";
		public static final String SEARCH = "/search";
		public static final String ERROR = "/error";
		public static final String AJAX_COMMENTS = "/ajax/html/comments";
		public static final String AJAX_COMMENT = "/ajax/html/comment";

		private URL() {
		}
	}


	private Constants() {
	}
}
