package com.revenat.iblog.presentation.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TrimmerTag extends SimpleTagSupport {
	private int limit;
	private String text;
	
	@Override
	public void doTag() throws JspException, IOException {
		if (text != null && text.length() > limit) {
			getJspContext().getOut().print(text.subSequence(0, limit-3) + "...");
		} else {
			getJspContext().getOut().print(text);
		}
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
