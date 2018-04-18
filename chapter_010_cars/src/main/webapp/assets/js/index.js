function getHeader() {
    var link = document.querySelector('link[rel=import]');
    var content = link.import.querySelector('#nav');
    document.body.insertBefore(content.cloneNode(true), document.body.childNodes[0]);
}

function applyFilters(action, parameter) {
    if (action === 'for_user') {

        $('#message').show().empty().append('<button class="btn btn-primary btn-sm" onclick="applyFilters()">Clear filter</button>');
        $('#filters').hide();
    } else {
        $('#filters').show();
        $('#message').hide();
        if(!action) {
            action = "";
        }
    }
    if (action !== 'by_make') {
        $('#select_by_make').hide();
    }
    getOffers(action, parameter);
}


function getOffers(action, parameter) {
    if (document.location.pathname != "/") {
        window.location.replace('/');
    }
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            'action': action,
            'parameter' : parameter
        },
        success: function (response) {
            $('#offers').empty();
            var count = 0;
            $.each(response, function (i, offer) {
                if (!offer.sold  || action == 'for_user') {
                    if (! count % 4) {
                        $('#offers').append('<div class="row" style="width: 100%; padding-top: 20px"></div>');
                    }
                    count++;
                    $('#offers').children().last().append('<div class="col-sm-3"></div>');
                    var lastrow = $('#offers').children().last().children().last();
                    lastrow.append('<div class="card w-22"></div>');
                    var lastcard = lastrow.children().last();
                    var id = 'carousel' + i;
                    lastcard.append('<div id="' + id + '" class="carousel slide" data-ride="carousel" data-interval="false"><div class="carousel-inner"></div></div>');
                    var carousel = $('#' + id).children().last();
                    var images = offer.images;
                    if( !$.isArray(images) ||  !images.length ) {
                        $(carousel).append('<div class="carousel-item active"><img class="d-block w-100" src="/assets/images/no-image-available2.jpg" alt="First slide">'
                            + '</div>');
                    } else {
                        var first = true;
                        $.each(images, function (i, image) {
                            if(first) {
                                $(carousel).append('<div class="carousel-item active"><img class="d-block w-100" src="'
                                    + image
                                    + '" alt="First slide">'
                                    + '</div>');
                                first = false;
                            } else {
                                $(carousel).append('<div class="carousel-item"><img class="d-block w-100" src="'
                                    + image
                                    + '" alt="First slide">'
                                    + '</div>');
                            }
                        });
                        $('#' + id).append('<a class="carousel-control-prev" href="#'+ id +'" role="button" data-slide="prev">' +
                            '        <span class="carousel-control-prev-icon" aria-hidden="true"></span>' +
                            '        <span class="sr-only">Previous</span>' +
                            '    </a>' +
                            '    <a class="carousel-control-next" href="#'+ id +'" role="button" data-slide="next">' +
                            '        <span class="carousel-control-next-icon" aria-hidden="true"></span>' +
                            '        <span class="sr-only">Next</span>' +
                            '    </a>');
                    }
                    lastcard.append('<div class="card-body"></div>');
                    var lastdiv = lastcard.children().last();
                    lastdiv.append('<h5 class="card-title">' + offer.make + ' ' + offer.model + '</h5>')
                        .append('<p class="card-text"> Body: ' + offer.body + '</p>')
                        .append('<p class="card-text"> Engine: ' + offer.engine.type + ', ' + offer.engine.displacement + '</p>')
                        .append('<p class="card-text"> Gearbox: ' + offer.gearbox + '</p>')
                        .append('<p class="card-text">' + offer.description + '</p>');
                    if (action == 'for_user') {
                        lastdiv.append('<p class="card-text"> Sold: ' + offer.sold + '</p>');
                    }
                    lastdiv.append('<button class="btn btn-primary" onclick="getPhone(this)">Get phone</a>');
                    if (action == 'for_user' && !offer.sold) {
                        lastdiv.append('<br/><button class="btn btn-link" onclick="setSold(' + offer.id + ', true)">Mark as sold</button>');
                    }
                }
            });
        },
        error: function (response) {
            console.log('error')
        }
    });
}

function getPhone(button, phone) {
    if ($(button).text() == 'Get phone') {
        $(button).text('8-800-555-35-35');
    } else {
        $(button).text('Get phone');
    }
}

function setSold(id, sold) {
    $.ajax({
        type: 'POST',
        url : '/update',
        data : {
            'offer_id' : id,
            'sold' : sold
        },
        success : function (response) {
            if (response === true) {
                getOffers(true);
            } else {
                console.log('err');
            }
        }
    });
}

function getMakes() {
    $.ajax({
        type: 'GET',
        url : '/showdata',
        data: {
            'action' : 'make'
        },
        success: function (response) {
            $('#make_select').append('<option selected disabled value="">Choose make</option>');
            $.each(response, function (i, make) {
                $('#make_select').append('<option value="' + make.id + '">' + make.name + '</option>');
            })
        },
        error: function () {
            alert('err')
        }
    });

}

