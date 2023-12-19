package webapp.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webapp.models.Carro;
import webapp.models.ItemCarro;
import webapp.models.Producto;
import webapp.service.ProductoService;
import webapp.service.ProductoServiceImpl;
import webapp.service.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/carro/agregar")
public class AgregarCarroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id")); // Obtiene el parametro id por get
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);
        Optional<Producto> producto = service.porId(id); // Obtiene el producto por id
        if(producto.isPresent()){
            ItemCarro item = new ItemCarro(1, producto.get()); // Crea un producto dentro del carro
            HttpSession session = req.getSession(); // Obtiene la session
            Carro carro = (Carro) session.getAttribute("carro"); // Obtiene el atributo carro
            carro.addItemCarro(item); // Lo guarda dentro de carro (List)
        }
        resp.sendRedirect(req.getContextPath() + "/carro/ver"); // Redirige la página
    }
}
