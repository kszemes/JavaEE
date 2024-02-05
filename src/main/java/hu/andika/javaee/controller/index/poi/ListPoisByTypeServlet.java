package hu.andika.javaee.controller.index.poi;

import hu.andika.javaee.model.poi.PoiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/index/list_pois_by_type")
public class ListPoisByTypeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PoiService poiService = new PoiService(request, response);
		poiService.listPoisByType();
	}

}