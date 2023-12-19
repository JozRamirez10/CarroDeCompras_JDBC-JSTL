package webapp.service;

import webapp.models.Usuario;
import webapp.repositories.UsuarioRepository;
import webapp.repositories.UsuarioRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioServiceJdbcImpl implements UsuarioService{
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceJdbcImpl(Connection conn) {
        this.usuarioRepository = new UsuarioRepositoryJdbcImpl(conn);
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.porUsername(username))
                    .filter(u -> u.getPassword().equals(password));
        } catch (SQLException e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}
