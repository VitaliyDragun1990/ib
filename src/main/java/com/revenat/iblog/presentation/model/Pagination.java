package com.revenat.iblog.presentation.model;

import java.util.ArrayList;
import java.util.List;

import com.revenat.iblog.application.domain.model.AbstractModel;
import com.revenat.iblog.application.infra.util.Checks;
import com.revenat.iblog.presentation.infra.config.Constants;

public class Pagination extends AbstractModel {
	private final String previousUrl;
	private final String nextUrl;
	private final List<PageItem> pages;

	private Pagination(String previousUrl, String nextUrl, List<PageItem> pages) {
		this.previousUrl = previousUrl;
		this.nextUrl = nextUrl;
		this.pages = pages;
	}
	
	public boolean isPrevious() {
		return previousUrl != null;
	}
	
	public boolean isNext() {
		return nextUrl != null;
	}

	public String getPreviousUrl() {
		return previousUrl;
	}

	public String getNextUrl() {
		return nextUrl;
	}

	public List<PageItem> getPages() {
		return pages;
	}
	
	public static class PageItem {
		private final String url;
		private final String caption;

		public PageItem(String url, String caption) {
			this.url = url;
			this.caption = caption;
		}

		public String getUrl() {
			return url;
		}

		public String getCaption() {
			return caption;
		}

		public boolean isEllipsis() {
			return url == null && caption == null;
		}
		
		public boolean isCurrent() {
			return url == null && caption != null;
		}
		
		public boolean isPageItem() {
			return url != null && caption != null;
		}
		
		public static PageItem createCurrent(String caption) {
			return new PageItem(null, caption);
		}
		
		public static PageItem createEllipsis() {
			return new PageItem(null, null);
		}
		
		public static PageItem createPageItems(String url, String caption) {
			return new PageItem(url, caption);
		}
	}
	
	public static class Builder extends AbstractModel {
		private String baseUrl;
		private int currentPage;
		private int maxItemsPerPage;
		private long totalNumberOfItems;
		private int maxPaginationButtonsPerPage;
		
		public Builder(String baseUrl, int currentPage, long totalNumberOfItems) {
			validate(currentPage, totalNumberOfItems);
			
			this.baseUrl = baseUrl;
			this.totalNumberOfItems = totalNumberOfItems;
			this.currentPage = currentPage;
			this.maxItemsPerPage = Constants.ITEMS_PER_PAGE;
			this.maxPaginationButtonsPerPage = Constants.MAX_PAGINATION_BUTTONS_PER_PAGE;
		}
		
		public Builder withMaxItemsPerPage(int maxItemsPerPage) {
			this.maxItemsPerPage = maxItemsPerPage;
			return this;
		}
		
		public Builder withMaxPaginationButtonsPerPage(int maxPaginationButtonsPerPage) {
			this.maxPaginationButtonsPerPage = maxPaginationButtonsPerPage;
			return this;
		}
		
		public Pagination build() {
			if (totalNumberOfItems <= maxItemsPerPage || currentPage > getMaxPage()) {
				return null;
			}
			
			String previousUrl = getPreviousUrl(currentPage);
			String nextUrl = getNextUrl(currentPage);
			int maxPage = getMaxPage();
			List<PageItem> pages;
			if (maxPage <= maxPaginationButtonsPerPage) {
				pages = createButtonsOnly(currentPage, maxPage);
			} else {
				int borderValue = (maxPaginationButtonsPerPage - 1) / 2;
				if (currentPage <(maxPaginationButtonsPerPage - borderValue)) {
					pages = createButtonsWithLastPageOnly(currentPage, maxPage);
				} else if (currentPage > maxPage - (maxPaginationButtonsPerPage - borderValue)) {
					pages = createButtonsWithFirstPageOnly(currentPage, maxPage);
				} else {
					pages = createButtonsWithMiddlePage(currentPage, maxPage);
				}
			}
			return new Pagination(previousUrl, nextUrl, pages);
		}
		
		private List<PageItem> createPageItems(int currentPage, int minPage, int maxPage) {
			List<PageItem> pages = new ArrayList<>();
			for (int page = minPage; page <= maxPage; page++) {
				if (currentPage == page) {
					pages.add(PageItem.createCurrent(String.valueOf(page)));
				} else {
					pages.add(PageItem.createPageItems(createUrlForPage(page), String.valueOf(page)));
				}
			}
			return pages;
		}

		private String createUrlForPage(int page) {
			if (page > 1) {
				return baseUrl + "page=" + page;
			} else {
				// Removes ? or & from url
				return baseUrl.substring(0, baseUrl.length()-1);
			}
		}
		
		private String getPreviousUrl(int currentPage) {
			if (currentPage > 1) {
				return baseUrl + "page=" + (currentPage-1);
			} else {
				return null;
			}
		}
		
		private String getNextUrl(int currentPage) {
			if ((currentPage * maxItemsPerPage) < totalNumberOfItems) {
				return baseUrl + "page=" + (currentPage + 1);
			} else {
				return null;
			}
		}
		
		private int getMaxPage() {
			int maxPage = (int) (totalNumberOfItems / maxItemsPerPage);
			if (totalNumberOfItems % maxItemsPerPage > 0) {
				maxPage++;
			}
			return maxPage;
		}
		
		private List<PageItem> createButtonsOnly(int currentPage, int maxPage) {
			return createPageItems(currentPage, 1, maxPage);
		}
		
		private List<PageItem> createButtonsWithLastPageOnly(int currentPage, int maxPage) {
			List<PageItem> pages = createPageItems(currentPage, 1, (maxPaginationButtonsPerPage - 2));
			pages.add(PageItem.createEllipsis());
			pages.add(PageItem.createPageItems(createUrlForPage(maxPage), String.valueOf(maxPage)));
			return pages;
		}
		
		private List<PageItem> createButtonsWithFirstPageOnly(int currentPage, int maxPage) {
			List<PageItem> pages = new ArrayList<>();
			pages.add(PageItem.createPageItems(createUrlForPage(1), "1"));
			pages.add(PageItem.createEllipsis());
			pages.addAll(createPageItems(currentPage, maxPage - (maxPaginationButtonsPerPage - 3), maxPage));
			return pages;
		}
		
		private List<PageItem> createButtonsWithMiddlePage(int currentPage, int maxPage) {
			int shiftValue = (maxPaginationButtonsPerPage - 5) / 2;
			List<PageItem> pages = new ArrayList<>();
			pages.add(PageItem.createPageItems(createUrlForPage(1), "1"));
			pages.add(PageItem.createEllipsis());
			pages.addAll(createPageItems(currentPage, currentPage - shiftValue, currentPage + shiftValue));
			pages.add(PageItem.createEllipsis());
			pages.add(PageItem.createPageItems(createUrlForPage(maxPage), String.valueOf(maxPage)));
			return pages;
		}
		private static void validate(int currentPage, long totalNumberOfItems) {
			Checks.checkParam(currentPage >= 1, "Page number cannot be less then 1: %d", currentPage);
			Checks.checkParam(totalNumberOfItems >= 0,
					"Total number of items count cannot be less then 0: %d", totalNumberOfItems);
			
		}
	}

}
