function getHeader() {
    var link = document.querySelector('link[rel=import]');
    var content = link.import.querySelector('#nav');
    document.body.insertBefore(content.cloneNode(true), document.body.childNodes[0]);
}

function getOffers(foruser) {
    if (document.location.pathname != "/") {
        window.location.replace('/');
    }
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            'for_user': foruser
        },
        success: function (response) {
            $('#offers').empty();
            $('#message').empty();
            var count = 0;
            $.each(response, function (i, offer) {
                if (!offer.sold  || foruser) {
                    if (count % 4 == 0) {
                        $('#offers').append('<div class="row" style="width: 100%; padding-top: 20px"></div>');
                    }
                    count++;
                    $('#offers').children().last().append('<div class="col-sm-3"></div>');
                    var lastrow = $('#offers').children().last().children().last();
                    lastrow.append('<div class="card w-22"></div>');
                    var lastcard = lastrow.children().last();
                    var id = 'carousel' + i;
                    lastcard.append('<div id="' + id + '" class="carousel slide" data-ride="carousel" data-interval="false"><div class="carousel-inner"></div></div>');
                    var first = true;
                    var carousel = $('#' + id).children().last();
                    $.each(offer.images, function (i, image) {
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
                    lastcard.append('<div class="card-body"></div>');
                    var lastdiv = lastcard.children().last();
                    lastdiv.append('<h5 class="card-title">' + offer.make + ' ' + offer.model + '</h5>')
                        .append('<p class="card-text"> Body: ' + offer.body + '</p>')
                        .append('<p class="card-text"> Engine: ' + offer.engine.type + ', ' + offer.engine.displacement + '</p>')
                        .append('<p class="card-text"> Gearbox: ' + offer.gearbox + '</p>')
                        .append('<p class="card-text">' + offer.description + '</p>');
                    if (foruser) {
                        lastdiv.append('<p class="card-text"> Sold: ' + offer.sold + '</p>');
                    }
                    lastdiv.append('<button class="btn btn-primary" onclick="getPhone(this)">Get phone</a>');
                    if (foruser && !offer.sold) {
                        lastdiv.append('<br/><button class="btn btn-link" onclick="setSold(' + offer.id + ', true)">Mark as sold</button>');
                    }
                }
            });
            if(foruser) {
                $('#message').append('<button class="btn btn-primary" onclick="getOffers(false)">Clear filter</button>');
            }
        },
        error: function (response) {

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