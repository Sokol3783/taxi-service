package org.example.controllers.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Encoding implements Filter {

  private String encoding;

  @Override
  public void init(FilterConfig config) {
    encoding = "UTF-8";
  }

  @Override
  public void doFilter(
      ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request.getCharacterEncoding() == null) {
      request.setCharacterEncoding(encoding);
    }
    chain.doFilter(request, response);
  }

}
