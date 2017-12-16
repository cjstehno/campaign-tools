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
    const encounterId = $('h1[data-id]').attr('data-id');
    const participantId = $('#remove-monster').attr('data-id');

    $.ajax({
        url: '/encounter/' + encounterId + '/' + participantId,
        type: 'DELETE',
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a.remove-button').on('click', function (evt) {
    const participantId = $(evt.target).closest('tr').attr('data-id');

    const dialog = $('#remove-monster');
    dialog.attr('data-id', participantId);
    dialog.modal();
});

$('a[href="#add-monster"]').on('click', function (evt) {
    const dialog = $('#add-monster');
    $('form input[name=initiative]', dialog).val(d20());
    dialog.modal();
});

$('#monster-existing-panel select').on('change', function (evt) {
    const elt = $(evt.target);
    const monsterId = elt.val();

    http.get(`/monster/${monsterId}/participant`, function (monster) {
        const form = new Form('#add-monster div.active form');
        form.text('description', monster.description);
        form.text('initiative', monster.initiative);
        form.text('ac', monster.armorClass);
        form.text('hp', monster.hitPoints);
        form.text('xp', monster.experiencePoints);
    });
});

$('#add-monster button.btn-primary').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');
    const form = new Form('#add-monster div.active form');

    http.post(`/encounter/${encounterId}`, `/encounter/${encounterId}`, {
        id: 0,
        active: false,
        type: form.text('type'),
        initiative: form.textInt('initiative'),
        description: form.text('description'),
        armorClass: form.textInt('ac'),
        hitPoints: form.textInt('hp'),
        conditions: [],
        experiencePoints: form.textInt('xp')
    });
});

$('a.add-party-member-button').on('click', function (evt) {
    const memberId = $(evt.target).closest('tr').attr('data-id');

    const dialog = $('#initiative-dialog');
    dialog.attr('data-id', memberId);

    $('input[name=initiative]', dialog).val(d20());

    dialog.modal();
});

$('#initiative-dialog button.btn-primary').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');

    const dialog = $('#initiative-dialog');
    const memberId = dialog.attr('data-id');
    const initiative = dialog.find('form input[name=initiative]').val();

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
    let value = 0;

    for (let i = 0; i < (times | 1); i++) {
        value += (Math.round(Math.random() * 100) % 20 + (adjustment | 0))
    }

    return value;
};

$('td.participant-hp').on('dblclick', function (evt) {
    const elt = $(evt.target);
    const participantId = $(evt.target).closest('tr').attr('data-id');
    const value = elt.text();

    elt.replaceWith('<td class="participant-hp"><input type="number" style="width:3em" value="' + value + '"/></td>');

    const newElt = $('td.participant-hp input');

    const savingFx = function () {
        const encounterId = $('h1[data-id]').attr('data-id');

        $.ajax({
            url: '/encounter/' + encounterId + '/' + participantId + '/hp',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({hp: parseInt(newElt.val())}),
            success: function (result) {
                location = '/encounter/' + encounterId;
            }
        });
    };

    newElt.on('keypress', function (e) {
        if (e.keyCode == 13) {
            savingFx();
        }
    });
    newElt.on('blur', function (e) {
        savingFx();
    });
});

$('td.participant-description').on('dblclick', function (evt) {
    const elt = $(evt.target);
    const participantId = $(evt.target).closest('tr').attr('data-id');
    const value = elt.text();

    elt.replaceWith('<td class="participant-description"><input type="text" style="width:10em" value="' + value + '"/></td>');

    const newElt = $('td.participant-description input');

    const savingFx = function () {
        const encounterId = $('h1[data-id]').attr('data-id');

        $.ajax({
            url: '/encounter/' + encounterId + '/' + participantId + '/description',
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({description: newElt.val()}),
            success: function (result) {
                location = '/encounter/' + encounterId;
            }
        });
    };

    newElt.on('keypress', function (e) {
        if (e.keyCode == 13) {
            savingFx();
        }
    });
    newElt.on('blur', function (e) {
        savingFx();
    });
});

$('a.adjust-hp-button').on('click', function (evt) {
    const elt = $(evt.target);
    const participantId = elt.closest('tr').attr('data-id');
    const currentHp = elt.closest('tr').find('td.participant-hp').text();

    console.log("current hp: " + currentHp);

    const dialog = $('#hp-adjust-dialog');
    dialog.attr('data-id', participantId);
    $('input[name=current]', dialog).val(currentHp);
    dialog.modal();
});

$('#hp-adjust-dialog button.btn-primary').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');

    const dialog = $('#hp-adjust-dialog');
    const participantId = dialog.attr('data-id');
    const hpCurrent = parseInt(dialog.find('form input[name=current]').val());
    const hpDecrease = parseInt(dialog.find('form input[name=reduce]').val());
    const hpIncrease = parseInt(dialog.find('form input[name=increase]').val());

    $.ajax({
        url: '/encounter/' + encounterId + '/' + participantId + '/hp',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({hp: hpCurrent - hpDecrease + hpIncrease}),
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a.conditions-button').on('click', function (evt) {
    const elt = $(evt.target);
    const participantId = elt.closest('tr').attr('data-id');

    const dialog = $('#conditions-dialog');
    dialog.attr('data-id', participantId);

    $('input[name=conditions]', dialog).prop('checked', false);

    const conditions = elt.closest('tr').find('td[data-conditions]').attr('data-conditions');
    if (conditions) {
        const items = conditions.trim().split(' ');
        for (let i = 0; i < items.length; i++) {
            $('input[value=' + items[i] + ']', dialog).prop('checked', true);
        }
    }

    dialog.modal();
});

$('#conditions-dialog button.btn-primary').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');

    const dialog = $('#conditions-dialog');
    const participantId = dialog.attr('data-id');
    // var conditions = $('input[name=conditions]:checked', dialog).val();

    const conditions = $('input:checkbox:checked').map(function () {
        return this.value;
    }).get();

    console.log('conditions: ' + conditions);
    console.log('json: ' + JSON.stringify({conditions: conditions}))

    $.ajax({
        url: '/encounter/' + encounterId + '/' + participantId + '/conditions',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({conditions: conditions}),
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a.start-button').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');

    $.ajax({
        url: '/encounter/' + encounterId + '/start',
        type: 'POST',
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a.next-button').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');

    $.ajax({
        url: '/encounter/' + encounterId + '/next',
        type: 'POST',
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a.finish-button').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');

    $.ajax({
        url: '/encounter/' + encounterId + '/stop',
        type: 'POST',
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});


$('a[href="#add-timer"]').on('click', function (evt) {
    evt.preventDefault();
    $('#add-timer-dialog').modal();
});

$('#add-timer-dialog .btn-primary').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');

    const form = $('#add-timer-dialog');
    const description = form.find('input[name=description]').val();
    const startRound = parseInt(form.find('input[name=startRound]').val());
    const duration = parseInt(form.find('input[name=duration]').val());

    $.ajax({
        url: '/encounter/' + encounterId + '/timer',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            description: description,
            startRound: startRound,
            endRound: startRound + duration
        }),
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});

$('a[href="#remove-timer"]').on('click', function (evt) {
    const timerId = $(evt.target).closest('tr').attr('data-id');

    const dialog = $('#remove-timer-dialog');
    dialog.attr('data-id', timerId);
    dialog.modal();
});

$('#remove-timer-dialog button.btn-danger').on('click', function (evt) {
    const encounterId = $('h1[data-id]').attr('data-id');
    const timerId = $('#remove-timer-dialog').attr('data-id');

    $.ajax({
        url: '/encounter/' + encounterId + '/timer/' + timerId,
        type: 'DELETE',
        success: function (result) {
            location = '/encounter/' + encounterId;
        }
    });
});