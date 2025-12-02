package org.example.controller;

import org.example.dto.EmpresaDTO;
import org.example.model.Empresa;
import org.example.service.EmpresaService;

import java.util.List;
import java.util.stream.Collectors;

public class EmpresaController {
    private final EmpresaService service = new EmpresaService();

    public EmpresaDTO salvar(EmpresaDTO dto) {
        Empresa e = new Empresa();
        e.setNome(dto.getNome());
        e.setCnpj(dto.getCnpj());
        e.setTelefone(dto.getTelefone());
        Empresa salvo = service.salvar(e);
        return new EmpresaDTO(salvo.getId(), salvo.getNome(), salvo.getCnpj(), salvo.getTelefone());
    }

    public EmpresaDTO buscar(Long id) {
        Empresa e = service.buscar(id);
        return new EmpresaDTO(e.getId(), e.getNome(), e.getCnpj(), e.getTelefone());
    }

    public List<EmpresaDTO> listar() {
        return service.listar().stream()
                .map(e -> new EmpresaDTO(e.getId(), e.getNome(), e.getCnpj(), e.getTelefone()))
                .collect(Collectors.toList());
    }

    public EmpresaDTO atualizar(Long id, EmpresaDTO dto) {
        Empresa e = service.buscar(id);
        e.setNome(dto.getNome());
        e.setCnpj(dto.getCnpj());
        e.setTelefone(dto.getTelefone());
        Empresa atualizado = service.atualizar(e);
        return new EmpresaDTO(atualizado.getId(), atualizado.getNome(), atualizado.getCnpj(), atualizado.getTelefone());
    }

    public void deletar(Long id) {
        service.deletar(id);
    }
}