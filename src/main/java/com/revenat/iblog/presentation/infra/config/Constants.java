package com.revenat.iblog.presentation.infra.config;

public final class Constants {
	public static final int SESSION_MAX_INACTIVE_INTERVAL = 30 * 60;

	public static class Attribute {
		public static final String CURRENT_PAGE = "currentPage";

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

		private Page() {
		}
	}

	public static class Fragment {

		private Fragment() {
		}
	}

	public static class URL {
		public static final String NEWS = "/news";
		public static final String NEWS_BY_CATEGORY = "/news/*";

		private URL() {
		}
	}


	private Constants() {
	}
}
