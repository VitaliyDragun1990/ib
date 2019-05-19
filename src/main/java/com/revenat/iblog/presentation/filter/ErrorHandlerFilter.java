package com.revenat.iblog.presentation.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.json.JSONObject;

import com.revenat.iblog.application.infra.exception.ResourceNotFoundException;
import com.revenat.iblog.application.infra.exception.base.ApplicationException;
import com.revenat.iblog.application.infra.exception.flow.FlowException;
import com.revenat.iblog.application.infra.exception.flow.InvalidParameterException;
import com.revenat.iblog.presentation.infra.config.Constants.Attribute;
import com.revenat.iblog.presentation.infra.config.Constants.Page;
import com.revenat.iblog.presentation.infra.exception.AccessDeniedException;
import com.revenat.iblog.presentation.infra.util.RoutingUtils;
import com.revenat.iblog.presentation.infra.util.UrlUtils;

public class ErrorHandlerFilter extends AbstractFilter {

	private static final String INTERNAL_ERROR = "Internal error";

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, new ExceptionThrowerHttpServletResponse(response));
		} catch (Exception e) {
			processException(e, request, response);
			sendResponse(request, response, e);
		}

	}

	private void processException(Exception e, HttpServletRequest req, HttpServletResponse resp) {
		String requestUri = req.getRequestURI();
		int statusCode = getStatusCode(e);
		logException(e, requestUri, statusCode);
		
		if (statusCode == HttpServletResponse.SC_BAD_REQUEST || statusCode == HttpServletResponse.SC_NOT_FOUND) {
			req.setAttribute(Attribute.ERROR_MESSAGE, e.getMessage());
		}
		
		req.setAttribute(Attribute.STATUS_CODE, statusCode);
		resp.setStatus(statusCode);
	}

	private void logException(Exception e, String requestUri, int statusCode) {
		if (statusCode != HttpServletResponse.SC_BAD_REQUEST) {
			LOGGER.error("Request {} failed: {}", requestUri, e.getMessage(), e);
		} else {
			LOGGER.warn("Bad request {}: {}", requestUri, e.getMessage(), e);
		}
	}
	
	private void sendResponse(HttpServletRequest req, HttpServletResponse resp, Exception e) 
			throws IOException, ServletException {
		String requestUri = req.getRequestURI();
		if (UrlUtils.isAjaxJsonUrl(requestUri)) {
			String jsonResponse = getJsonResponse(e);
			RoutingUtils.sendJSON(jsonResponse, resp);
		} else if (UrlUtils.isAjaxHtmlUrl(requestUri)) {
			RoutingUtils.sendHtmlFragment(INTERNAL_ERROR, resp);
		} else {
			RoutingUtils.forwardToPage(Page.ERROR, req, resp);
		}
	}

	private String getJsonResponse(Exception e) {
		JSONObject json = new JSONObject();
		json.put("message", getMessage(getStatusCode(e), e.getMessage()));
		return json.toString();
	}

	private String getMessage(int statusCode, String badRequestMessage) {
		switch (statusCode) {
		case 400:
			return badRequestMessage;
		case 401:
			return "You must be authorized to view this resource";
		case 403:
			return "You don't have permissions to view this resource";
		case 404:
			return "Resource not found";
		default:
			return INTERNAL_ERROR;
		}
	}

	private int getStatusCode(Exception e) {
		if (e instanceof ApplicationException) {
			return ((ApplicationException) e).getCode();
		} else {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
	}
	
	/**
	 * This custom {@link HttpServletResponseWrapper} implementation throws
	 * exception in case of invalid request (method not implemented, page not exist,
	 * etc) instead of default behaviour (returning container-specific response
	 * page). This custom wrapper is needed in order to let our
	 * {@link ErrorHandlerFilter} filter handle all kind of exceptions application
	 * can thorw.
	 * 
	 * @author Vitaly Dragun
	 *
	 */
	private static class ExceptionThrowerHttpServletResponse extends HttpServletResponseWrapper {

		public ExceptionThrowerHttpServletResponse(HttpServletResponse response) {
			super(response);
		}

		@Override
		public void sendError(int sc) throws IOException {
			sendError(sc, INTERNAL_ERROR);
		}

		@Override
		public void sendError(int sc, String msg) throws IOException {
			switch (sc) {
			case 400:
				throw new InvalidParameterException(msg);
			case 403:
				throw new AccessDeniedException(msg);
			case 404:
				throw new ResourceNotFoundException(msg);
			default:
				throw new FlowException(msg, sc);
			}
		}
	}
}
