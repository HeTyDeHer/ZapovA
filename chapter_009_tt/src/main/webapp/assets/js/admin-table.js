/**
 * Fill main table. Datatable plugin.
 */
function getAllUsersAdmin() {
    $.ajax({
        type: 'GET',
        url: '/show',
        data: {
            "get": "user"
        },
        success: function (response) {
            if ($.fn.DataTable.isDataTable( '#maintable' )) {
                $('#maintable').dataTable().fnClearTable();
                $('#maintable').dataTable().fnAddData(response);
                document.getElementById('clear').style.display = 'none';
                document.getElementById('error').style.display = 'none';
            } else {
                filldata(response);
            }
        }
    });
}

function getUsersByFilterAdmin(formId) {
    document.getElementById('clear').style.display = 'block';
    document.getElementById('error').style.display = 'none';
    $.ajax({
        type:   'POST',
        url: '/show',
        data: jQuery("#"+formId).serialize(),
        success: function(response) {
            $('#maintable').dataTable().fnClearTable();
            if(response) {
                $('#maintable').dataTable().fnAddData(response);
            }
        },
        error: function(response) {
            document.getElementById('error').style.display = 'block';
            document.getElementById('error').innerHTML = "Error. Data wasn't sent.";
        }
    });
}

function filldata(response) {
    $('#maintable').DataTable( {
        data: response,
        columns: [
            { data: 'id' },
            { data: 'login' },
            { data: 'name' },
            { data: 'address.country'},
            { data: 'address.city'},
            { data: 'address.street'},
            { data: 'address.home'},
            { data: 'address.apart'},
            { data: 'role.role' },
            { data: 'music[, ].genre' },
            { data: 'null'}
        ],
        columnDefs: [
            {
                "defaultContent": "<button onclick='editRedir(this)'>Edit</button><button onclick='del(this)'>Delete</button>",
                "targets": -1
            }
        ],
        paging:   false,
        info:     false,
        retrieve : true
    } );
}

function editRedir(button) {
    var tr = $(button).closest("tr").find("td:first").html();
    var url = "/edit?user=" + tr;
    $(location).attr('href',url);
}

function del(button) {
    var id = $(button).closest("tr").find("td:first").html();
    $.ajax({
        type: 'POST',
        url: '/delete',
        data: {
            'id' : id
        },
        success: function (response) {
            if (response == 'true') {
                getAllUsersAdmin();
            } else {
                document.getElementById('error').innerHTML = "Error!";
            }
        },
        error: function (resp) {
            document.getElementById('error').innerHTML = "Error!";
        }
    });
}