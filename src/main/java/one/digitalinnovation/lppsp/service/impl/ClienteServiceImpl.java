package one.digitalinnovation.lppsp.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.lppsp.feignclient.ViaCepFeign;
import one.digitalinnovation.lppsp.model.Cliente;
import one.digitalinnovation.lppsp.model.Endereco;
import one.digitalinnovation.lppsp.repository.ClienteRepository;
import one.digitalinnovation.lppsp.repository.EnderecoRepository;
import one.digitalinnovation.lppsp.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ViaCepFeign viaCepFeign;

	@Override
	public Iterable<Cliente> buscarTodos(){
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarCliente(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente newCliente) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			salvarCliente(newCliente);
		}
	}

	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}
	
	private void salvarCliente(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco newEndereco = viaCepFeign.consultarCep(cep);
			enderecoRepository.save(newEndereco);
			return newEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}
}
