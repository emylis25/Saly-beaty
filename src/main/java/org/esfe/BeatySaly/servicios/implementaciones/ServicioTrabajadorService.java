package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.ServicioTrabajador;
import org.esfe.BeatySaly.repositorios.IServicioTrabajadorRepository;
import org.esfe.BeatySaly.servicios.interfaces.IServicioTrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioTrabajadorService implements IServicioTrabajadorService {

    @Autowired
    private IServicioTrabajadorRepository servicioTrabajadorRepository;

    @Override
    public List<ServicioTrabajador> obtenerTodos() {
        return servicioTrabajadorRepository.findAll();
    }

    @Override
    public ServicioTrabajador obtenerPorId(int id) {
        Optional<ServicioTrabajador> servicioTrabajador = servicioTrabajadorRepository.findById(id);
        return servicioTrabajador.orElse(null);
    }

    @Override
    public ServicioTrabajador crear(ServicioTrabajador servicioTrabajador) {
        if (servicioTrabajador.getId() != null && servicioTrabajador.getId() != 0) {
            throw new IllegalArgumentException("El nuevo servicio de trabajador no debe tener ID");
        }
        return servicioTrabajadorRepository.save(servicioTrabajador);
    }

    @Override
    public ServicioTrabajador actualizar(ServicioTrabajador servicioTrabajador) {
        if (servicioTrabajador.getId() == null || !servicioTrabajadorRepository.existsById(servicioTrabajador.getId())) {
            throw new IllegalArgumentException("El servicio de trabajador a actualizar no existe");
        }
        return servicioTrabajadorRepository.save(servicioTrabajador);
    }

    @Override
    public void eliminar(int id) {
        servicioTrabajadorRepository.deleteById(id);
    }

    @Override
    public Page<ServicioTrabajador> buscarPorTrabajadorYServicio(String nombreTrabajador, String nombreServicio, Pageable pageable) {
        return servicioTrabajadorRepository.findByTrabajadorNombreContainingIgnoreCaseAndServicioNombreServicioContainingIgnoreCase(
                nombreTrabajador, nombreServicio, pageable
        );
    }

    @Override
    public Page<ServicioTrabajador> buscarTodosPaginados(Pageable pageable) {
        return servicioTrabajadorRepository.findAll(pageable);
    }
}