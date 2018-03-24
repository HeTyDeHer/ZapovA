function loadUsers () {
    $.ajax({
        type:'GET',
        url: '/show',
        data: {
            "get" : "user"
        },
        success: function (response) {
            document.getElementById('mainTableBody').innerHTML ='';
            document.getElementById('tableFoot').style.display = 'none';
            fillTable('#maintable', response);
        }
    });
}

function loadMusic(idForm) {
    document.getElementById(idForm).innerHTML ='';
    idForm = '#' + idForm;
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            "get" : "music"
        },
        success: function (response) {
            $.each(response, function (i, music) {
                $(idForm).append('<input type="checkbox" name="' + music.genre + '" value="' + music.genre + '">' + music.genre + '</input>');
            });
            $(idForm).append('<input type="hidden" name="filter" value="music">');

        }
    });
}
function loadRole(selectId) {
    document.getElementById(selectId).innerHTML ='';
    selectId = '#' + selectId;
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            "get" : "role"
        },
        success: function (response) {
            $.each(response, function (i, role) {
                $("<option>").text(role.role).appendTo($(selectId));
            });
        }
    });
}
function loadCountry(id) {
    document.getElementById(id).innerHTML ='';
    id = '#' + id;
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            "get" : "country"
        },
        success: function (response) {
            $("<option selected disabled >").text('Choose country').appendTo($(id));
            $.each(response, function (i, country) {
                $("<option>").text(country).val(country).appendTo($(id));
            });
        }
    });
}

function loadCity(id, country) {
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
                $("<option>").val(city.id).text(city.name).appendTo($(id));

            });
        }
    });
}

function sendFilter(formId) {
    document.getElementById('tableFoot').style.display = 'table-footer-group';
    $.ajax({
        type:   'POST',
        url: '/show',
        data: jQuery("#"+formId).serialize(),
        success: function(response) {
            document.getElementById('error').innerHTML = '';
            document.getElementById('mainTableBody').innerHTML = '';
            fillTable('#maintable', response);
        },
        error: function(response) {
            document.getElementById('error').innerHTML = "Error. No data.";
        }
    });
}

function fillTable(tableId, data) {
    $.each(data, function (i, user) {
        $(tableId + ' > tbody').append('<tr></tr>');
        $(tableId + ' > tbody > tr:last').append('<td>' + user.id + '</td>');
        $(tableId + ' > tbody > tr:last').append('<td>' + user.login + '</td>');
        $(tableId + ' > tbody > tr:last').append('<td>' + user.name + '</td>');
        $(tableId + ' > tbody > tr:last').append('<td>' + user.address.country + ', '
            + user.address.city + ', '
            + user.address.street + ', '
            + user.address.home + ', '
            + user.address.apart + ', '
            + '</td>');
        $(tableId + ' > tbody > tr:last').append('<td>' + user.role.role + '</td>');
        $(tableId + ' > tbody > tr:last').append('<td></td>');
        $.each(user.music, function (z, mus) {
            $(tableId + ' > tbody > tr:last > td:last').append(mus.genre + '. ');
        });
    })
}