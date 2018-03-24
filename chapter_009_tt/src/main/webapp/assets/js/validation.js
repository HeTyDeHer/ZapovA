function validatePassorwEq(password, rpassword) {
    var pass1 = document.getElementById(password).value;
    var pass2 = document.getElementById(rpassword).value;
    if(pass1 != pass2) {
        document.getElementById(rpassword).setCustomValidity("Passwords Don't Match");
    } else {
        document.getElementById(rpassword).setCustomValidity('');
    }
}

function setInvalid(id) {
    document.getElementById(id).setCustomValidity("Invalid");
}
function setValid(id) {
    document.getElementById(id).setCustomValidity("");

}

function validateLogin(login) {
    $.ajax({
        type: 'GET',
        url: '/adduser',
        data: {
            'login' : login
        },
        success: function (response) {
            if (response == 'valid') {
                setValid('login');
            } else {
                setInvalid('login');
            }
        },
        error: function (resp) {
            $('#h2').empty().addClass('answer').append('Error!');
        }
    });
}

function validateAndSend() {
    var sendform = $('#sendform');
    if(!sendform[0].checkValidity()) {
        $('<input type="submit">').hide().appendTo(sendform).click().remove();
    } else {
        $.ajax({
            type: 'POST',
            url: '/adduser',
            data: jQuery("#sendform").serialize(),
            success: function (response) {
                if (response != -1) {
                    $('#h2').empty().addClass('answer').append('Successful, user id = ', response);
                    window.setTimeout(function(){
                        window.location.replace('/admin');
                    }, 2500);
                } else {
                    $('#h2').empty().addClass('answer').append('Error!');
                }
            },
            error: function (resp) {
                $('#h2').empty().addClass('answer').append('Error!');
            }
        });
    }
}