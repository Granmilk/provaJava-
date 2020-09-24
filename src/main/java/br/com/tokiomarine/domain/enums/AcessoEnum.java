package br.com.tokiomarine.domain.enums;

public enum AcessoEnum {

    ADMIN(1,"Administrador do sistema"),
    FUNCIONARIO(2, "Funcionario");

    private Integer codigo;
    private String descricao;

    AcessoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
}
