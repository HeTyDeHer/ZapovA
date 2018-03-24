function loginCheck(loginId, logoutId, greetingId) {
    $.ajax({
        type: "GET",
        url: "/login",
        success: function (response) {
            if (response) {
                showGreetings(greetingId, loginId, logoutId, response);
            } else {
                document.getElementById(loginId).style.display = 'block';
            }
        },
        error: function (response) {
            document.getElementById(loginId).style.display = 'block';
        }
    });
}

function login(loginFormId, loginId, logoutid, greetingid) {
    $.ajax({
        type: "POST",
        url: "/login",
        data: jQuery("#"+loginFormId).serialize(),
        success: function (response) {
            if (response) {
                if (response.role.role == 'admin') {
                    window.location.replace('/admin');
                } else {
                    showGreetings(greetingid, loginId, logoutid, response.login);
                }
            } else {
                $('#loginerror').empty().append('Wrong login/password!');
            }
        },
        error: function () {
            $('#loginerror').empty().append('Error!');
        }
    });
}

function logout(loginId, logoutId, greetingId) {
    $.ajax({
        type: "POST",
        url: "/logout",
        success: function (response) {
            document.getElementById(loginId).style.display = 'block';
            document.getElementById(logoutId).style.display = 'none';
        },
        error: function () {
            $('#' + greetingId).empty().append('<p style="color: red;">Error!<p>');
        }
    });
}


function showGreetings(greetingid, loginId, logoutId, login) {
    document.getElementById(loginId).style.display = 'none';
    document.getElementById(logoutId).style.display = 'block';
    document.getElementById(greetingid).innerHTML = '';
    greetingid = '#' + greetingid;
    $(greetingid).empty().append('Hello, '+ login);
}