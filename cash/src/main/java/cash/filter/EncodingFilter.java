package cash.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

@WebFilter("/*") // 모든 서블릿을 가로챈다
public class EncodingFilter extends HttpFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8"); // 한글 깨지지 않도록 인코딩
		// db에 데이터를 보내는 작업 시 매번 필요하기 때문에 filter를 이용하면 중복되는 코드를 줄일 수 있다
		chain.doFilter(request, response);
	}
}
