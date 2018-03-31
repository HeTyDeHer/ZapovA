function filltable() {
    $.ajax({
        type:'GET',
        url: '/show',
        success: function (response) {
            var tbody = '#tbody';
            $(tbody).empty();
            $.each(response, function(i, item) {
                if (item.done) {
                    $(tbody).append('<tr style="display: none"></tr>');
                } else {
                    $(tbody).append('<tr></tr>');
                }

                $(tbody + ' > tr:last').append('<td>' + item.id + '</td>');
                $(tbody + ' > tr:last').append('<td>' + item.description + '</td>');
                $(tbody + ' > tr:last').append('<td>' + item.created + '</td>');
                $(tbody + ' > tr:last').append('<td>' + item.done + '</td>');
            })
        },
        error: function () {
            alert('err')
        }
    });
}


function addnew() {
    if ($.trim($('#description').val()) == '') {
        $('#description').css('background', 'lightcoral');
    } else {
        $('#description').css('background', 'white');
        $('#message').empty().show();
        $.ajax({
            type: 'POST',
            url: '/add',
            data: jQuery("#addNew").serialize(),
            success: function (response) {
                filltable();
                $('#description').val('');
                if (response != -1) {
                    $('#message').append('Successful! Item id = ' + response);
                } else {
                    $('#message').append('Error!');
                }
            },
            error: function () {
                $('#message').empty().append('Error!');
            }
        });
        setTimeout(function () {
            $('#message').hide();
        }, 5000);
    }
}

function showAll() {
    var isChecked = $('#all').is(':checked');
    if (!isChecked) {
        $('#tbody').find('tr').each(function () {
            var done = $(this).find(':last-child').html();
            if (done == 'true') {
                $(this).hide()
            } else {
                $(this).show()
            }
        });
    } else {
        $('#tbody').find('tr').each(function () {
            $(this).show()
        });
    }
}