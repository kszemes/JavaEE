package hu.andika.javaee.controller.admin.poi;

import hu.andika.javaee.model.poi.PoiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/update_poi")
public class UpdatePoiServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoiService poiService = new PoiService(request, response);
		poiService.updatePoi();
	}

}
