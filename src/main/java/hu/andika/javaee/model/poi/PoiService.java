package hu.andika.javaee.model.poi;

import hu.andika.javaee.model.comment.Comment;
import hu.andika.javaee.model.comment.CommentDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PoiService {

    private final PoiDao poiDao;
    private final CommentDao commentDao;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Set<String> locations;
    private final Set<String> types;

    public PoiService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.poiDao = new PoiDao();
        this.commentDao = new CommentDao();
        this.locations = poiDao.getLocations();
        this.types = poiDao.getTypes();
    }

    public void listPois(String message) throws ServletException, IOException {
        List<Poi> pois = poiDao.readAll();
        request.setAttribute("pois", pois);
        request.setAttribute("locations", locations);
        request.setAttribute("types", types);
        if (message != null) {
            request.setAttribute("message", message);
        }
        String listPage = "pois_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
        requestDispatcher.forward(request, response);
    }

    public void listPoisByLocation() throws ServletException, IOException {
        String location = request.getParameter("location");
        List<Poi> pois = poiDao.getPoisByLocation(location);
        request.setAttribute("pois", pois);
        request.setAttribute("locations", locations);
        request.setAttribute("types", types);
        String listPage = "pois_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
        requestDispatcher.forward(request, response);
    }

    public void listPoisByType() throws ServletException, IOException {
        String type = request.getParameter("type");
        List<Poi> pois = poiDao.getPoisByType(type);
        request.setAttribute("pois", pois);
        request.setAttribute("locations", locations);
        request.setAttribute("types", types);
        String listPage = "pois_list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
        requestDispatcher.forward(request, response);
    }

    public void showNewPoiForm() throws ServletException, IOException {
        String newPoiPage = "poi_form.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(newPoiPage);
        requestDispatcher.forward(request, response);
    }

    public void createPoi() throws ServletException, IOException {
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String type = request.getParameter("type");
        Poi poi = new Poi(name, location, type);
        poiDao.create(poi);
        String message = poi.getName() + " has been created successfully";
        request.setAttribute("message", message);
        listPois(message);
    }

    public void editPoi() throws ServletException, IOException {
        Integer poiId = Integer.parseInt(request.getParameter("id"));
        Optional<Poi> optionalPoi = poiDao.readById(poiId);
        if (optionalPoi.isPresent()) {
            Poi poi = optionalPoi.get();
            String editPage = "poi_form.jsp";
            request.setAttribute("poi", poi);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
            requestDispatcher.forward(request, response);
        }
    }

    public void updatePoi() throws ServletException, IOException {
        Integer poiId = Integer.parseInt(request.getParameter("id"));
        Optional<Poi> optionalPoi = poiDao.readById(poiId);
        if (optionalPoi.isPresent()) {
            Poi poi = optionalPoi.get();
            String name = request.getParameter("name");
            String location = request.getParameter("location");
            String type = request.getParameter("type");
            Poi updatedPoi = new Poi(poiId, name, location, type, poi.getLikes());
            poiDao.update(updatedPoi);
            String message = updatedPoi.getName() + " has been updated successfully";
            request.setAttribute("message", message);
            listPois(message);
        } else {
            String message = "Could not update Poi. Something went wrong.";
            request.setAttribute("message", message);
            listPois(message);
        }
    }

    public void deletePoi() throws ServletException, IOException {
        Integer poiId = Integer.parseInt(request.getParameter("id"));
        for (Comment comment : commentDao.readAllByPoiId(poiId)) {
            System.out.println("Comment to be deleted: "+comment);
            commentDao.deleteById(comment.getId());
        }
        poiDao.deleteById(poiId);
        String message = "Poi has been deleted successfully";
        listPois(message);
    }

    public void likePoiById() throws ServletException, IOException {
        Integer poiId = Integer.parseInt(request.getParameter("id"));
        Optional<Poi> optionalPoi = poiDao.readById(poiId);
        if (optionalPoi.isPresent()) {
            Poi poi = optionalPoi.get();
            poi.setLikes(poi.getLikes() + 1);
            poiDao.update(poi);
            String message = poi.getName() + " has been liked successfully";
            request.setAttribute("message", message);
            listPois(message);
        }
    }
}