package com.revenat.iblog.presentation.infra.config;

public final class Constants {
	public static final int SESSION_MAX_INACTIVE_INTERVAL = 30 * 60;

	public static class Attribute {

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

		private Page() {
		}
	}

	public static class Fragment {

		private Fragment() {
		}
	}

	public static class URL {

		private URL() {
		}
	}


	private Constants() {
	}
}
