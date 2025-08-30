package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.Cita;
import org.esfe.BeatySaly.repositorios.ICitaRepository;
import org.esfe.BeatySaly.servicios.interfaces.ICitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService implements ICitaService {

    @Autowired
    private ICitaRepository citaRepository;

    @Override
    public List<Cita> obtenerTodos() {
        return citaRepository.findAll();
    }

    @Override
    public Page<Cita> buscarTodosPaginados(Pageable pageable) {
        return citaRepository.findAll(pageable);
    }

    @Override
    public Cita buscarPorId(int id) {
        Optional<Cita> cita = citaRepository.findById(id);
        return cita.orElse(null);
    }

    @Override
    public Cita crear(Cita cita) {
        if (cita.getId() != 0) {
            throw new IllegalArgumentException("La cita nueva no debe tener ID");
        }
        return citaRepository.save(cita);
    }

    @Override
    public Cita editar(Cita cita) {
        if (cita.getId() == 0 || !citaRepository.existsById(cita.getId())) {
            throw new IllegalArgumentException("La cita a editar no existe");
        }
        return citaRepository.save(cita);
    }

    @Override
    public void eliminarPorId(int id) {
        citaRepository.deleteById(id);
    }

    @Override
    public Page<Cita> buscarPorNombreTrabajadorYCliente(String nombreTrabajador, String nombreCliente, Pageable pageable) {
        return citaRepository.findByTrabajador_NombreContainingIgnoreCaseAndCliente_NombreContainingIgnoreCase(
                nombreTrabajador, nombreCliente, pageable
        );
    }
}
