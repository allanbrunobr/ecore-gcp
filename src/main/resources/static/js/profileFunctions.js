$(document).ready(function() {
    $('#password, #confirmPassword').on('keyup', function () {
        if ($('#password').val() == $('#confirmPassword').val()) {
            $('#confirmPassword').removeClass('is-invalid').addClass('is-valid');
        } else {
            $('#confirmPassword').removeClass('is-valid').addClass('is-invalid');
        }
    });
});

// $(document).ready(function() {
//     $('#cep').inputmask('99999-999');
// });
//
// function getAddressByCep(cep) {
//     return new Promise((resolve, reject) => {
//         $.getJSON('https://brasilapi.com.br/api/cep/v1/' + cep.replace('-', ''))
//             .done(function (data) {
//                 if (data && data.result) {
//                     resolve(data.result);
//                 } else {
//                     reject(new Error('Endereço não encontrado para o CEP fornecido.'));
//                 }
//             })
//             .fail(function (jqxhr, textStatus, error) {
//                 reject(new Error('Erro ao consultar o CEP: ' + textStatus + ', ' + error));
//             });
//     });
// }
//
// $('#cep').blur(function () {
//     var cep = $(this).val().replace(/\D/g, '');
//     if (cep.length === 8) {
//         getAddressByCep(cep)
//             .then(function (address) {
//                 $('#street').val(address.street);
//                 $('#district').val(address.district);
//                 $('#city').val(address.city);
//             })
//             .catch(function (error) {
//                 console.error(error);
//                 // Handle the error here, for example, by displaying an error message to the user
//             });
//     }
// });
// Password and confirmPassword fields validation
var passwordField = document.getElementById('password');
var confirmPasswordField = document.getElementById('confirmPassword');
var passwordFeedback = document.getElementById('password-feedback');
var confirmPasswordFeedback = document.getElementById('confirmPassword-feedback');

passwordField.addEventListener('focusout', function() {
    var password = passwordField.value;
    var isValid = /^(?=.*\d)(?=.*[a-zA-Z])(?=.*\W)(?=.*\S).{8,20}$/.test(password);
    if (isValid) {
        passwordField.classList.add('is-valid');
        passwordFeedback.classList.remove('invalid-feedback');
        passwordFeedback.classList.add('valid-feedback');
        passwordFeedback.textContent = 'Senha válida.';
        confirmPasswordField.removeAttribute('readonly');
    } else {
        passwordField.classList.remove('is-valid');
        passwordFeedback.classList.remove('valid-feedback');
        passwordFeedback.classList.add('invalid-feedback');
        passwordFeedback.textContent = 'A senha deve ter entre 8 e 20 caracteres e conter pelo menos um número, uma letra, um caractere especial e não pode conter espaços em branco.';
        confirmPasswordField.setAttribute('readonly', 'readonly');
        confirmPasswordField.value = '';
        confirmPasswordField.classList.remove('is-valid');
        confirmPasswordFeedback.classList.remove('valid-feedback');
        confirmPasswordFeedback.classList.add('invalid-feedback');
    }
});

confirmPasswordField.addEventListener('input', function() {
    var confirmPassword = confirmPasswordField.value;
    var password = passwordField.value;
    if (confirmPassword === password) {
        confirmPasswordField.classList.add('is-valid');
        confirmPasswordFeedback.classList.remove('invalid-feedback');
        confirmPasswordFeedback.classList.add('valid-feedback');
        confirmPasswordFeedback.textContent = 'Senhas coincidem.';
    } else {
        confirmPasswordField.classList.remove('is-valid');
        confirmPasswordFeedback.classList.remove('valid-feedback');
        confirmPasswordFeedback.classList.add('invalid-feedback');
        confirmPasswordFeedback.textContent = 'As senhas não coincidem.';
    }
});

(function() {
    'use strict';
    window.addEventListener('load', function() {
        var forms = document.getElementsByClassName('needs-validation');
        Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    });
})();

$().ready(function() {
    $("form").submit(function (event) {
        var form = $(this)[0];
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
        }
        else {
            event.preventDefault();
            const formData = {
                primeiroNome: $("#primeiroNome").val(),
                segundoNome: $("#segundoNome").val(),
                email: $("#email").val(),
                password: $("#password").val(),
                cep: $("#cep").val()
            };

            $.ajax({
                type: 'POST',
                contentType: 'application/json;charset=utf-8',
                dataType: 'json',
                url: '/create',
                data: JSON.stringify(formData),
                success: function (response) {
                    console.log(response);
                    // Handle response here
                },
                error: function (error) {
                    console.error(error);
                    // Handle errors here
                }
            });
        }
    });
});