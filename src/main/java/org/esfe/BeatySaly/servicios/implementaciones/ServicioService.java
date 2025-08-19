package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.Servicio;
import org.esfe.BeatySaly.repositorios.IServicioRepository;
import org.esfe.BeatySaly.servicios.interfaces.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepository;

    @Override
    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    @Override
    public Page<Servicio> buscarTodosPaginados(Pageable pageable) {
        return servicioRepository.findAll(pageable);
    }

    @Override
    public Servicio buscarPorId(int id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);
        return servicio.orElse(null);
    }

    @Override
    public Servicio crear(Servicio servicio) {
        if (servicio.getId() != 0) {
            throw new IllegalArgumentException("El servicio nuevo no debe tener ID");
        }
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio editar(Servicio servicio) {
        if (servicio.getId() == 0 || !servicioRepository.existsById(servicio.getId())) {
            throw new IllegalArgumentException("El servicio a editar no existe");
        }
        return servicioRepository.save(servicio);
    }

    @Override
    public void eliminarPorId(int id) {
        servicioRepository.deleteById(id);
    }

    @Override
    public Page<Servicio> buscarPorNombreServicio(String nombreServicio, Pageable pageable) {
        return servicioRepository.findByNombreServicioContainingIgnoreCase(nombreServicio, pageable);
    }
}

