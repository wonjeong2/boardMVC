package com.springbook.board.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springbook.board.user.UserVO;

public class checkInterceptor extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {

			System.out.println("인터셉터!!");

			HttpSession hs = request.getSession();
			UserVO loginUser = (UserVO) hs.getAttribute("loginUser");
			if (loginUser != null) {
				return true;
			}

			response.sendRedirect("/user/login");
			return false;
		}

	}

