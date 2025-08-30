package org.esfe.BeatySaly.servicios.implementaciones;
import org.esfe.BeatySaly.modelos.Cliente;
import org.esfe.BeatySaly.repositorios.IClienteRepository;
import org.esfe.BeatySaly.servicios.interfaces.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Page<Cliente> buscarTodosPaginados(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Cliente obtenerPorId(int id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElse(null);
    }

//    @Override
//    public Cliente crear(Cliente cliente) {
//        if (cliente.getId() != 0) {
//            throw new IllegalArgumentException("El cliente nuevo no debe tener ID");
//        }
//        return clienteRepository.save(cliente);
//    }
//
//    @Override
//    public Cliente actualizar(Cliente cliente) {
//        if (cliente.getId() == 0 || !clienteRepository.existsById(cliente.getId())) {
//            throw new IllegalArgumentException("El cliente a actualizar no existe");
//        }
//        return clienteRepository.save(cliente);
//    }

    @Override
    public Cliente guardar(Cliente cliente) {
        if (cliente.getId() != 0 && !clienteRepository.existsById(cliente.getId())) {
            throw new IllegalArgumentException("El cliente a actualizar no existe");
        }
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(int id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente obtenerPorCorreo(String correo) {
        Optional<Cliente> cliente = clienteRepository.findByCorreo(correo);
        return cliente.orElse(null); // O puedes lanzar una excepción si prefieres
    }

    @Override
    public Page<Cliente> buscarPorNombreYCorreo(String nombre, String correo, Pageable pageable) {
        return clienteRepository.findByNombreContainingIgnoreCaseAndCorreoContainingIgnoreCase(
                nombre, correo, pageable
        );
    }
}
