package com.eazow.blog.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eazow.blog.dao.factory.DAOFactory;
import com.eazow.blog.entity.Admin;
import com.eazow.blog.entity.Category;
import com.eazow.blog.service.CategoryService;


@SuppressWarnings("serial")
public class UpdateCategoryNameAdminServlet extends HttpServlet
{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Admin admin = (Admin)session.getAttribute("admin");
		if(null == admin)
		{
			request.setAttribute("usernameErrorMessage", "���¼");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		String categoryIdStr = request.getParameter("categoryId");
		String categoryName = request.getParameter("categoryName");
		if(null==categoryIdStr || "".equals(categoryIdStr) || null==categoryName || "".equals(categoryName))
		{
			response.sendError(400, "Input Violation");
			return;
		}
		int categoryId = 0;
		try
		{
			categoryId = Integer.parseInt(categoryIdStr);
		}
		catch(NumberFormatException e)
		{
			response.sendError(400, "Input Violation");
			return;
		}
		CategoryService categoryService = DAOFactory.getCategoryServiceInstance();
		Category category = categoryService.getCategory(categoryId);
		if(null == category)
		{
			response.sendError(400, "Input Violation");
			return;
		}
		category.setName(categoryName);
		categoryService.updateCategory(category);
		
		response.sendRedirect("manageCategoriesAdminServlet");
	}

}
