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
$('a[href="#add-monster"]').on('click', function (evt) {
    evt.preventDefault();
    $('#add-monster-dialog').modal();
});

function inputValue(name, parent) {
    return parent.find(`input[name=${name}]`).val();
}

function inputIntValue(name, parent) {
    return parseInt(inputValue(name, parent));
}

const http = {
    post: function (path, redirectTo, payload) {
        $.ajax({
            url: path,
            type: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(payload),
            success: function (result) {
                location = redirectTo;
            }
        });
    },

    delete: function (path, redirectTo) {
        $.ajax({
            url: path,
            type: 'DELETE',
            success: function (result) {
                location = redirectTo;
            }
        });
    }
};

$('#add-monster-dialog .btn-primary').on('click', function (evt) {
    const form = $('#add-monster-dialog');
    const name = inputValue('name', form);
    const page = inputValue('page', form);
    const ac = inputIntValue('armorClass', form);
    const hd = inputValue('hitDice', form);
    const xp = inputIntValue('experiencePoints', form);

    http.post('/monster', '/monster', {
        name: name,
        page: page,
        armorClass: ac,
        hitDice: hd,
        experiencePoints: xp
    });
});

$('a[href="#edit-monster"]').on('click', function (evt) {
    evt.preventDefault();

    const monsterRow = $(evt.target).closest('tr');
    const monsterId = monsterRow.attr('data-id');

    const dialog = $('#edit-monster-dialog');
    dialog.attr('data-id', monsterId);

    $('input[name=name]', dialog).val($('td.monster-name', monsterRow).text());
    $('input[name=page]', dialog).val($('td.monster-page', monsterRow).text());
    $('input[name=armorClass]', dialog).val($('td.monster-ac', monsterRow).text());
    $('input[name=hitDice]', dialog).val($('td.monster-hd', monsterRow).text());
    $('input[name=experiencePoints]', dialog).val($('td.monster-xp', monsterRow).text());

    dialog.modal();
});

$('#edit-monster-dialog .btn-primary').on('click', function (evt) {
    const dialog = $('#edit-monster-dialog');
    const monsterId = dialog.attr('data-id');

    const name = inputValue('name', dialog);
    const page = inputValue('page', dialog);
    const armorClass = inputIntValue('armorClass', dialog);
    const hitDice = inputValue('hitDice', dialog);
    const experiencePoints = inputIntValue('experiencePoints', dialog);

    http.post(`/monster/${monsterId}`, '/monster', {
        id: monsterId,
        name: name,
        page: page,
        armorClass: armorClass,
        hitDice: hitDice,
        experiencePoints: experiencePoints
    });
});

$('a[href="#remove-monster"]').on('click', function (evt) {
    const monsterId = $(evt.target).closest('tr').attr('data-id');

    const dialog = $('#remove-monster-dialog');
    dialog.attr('data-id', monsterId);
    dialog.modal();
});

$('#remove-monster-dialog button.btn-danger').on('click', function (evt) {
    const monsterId = $('#remove-monster-dialog').attr('data-id');
    http.delete(`/monster/${monsterId}`, '/monster');
});

$('a[href="#import-monsters"]').on('click', function (evt) {
    $('#import-monsters-dialog').modal();
});

$('#import-monsters-dialog button.btn-primary').on('click', function (evt) {
    $('#import-monsters-dialog').find('form').submit();
});