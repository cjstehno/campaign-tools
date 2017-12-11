$('a[href="#add-encounter"]').on('click', function (evt) {
    evt.preventDefault();

    $('#add-encounter-dialog').modal();
});

$('#add-encounter-dialog .btn-primary').on('click', function(evt){
    var name = $('#add-encounter-dialog').find('input[name=name]').val();

    $.ajax({
        url: '/encounter',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({name: name}),
        success: function (result) {
            location = '/encounter';
        }
    });
});

$('a[href="#remove-encounter"]').on('click', function (evt) {
    var encounterId = $(evt.target).closest('tr').attr('data-id');

    var dialog = $('#remove-encounter-dialog');
    dialog.attr('data-id', encounterId);
    dialog.modal();
});

$('#remove-encounter-dialog button.btn-danger').on('click', function (evt) {
    var encounterId = $('#remove-encounter-dialog').attr('data-id');

    $.ajax({
        url: '/encounter/' + encounterId,
        type: 'DELETE',
        success: function (result) {
            location = '/encounter';
        }
    });
});