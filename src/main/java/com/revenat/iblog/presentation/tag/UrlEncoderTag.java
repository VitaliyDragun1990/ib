package com.revenat.iblog.presentation.tag;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class UrlEncoderTag extends SimpleTagSupport {
	private String url;
	
	@Override
	public void doTag() throws JspException, IOException {
		if (url != null) {
			getJspContext().getOut().print(URLEncoder.encode(url, "UTF-8"));
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}
