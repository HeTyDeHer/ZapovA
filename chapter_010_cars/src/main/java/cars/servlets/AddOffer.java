package cars.servlets;

import cars.dao.HibernateDAO;
import cars.models.*;
import com.google.gson.Gson;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Add new offer to database.
 * Saves images to web app directory/uploadFiles/user_id.
 */

@MultipartConfig(fileSizeThreshold=1024*1024*2,
        maxFileSize=1024*1024*10,
        maxRequestSize=1024*1024*50)   
public class AddOffer extends HttpServlet {

    /**
     * Name of the directory where uploaded files will be saved, relative to
     * the web application directory.
     */
    private static final String SAVE_DIR = "uploadFiles";

    @Override
    public void init(ServletConfig config) throws ServletException {
        String dir = config.getServletContext().getRealPath("") + SAVE_DIR;
        File saveDir = new File(dir);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        super.init(config);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean response = false;
        User user = (User) req.getSession().getAttribute("user");
        String modelid = req.getParameter("model");
        String bodyid = req.getParameter("body");
        String engineid = req.getParameter("engine");
        String gearboxid = req.getParameter("gearbox");
        String description = req.getParameter("description");
        if (user != null && modelid != null && bodyid != null && engineid != null && gearboxid != null && description != null) {
            HibernateDAO hibernateDAO = new HibernateDAO();
            int author_id = user.getId();
            Map<String, Object> hm = new HashMap<>();
            hm.put("id", Integer.valueOf(modelid));
            Model model = (Model) hibernateDAO.read(Model.class, hm).get(0);
            hm = new HashMap<>();
            hm.put("id", Integer.valueOf(bodyid));
            Body body = (Body) hibernateDAO.read(Body.class, hm).get(0);
            hm = new HashMap<>();
            hm.put("id", Integer.valueOf(engineid));
            Engine engine = (Engine) hibernateDAO.read(Engine.class, hm).get(0);
            hm = new HashMap<>();
            hm.put("id", Integer.valueOf(gearboxid));
            Gearbox gearbox = (Gearbox) hibernateDAO.read(Gearbox.class, hm).get(0);
            CarOffer carOffer = new CarOffer(model, body, engine, gearbox, description, user);

            String savePath = req.getServletContext().getRealPath("") + SAVE_DIR + File.separator + author_id;
            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                System.out.println(fileSaveDir.mkdir());
            }
            for (Part part : req.getParts()) {
                if ("file".equals(part.getName())) {
                    String fileName = part.getSubmittedFileName();
                    fileName = new File(fileName).getName();
                    String path = savePath + File.separator + fileName;
                    part.write(path);
                    String relativePath = req.getContextPath() + SAVE_DIR + File.separator + author_id + File.separator + fileName;
                    carOffer.getImages().add(relativePath);
                }
            }
            hibernateDAO.create(carOffer);
            response = true;
        }
        String result = new Gson().toJson(response);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

}
