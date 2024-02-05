package hu.andika.javaee.controller.admin.poi;

import hu.andika.javaee.model.poi.PoiService;
import hu.andika.javaee.model.user.UserServices;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/edit_poi")
public class EditPoiServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoiService poiService = new PoiService(request, response);
		poiService.editPoi();
	}

}
