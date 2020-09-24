package br.com.tokiomarine.domain.enums;

public enum ErroEnums {

    EMAIL_JA_CADASTRADO(1, "Erro no cadastro", "Este endereco de email ja foi cadastrado, busque na secao de clientes", "clientes","Voltar"),
    ENDERECO_JA_CADASTRADO(1, "Erro no cadastro", "Este endereco ja foi cadastrado para este cliente", "ok","Ok" ),
    TIMEOUT(3, "TIMEOUT", "Sua sessao expirou, relogue para continuar", "", "Relogar");

    private Integer codigo;
    private String titulo;
    private String descricao;
    private String link;
    private String rotuloBotao;

    ErroEnums(Integer codigo, String titulo, String descricao, String link, String rotuloBotao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.rotuloBotao = rotuloBotao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLink() {
        return link;
    }

    public String getRotuloBotao() {
        return rotuloBotao;
    }
}
