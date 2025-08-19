package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.Administrador;
import org.esfe.BeatySaly.repositorios.IAdministradorRepository;
import org.esfe.BeatySaly.servicios.interfaces.IAdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService  implements IAdministradorService {

    @Autowired
    private IAdministradorRepository administradorRepository;

    @Override
    public List<Administrador> obtenerTodos() {
        return administradorRepository.findAll();
    }

    @Override
    public Page<Administrador> obtenerTodosPaginados(Pageable pageable) {
        return administradorRepository.findAll(pageable);
    }

    @Override
    public Administrador obtenerPorId(int id) {
        Optional<Administrador> administrador = administradorRepository.findById(id);
        return administrador.orElse(null);
    }

    @Override
    public Administrador crear(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public Administrador actualizar(Administrador administrador) {
        if (administrador.getId() != 0 && administradorRepository.existsById(administrador.getId())) {
            return administradorRepository.save(administrador);
        }
        return null;
    }

    @Override
    public void eliminar(int id) {
        if (administradorRepository.existsById(id)) {
            administradorRepository.deleteById(id);
        }
    }

    @Override
    public Administrador buscarPorcorreo(String correo) {
        return administradorRepository.findByCorreo(correo);
    }
}
