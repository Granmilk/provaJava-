$(document).on('click', 'a[endpoint]', function (event) {
    var endpoint = $(this).attr('endpoint');
    $("#content-sec").load("/"+endpoint);
});

$(document).on('click', '.nav-item', function (event) {
    $('.active').removeClass("active");
    $(this).addClass("active");
});

$(document).on('click', 'a[menu]', function (event) {
    $('.active').removeClass("active");
    var classe = '.menu-'+$(this).attr('menu');
    $(classe).addClass("active");
});

$(document).on('submit', '.form-ajax', function (event) {
    var form = event.target;
    ajaxSubmitForm(form);
    return false;
});

$(document).on('click', 'button[auto-login]', function (event) {
    var user = $(this).attr('auto-login');
    if(user === 'adm'){
        $("#email").val('admin@admin.com');
        $('#senha').val('teste');
    }else {
        $("#email").val('funcionario@email.com');
        $('#senha').val('teste');
    }
});

function ajaxSubmitForm(form) {
    var container = $(form).attr('data-target');
    var containerId = '#' + container;
    var url = $(form).attr('action');

    $.ajax({
        type: "POST",
        url: url,
        data: $(form).serialize(), // serializes the form's elements.
        success: function (data) {
            $(containerId).html(data);
        }
    })

        .fail(function () {

        });

}

$(document).on('change', '.cep', function (event) {
    var endId = $(this).attr('endId');
    function limpa_formulário_cep() {
        // Limpa valores do formulário de cep.
        $("#rua"+endId).val("");
        $("#numero"+endId).val("");
        $("#bairro"+endId).val("");
        $("#cidade"+endId).val("");
        $("#estado"+endId).val("");
    }

    //Quando o campo cep perde o foco.
    $("#cep"+endId).blur(function () {

        //Nova variável "cep" somente com dígitos.
        var cep = $(this).val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
        if (cep != "") {

            //Expressão regular para validar o CEP.
            var validacep = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if (validacep.test(cep)) {

                //Preenche os campos com "Buscando..." enquanto consulta webservice.
                $("#rua"+endId).val("Buscando...");
                $("#bairro"+endId).val("Buscando...");
                $("#cidade"+endId).val("Buscando...");
                $("#estado"+endId).val("Buscando...");

                //Consulta o webservice viacep.com.br/
                $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {

                    if (!("erro" in dados)) {
                        //Atualiza os campos com os valores da consulta.
                        $("#rua"+endId).val(dados.logradouro);
                        $("#bairro"+endId).val(dados.bairro);
                        $("#cidade"+endId).val(dados.localidade);
                        $("#estado"+endId).val(dados.uf);
                    }
                    else {
                        //CEP pesquisado não foi encontrado.
                        limpa_formulário_cep();
                        alert("CEP não encontrado.");
                    }
                });
            }
            else {
                //cep é inválido.
                limpa_formulário_cep();
                alert("Formato de CEP inválido.");
            }
        }
        else {
            //cep sem valor, limpa formulário.
            limpa_formulário_cep();
        }
    });
});


