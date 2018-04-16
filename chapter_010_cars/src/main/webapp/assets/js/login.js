function changeWindow(toRegister) {
    $('#alert').hide();
    if(toRegister) {
        $('#loginblock').hide();
        $('#registerblock').show();
    } else {
        $('#loginblock').show();
        $('#registerblock').hide();
    }
}

function validateAndSend() {
    event.preventDefault();
    $('#reglogin').removeClass().addClass('form-control');
    $('#regpassword').removeClass().addClass('form-control');
    $('#regpasswordrepeat').removeClass().addClass('form-control');

    $('#regpasswordinvalid').hide();
    $('#regrepeatpasswordinvalid').hide();

    var password = $('#regpassword').val();
    var reppassword = $('#regpasswordrepeat').val();
    if (password === reppassword) {
        $.ajax({
            type: 'GET',
            url: '/registration',
            data: {
                'login' : $('#reglogin').val()
            },
            success: function (response) {
                if(response === false) {
                    $('#reglogin').removeClass().addClass('form-control is-invalid');
                    $('#logininvalid').html('Login already in use!').show();
                } else {
                    register();
                }
            }
        });
    } else {
        $('#regpassword').removeClass().addClass('form-control is-invalid');
        $('#regpasswordrepeat').removeClass().addClass('form-control is-invalid');
        $('#regrepeatpasswordinvalid').html("Passwords don't match!").show();
    }
}

function sendLogin() {
    event.preventDefault();
    $('#alert').hide();
    $.ajax({
        type: 'POST',
        url: '/validate',
        data: jQuery('#loginForm').serialize(),
        success: function (response) {
            if(response === 'false') {
                $('#alert').html('Wrong login or password!').show();
            } else {
                $('#submit').removeClass().addClass('btn btn-success').css({"width":"100%"}).val('Success!');
                setTimeout(function () {
                    document.location.href = '/';
                },2500);
            }
        }
    });
}

function register() {
    $.ajax({
        type: 'POST',
        url : '/registration',
        data : jQuery("#registerForm").serialize(),
        success : function (response) {
            if (response === -1) {
                $('#regalert').html('Something went wrong =(').show();
            } else {
                $('#regsubmit').removeClass().addClass('btn btn-success').css({"width": "100%"}).val('Success!');
                setTimeout(function () {
                    document.location.href = '/';
                }, 2500);
            }
        }
    });
}