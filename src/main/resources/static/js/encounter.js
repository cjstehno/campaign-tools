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

    if (type === 'MONSTER') {
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

    } else {
        var memberId = parseInt($('select[name=member]', form).val());

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
    }
});

// Math.round(Math.random() * 100) % 20