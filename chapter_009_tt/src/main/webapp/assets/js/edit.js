function getUserId() {
    var url = window.location.href;
    var regex = new RegExp("[?&]user(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    results = results[2];
    if(results) {
        return results;
    }
}

function loadMusicEdit(placeId) {
    placeId = '#' + placeId;
    $(placeId).empty();
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            "get" : "music"
        },
        success: function (response) {
            $.each(response, function (i, music) {
                $(placeId).append('<input type="checkbox" name="' + music.genre + '" value="' + music.genre + '">' + music.genre + '</input>');
            });
        }
    });
}


function getUser(id){
    $.ajax({
        type: 'GET',
        url: '/edituser',
        data: {
            "id" : id
        },
        success: function (user) {
            $('#idCell').append(user.id);
            $('#id').val(user.id);
            $('#addressId').val(user.address.id);
            $('#login').val(user.login);
            $('#password').val(user.password);
            $('#rpassword').val(user.password);
            $('#name').val(user.name);
            $("#country").find('option').removeAttr("selected");
            $("#country").find('option').each(function(i, country) {
                if ( $(country).val() == user.address.country ) {
                    $('#country').prop('selectedIndex',i);
                    loadCityEdit('city', user.address.country, user.address.city);
                }
            });
            $('#street').val(user.address.street);
            $('#home').val(user.address.home);
            $('#apart').val(user.address.apart);
            $("#role").find('option').each(function(i, role) {
                if ( $(role).val() == user.role.role ) {
                    $('#role').prop('selectedIndex',i);
                }
            });
            $("#music").find('input').each(function(i, input) {
                $(user.music).each(function (z, music) {
                    if ( $(input).val() == music.genre ) {
                        $(input).prop('checked', true );
                    }
                });
            });


        }

    });
}

function loadCityEdit(id, country, userCity) {
    document.getElementById(id).innerHTML ='';
    id = '#' + id;
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            "get" : "city",
            "country" : country
        },
        success: function (response) {
            $("<option selected disabled >").text('Choose city').appendTo($(id));
            $.each(response, function (i, city) {
                if (city.name == userCity) {
                    $("<option selected>").val(city.name).text(city.name).appendTo($(id));
                } else {
                    $("<option>").val(city.name).text(city.name).appendTo($(id));
                }
            });
        }
    });
}