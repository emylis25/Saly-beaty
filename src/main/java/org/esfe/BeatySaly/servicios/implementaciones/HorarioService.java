package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.Horario;
import org.esfe.BeatySaly.repositorios.IHorarioRepository;
import org.esfe.BeatySaly.servicios.interfaces.IHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService implements IHorarioService {

    @Autowired
    private IHorarioRepository horarioRepository;

    @Override
    public List<Horario> obtenerTodos() {
        return horarioRepository.findAll();
    }

    @Override
    public Page<Horario> buscarTodosPaginados(Pageable pageable) {
        return horarioRepository.findAll(pageable);
    }

    @Override
    public Horario obtenerPorId(Integer id) {
        Optional<Horario> horario = horarioRepository.findById(id);
        return horario.orElse(null);
    }

    @Override
    public Horario crear(Horario horario) {
        if (horario.getId() != 0) {
            throw new IllegalArgumentException("El horario nuevo no debe tener ID");
        }
        return horarioRepository.save(horario);
    }

    @Override
    public Horario actualizar(Horario horario) {
        if (horario.getId() == 0 || !horarioRepository.existsById(horario.getId())) {
            throw new IllegalArgumentException("El horario a actualizar no existe");
        }
        return horarioRepository.save(horario);
    }

    @Override
    public void eliminar(Integer id) {
        horarioRepository.deleteById(id);
    }

    @Override
    public Page<Horario> buscarPorTrabajadorYDia(String nombreTrabajador, String diaSemana, Pageable pageable) {
        return horarioRepository.findByTrabajadorNombreContainingIgnoreCaseAndDiaSemanaContainingIgnoreCase(
                nombreTrabajador, diaSemana, pageable
        );
    }
}