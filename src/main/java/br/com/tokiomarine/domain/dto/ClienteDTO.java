package br.com.tokiomarine.domain.dto;

import br.com.tokiomarine.domain.Cliente;
import br.com.tokiomarine.domain.Endereco;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ClienteDTO {
    private Cliente cliente;
    private List<Endereco> listaEndereco;
}
