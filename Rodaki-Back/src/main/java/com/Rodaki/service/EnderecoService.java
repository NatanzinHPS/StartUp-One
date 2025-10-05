package com.Rodaki.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.Rodaki.entity.Endereco;
import com.Rodaki.entity.Passageiro;
import com.Rodaki.repository.EnderecoRepository;
import com.Rodaki.repository.PassageiroRepository;

import java.util.List;
import java.util.Map;

@Service
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;
    private final PassageiroRepository passageiroRepository;
    private final GoogleMapsService googleMapsService;

    public EnderecoService(EnderecoRepository enderecoRepository, 
                          PassageiroRepository passageiroRepository,
                          GoogleMapsService googleMapsService) {
        this.enderecoRepository = enderecoRepository;
        this.passageiroRepository = passageiroRepository;
        this.googleMapsService = googleMapsService;
    }

    @Transactional
    public Endereco salvar(Endereco endereco, Long passageiroId) {
        Passageiro passageiro = passageiroRepository.findById(passageiroId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));

        endereco.setPassageiro(passageiro);

        // Se marcado como principal, desmarca os outros
        if (Boolean.TRUE.equals(endereco.getIsPrincipal())) {
            enderecoRepository.desmarcarTodosPrincipais(passageiroId);
        }

        // Se não tem endereço principal, marca o primeiro como principal
        if (!enderecoRepository.existsByPassageiroIdAndIsPrincipalTrue(passageiroId)) {
            endereco.setIsPrincipal(true);
        }

        // Se latitude/longitude não foram fornecidos, busca via Google Maps
        if (endereco.getLatitude() == null || endereco.getLongitude() == null) {
            String enderecoCompleto = String.format("%s, %s, %s, %s - %s, %s",
                    endereco.getRua(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getCidade(),
                    endereco.getEstado(),
                    endereco.getCep());

            Map<String, Object> geocodeResult = googleMapsService.geocodeAddress(enderecoCompleto);
            
            if (Boolean.TRUE.equals(geocodeResult.get("success"))) {
                endereco.setLatitude((Double) geocodeResult.get("latitude"));
                endereco.setLongitude((Double) geocodeResult.get("longitude"));
            } else {
                throw new RuntimeException("Não foi possível geocodificar o endereço");
            }
        }

        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarPorPassageiro(Long passageiroId) {
        return enderecoRepository.findByPassageiroIdOrderByIsPrincipalDescCreatedAtDesc(passageiroId);
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    @Transactional
    public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
        Endereco endereco = buscarPorId(id);

        endereco.setCep(enderecoAtualizado.getCep());
        endereco.setRua(enderecoAtualizado.getRua());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setComplemento(enderecoAtualizado.getComplemento());
        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setApelido(enderecoAtualizado.getApelido());

        if (Boolean.TRUE.equals(enderecoAtualizado.getIsPrincipal()) && 
            !Boolean.TRUE.equals(endereco.getIsPrincipal())) {
            enderecoRepository.desmarcarTodosPrincipais(endereco.getPassageiro().getId());
            endereco.setIsPrincipal(true);
        }

        // Atualiza coordenadas
        String enderecoCompleto = String.format("%s, %s, %s, %s - %s, %s",
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());

        Map<String, Object> geocodeResult = googleMapsService.geocodeAddress(enderecoCompleto);
        
        if (Boolean.TRUE.equals(geocodeResult.get("success"))) {
            endereco.setLatitude((Double) geocodeResult.get("latitude"));
            endereco.setLongitude((Double) geocodeResult.get("longitude"));
        }

        return enderecoRepository.save(endereco);
    }

    @Transactional
    public void deletar(Long id) {
        Endereco endereco = buscarPorId(id);
        
        if (Boolean.TRUE.equals(endereco.getIsPrincipal())) {
            enderecoRepository.delete(endereco);
            List<Endereco> outrosEnderecos = enderecoRepository
                    .findByPassageiroId(endereco.getPassageiro().getId());
            if (!outrosEnderecos.isEmpty()) {
                Endereco novoEnderecoPrincipal = outrosEnderecos.get(0);
                novoEnderecoPrincipal.setIsPrincipal(true);
                enderecoRepository.save(novoEnderecoPrincipal);
            }
        } else {
            enderecoRepository.delete(endereco);
        }
    }

    @Transactional
    public Endereco marcarComoPrincipal(Long id) {
        Endereco endereco = buscarPorId(id);
        enderecoRepository.desmarcarTodosPrincipais(endereco.getPassageiro().getId());
        endereco.setIsPrincipal(true);
        return enderecoRepository.save(endereco);
    }

    public Map<String, Object> buscarCoordenadas(String endereco) {
        return googleMapsService.geocodeAddress(endereco);
    }

    public Map<String, Object> buscarPorCep(String cep) {
        return googleMapsService.geocodeByCep(cep);
    }

    public Map<String, Object> autocomplete(String input) {
        return googleMapsService.autocompleteAddress(input);
    }
}
