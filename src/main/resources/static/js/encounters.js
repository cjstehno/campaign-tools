/*
 * Copyright (C) 2017 Christopher J. Stehno <chris@stehno.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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