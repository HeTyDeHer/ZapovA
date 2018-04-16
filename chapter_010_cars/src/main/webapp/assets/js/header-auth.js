function checklogin() {
    $.ajax({
        type:'GET',
        url: '/validate',
        success: function (response) {
            if(response != 'false') {
                $('#unauthorized').hide();
                $('#greeting').html('Hello, ' + response + '!');
                $('#authorized').show();
            }
        },
        error: function () {
            alert('err')
        }
    });

}

function authorize() {
    $.ajax({
        type:'POST',
        url: '/validate',
        data: jQuery("#loginform").serialize(),
        success: function (response) {
            if(response == 'false') {
                var login = $("#login").val();
                document.location.href = '/login';
            } else {
                $('#unauthorized').hide();
                $('#greeting').html('Hello, ' + response + '!');
                $('#authorized').show();
            }
        },
        error: function () {
            alert('err')
        }
    });
}
function logout() {
    $.ajax({
        type:'POST',
        url:'/invalidate',
        success: function () {
            $('#authorized').hide();
            $('#unauthorized').show();
        }
    });
}