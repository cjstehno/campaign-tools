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
$('#remove-monster button.btn-danger').on('click', function (evt) {
    var encounterId = $('h1[data-id]').attr('data-id');
    var participantId = $('#remove-monster').attr('data-id');

    $.ajax({
        url: '/encounter/' + encounterId + '/' + participantId,
        type: 'DELETE',
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a.remove-button').on('click', function (evt) {
    var participantId = $(evt.target).closest('tr').attr('data-id');

    var dialog = $('#remove-monster');
    dialog.attr('data-id', participantId);
    dialog.modal();
});

$('#add-monster button.btn-primary').on('click', function (evt) {
    var encounterId = $('h1[data-id]').attr('data-id');

    var form = $('#add-monster div.active form');

    var type = $('input[name=type]', form).val();
    var initiative = parseInt($('input[name=initiative]', form).val());

    var description = $('input[name=description]', form).val();
    var armorClass = parseInt($('input[name=ac]', form).val());
    var hitPoints = parseInt($('input[name=hp]', form).val());

    $.ajax({
        url: '/encounter/' + encounterId,
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            id: 0,
            active: false,
            type: type,
            initiative: initiative,
            description: description,
            armorClass: armorClass,
            hitPoints: hitPoints,
            conditions: [],
            notes: ''
        }),
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a.add-party-member-button').on('click', function (evt) {
    var memberId = $(evt.target).closest('tr').attr('data-id');

    var dialog = $('#initiative-dialog');
    dialog.attr('data-id', memberId);

    $('input[name=initiative]', dialog).val(d20());

    dialog.modal();
});

$('#initiative-dialog button.btn-primary').on('click', function (evt) {
    var encounterId = $('h1[data-id]').attr('data-id');

    var dialog = $('#initiative-dialog');
    var memberId = dialog.attr('data-id');
    var initiative = dialog.find('form input[name=initiative]').val();

    $.ajax({
        url: '/encounter/' + encounterId + "/party/",
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({memberId: memberId, initiative: initiative}),
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

var d20 = function (times, adjustment) {
    var value = 0;

    for (var i = 0; i < (times | 1); i++) {
        value += (Math.round(Math.random() * 100) % 20 + (adjustment | 0))
    }

    return value;
};
