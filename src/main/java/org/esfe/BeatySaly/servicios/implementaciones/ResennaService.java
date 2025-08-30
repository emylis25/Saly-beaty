package org.esfe.BeatySaly.servicios.implementaciones;

import org.esfe.BeatySaly.modelos.Cliente;
import org.esfe.BeatySaly.modelos.Resenna;
import org.esfe.BeatySaly.repositorios.IClienteRepository;
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

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public List<Resenna> obtenerTodos() {
        return resennaRepository.findAll();
    }

    @Override
    public Page<Resenna> buscarTodosPaginados(Pageable pageable) {
        return resennaRepository.findAll(pageable);
    }

    @Override
    public Resenna buscarPorId(int id) {
        Optional<Resenna> resenna = resennaRepository.findById(id);
        return resenna.orElse(null);
    }

    @Override
    public Resenna crear(Resenna resenna) {
        if (resenna.getId() != 0) {
            throw new IllegalArgumentException("La reseña nueva no debe tener ID");
        }

        // ⚠️ asegurar que el cliente esté persistido
        if (resenna.getCliente() != null && resenna.getCliente().getId() != 0) {
            Cliente clientePersistido = clienteRepository.findById(resenna.getCliente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            resenna.setCliente(clientePersistido);
        } else {
            throw new IllegalArgumentException("La reseña debe tener un cliente válido");
        }

        return resennaRepository.save(resenna);
    }

    @Override
    public Resenna editar(Resenna resenna) {
        if (resenna.getId() == 0 || !resennaRepository.existsById(resenna.getId())) {
            throw new IllegalArgumentException("La reseña a editar no existe");
        }

        // asegurar cliente persistido
        if (resenna.getCliente() != null && resenna.getCliente().getId() != 0) {
            Cliente clientePersistido = clienteRepository.findById(resenna.getCliente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            resenna.setCliente(clientePersistido);
        }

        return resennaRepository.save(resenna);
    }

    @Override
    public void eliminarPorId(int id) {
        resennaRepository.deleteById(id);
    }

    @Override
    public Page<Resenna> buscarPorNombreTrabajadorYCliente(String nombreTrabajador, String nombreCliente, Pageable pageable) {
        return resennaRepository.findByTrabajadorNombreContainingIgnoreCaseAndClienteNombreContainingIgnoreCase(
                nombreTrabajador, nombreCliente, pageable
        );
    }

    @Override
    public Resenna guardar(Resenna resenna) {
        return crear(resenna); // o editar(resenna), según tu lógica
    }

    @Override
    public List<Resenna> obtenerPorCliente(Cliente cliente) {
        if (cliente == null || cliente.getId() == 0) {
            throw new IllegalArgumentException("Cliente inválido");
        }
        return resennaRepository.findByCliente(cliente);
    }

}
