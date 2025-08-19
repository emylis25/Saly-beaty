package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.Resenna;
import org.esfe.BeatySaly.repositorios.IResennaRepository;
import org.esfe.BeatySaly.servicios.interfaces.IResennaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResennaService implements IResennaService {
    @Autowired
    private IResennaRepository resennaRepository;

    @Override
    public List<Resenna> obtenerTodos() {
        return resennaRepository.findAll();
    }

    @Override
    public Page<Resenna> buscarTodosPaginados(Pageable pageable) {
        return resennaRepository.findAll(pageable);
    }

    @Override
    public Resenna buscarPorId(Integer id) {
        Optional<Resenna> resenna = resennaRepository.findById(id);
        return resenna.orElse(null);
    }

    @Override
    public Resenna crear(Resenna resenna) {
        if (resenna.getId() != 0) {
            throw new IllegalArgumentException("La reseña nueva no debe tener ID");
        }
        return resennaRepository.save(resenna);
    }

    @Override
    public Resenna editar(Resenna resenna) {
        if (resenna.getId() == 0 || !resennaRepository.existsById(resenna.getId())) {
            throw new IllegalArgumentException("La reseña a editar no existe");
        }
        return resennaRepository.save(resenna);
    }

    @Override
    public void eliminarPorId(Integer id) {
        resennaRepository.deleteById(id);
    }

    @Override
    public Page<Resenna> buscarPorNombreTrabajadorYCliente(String nombreTrabajador, String nombreCliente, Pageable pageable) {
        return resennaRepository.findByTrabajadorNombreContainingIgnoreCaseAndClienteNombreContainingIgnoreCase(
                nombreTrabajador, nombreCliente, pageable
        );
    }
}
