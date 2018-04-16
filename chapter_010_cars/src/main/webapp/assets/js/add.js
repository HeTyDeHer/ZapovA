function loadMake() {
    $.ajax({
        type: 'GET',
        url : '/showdata',
        data: {
            'action' : 'make'
        },
        success: function (response) {
            $('#make').append('<option selected disabled value="">Choose make</option>');
            $.each(response, function (i, make) {
                $('#make').append('<option value="' + make.id + '">' + make.name + '</option>');
            })
        },
        error: function () {
            alert('err')
        }
    });
}

function loadModel(make_id) {
    $.ajax({
        type: 'GET',
        url : '/showdata',
        data: {
            'action' : 'model',
            'make_id' : make_id
        },
        success: function (response) {
            models = response;
            $('#model').empty().append('<option selected disabled value="">Choose model</option>');
            $.each(response, function (i, model) {
                $('#model').append('<option value="' + model.id + '">' + model.name + '</option>');
            })
        },
        error: function () {
            alert('err')
        }
    });
}

function loadModelData(model_id) {
    $.each(models, function (i, model) {
        if (model.id == model_id) {
            currentModel = model;
            return true;
        }
    });
    $('#body').empty().append('<option selected disabled value="">Choose body</option>');
    $.each(currentModel.body, function (i, body) {
        $('#body').append('<option value="' + body.id + '">' + body.type + '</option>');
    });
    $('#engine').empty().append('<option selected disabled value="">Choose engine</option>');
    $.each(currentModel.engine, function (i, engine) {
        $('#engine').append('<option value="' + engine.id + '">' + engine.type + ', ' + engine.displacement + '</option>');
    });
    $('#gearbox').empty().append('<option selected disabled value="">Choose gearbox</option>');
    $.each(currentModel.gearbox, function (i, gearbox) {
        $('#gearbox').append('<option value="' + gearbox.id + '">' + gearbox.type + '</option>');
    });
}

function postOffer() {
    if(! $('#mainForm')[0].checkValidity()) {
        $('#mainForm').find(':submit').click();
    } else {
        event.preventDefault();
        $.ajax({
            type: 'post',
            url: '/postoffer',
            data: new FormData($('#mainForm')[0]),
            contentType: false,
            enctype: 'multipart/form-data',
            processData: false,
            success: function (response) {
                if(response === true) {
                    document.location.href = '/';
                }
            },
            error: function () {
                alert('err')
            }
        });
    }
}