package webapp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import webapp.service.ServiceJdbcException;
import webapp.util.ConexionBaseDatos;
import webapp.util.ConexionBaseDatosDS;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter("/*") // Se aplica al proyecto completo
public class ConexionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // try (Connection conn = ConexionBaseDatos.getConnection()){ // Realiza la conexión a la base de datos
        try(Connection conn = ConexionBaseDatosDS.getConnection()){ // Pool de conexiones
            if(conn.getAutoCommit()) // Verifica el autocommit
                conn.setAutoCommit(false); // Lo coloca en false
            try{
                servletRequest.setAttribute("conn", conn); // Da como atributo al request la conexión a la base de datos
                filterChain.doFilter(servletRequest, servletResponse); // filtra la conexión
                conn.commit(); // Si sale bien, realiza el commit
            }catch(SQLException | ServiceJdbcException e){ // En caso de que el request lance una expcetion
                conn.rollback(); // Realiza rollback
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        e.getMessage()); // Muestra el mensaje de error
                throw new RuntimeException(e);
            }
        } catch (SQLException | NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
