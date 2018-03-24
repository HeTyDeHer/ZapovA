function showFilter(id) {
    var address = document.getElementById(id);
    var wasOpen = address.style.display == 'block';
    document.getElementById('musicFilter').style.display = 'none';
    document.getElementById('roleFilter').style.display = 'none';
    document.getElementById('addressFilter').style.display = 'none';
    if(!wasOpen) {
        address.style.display = 'block';
    }
}

