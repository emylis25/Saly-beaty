package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.Trabajador;
import org.esfe.BeatySaly.repositorios.ITrabajadorRepository;
import org.esfe.BeatySaly.servicios.interfaces.ITrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajadorService implements ITrabajadorService {

    @Autowired
    private ITrabajadorRepository trabajadorRepository;

    @Override
    public List<Trabajador> obtenerTodos() {
        return trabajadorRepository.findAll();
    }

    @Override
    public Page<Trabajador> buscarTodosPaginados(Pageable pageable) {
        return trabajadorRepository.findAll(pageable);
    }

    @Override
    public Trabajador obtenerPorId(Integer id) {
        Optional<Trabajador> trabajador = trabajadorRepository.findById(id);
        return trabajador.orElse(null);
    }

    @Override
    public Trabajador crear(Trabajador trabajador) {
        if (trabajador.getId() != 0) {
            throw new IllegalArgumentException("El trabajador nuevo no debe tener ID");
        }
        return trabajadorRepository.save(trabajador);
    }

    @Override
    public Trabajador actualizar(Trabajador trabajador) {
        if (trabajador.getId() == 0 || !trabajadorRepository.existsById(trabajador.getId())) {
            throw new IllegalArgumentException("El trabajador a actualizar no existe");
        }
        return trabajadorRepository.save(trabajador);
    }

    @Override
    public void eliminar(Integer id) {
        trabajadorRepository.deleteById(id);
    }

    @Override
    public Page<Trabajador> buscarPorNombre(String nombre, Pageable pageable) {
        return trabajadorRepository.findByNombreContainingIgnoreCase(nombre, pageable);
    }
}
