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
$('a[href="#add-member"]').on('click', function (evt) {
    evt.preventDefault();
    $('#add-member-dialog').modal();
});

$('#add-member-dialog .btn-primary').on('click', function (evt) {
    var form = $('#add-member-dialog');
    var characterName = form.find('input[name=characterName]').val();
    var playerName = form.find('input[name=playerName]').val();
    var classLevel = form.find('input[name=classLevel]').val();
    var race = form.find('input[name=race]').val();
    var alignment = form.find('input[name=alignment]').val();
    var armorClass = form.find('input[name=armorClass]').val();
    var perception = form.find('input[name=perception]').val();

    $.ajax({
        url: '/party',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            characterName: characterName,
            playerName: playerName,
            classLevel: classLevel,
            race: race,
            alignment: alignment,
            armorClass: armorClass,
            perception: perception
        }),
        success: function (result) {
            location = '/party';
        }
    });
});

$('a[href="#edit-member"]').on('click', function (evt) {
    evt.preventDefault();

    var memberRow = $(evt.target).closest('tr');
    var memberId = memberRow.attr('data-id');

    var dialog = $('#edit-member-dialog');
    dialog.attr('data-id', memberId);

    $('input[name=characterName]', dialog).val($('td.character-name', memberRow).text());
    $('input[name=playerName]', dialog).val($('td.player-name', memberRow).text());
    $('input[name=race]', dialog).val($('td.race', memberRow).text());
    $('input[name=classLevel]', dialog).val($('td.class-level', memberRow).text());
    $('input[name=alignment]', dialog).val($('td.alignment', memberRow).text());
    $('input[name=armorClass]', dialog).val($('td.armor-class', memberRow).text());
    $('input[name=perception]', dialog).val($('td.perception', memberRow).text());

    dialog.modal();
});

$('#edit-member-dialog .btn-primary').on('click', function (evt) {
    var dialog = $('#edit-member-dialog');
    var memberId = dialog.attr('data-id');
    var characterName = dialog.find('input[name=characterName]').val();
    var playerName = dialog.find('input[name=playerName]').val();
    var classLevel = dialog.find('input[name=classLevel]').val();
    var race = dialog.find('input[name=race]').val();
    var alignment = dialog.find('input[name=alignment]').val();
    var armorClass = dialog.find('input[name=armorClass]').val();
    var perception = dialog.find('input[name=perception]').val();

    $.ajax({
        url: '/party/' + memberId,
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            id: memberId,
            characterName: characterName,
            playerName: playerName,
            classLevel: classLevel,
            race: race,
            alignment: alignment,
            armorClass: armorClass,
            perception: perception
        }),
        success: function (result) {
            location = '/party';
        }
    });
});

$('a[href="#remove-member"]').on('click', function (evt) {
    var memberId = $(evt.target).closest('tr').attr('data-id');

    var dialog = $('#remove-member-dialog');
    dialog.attr('data-id', memberId);
    dialog.modal();
});

$('#remove-member-dialog button.btn-danger').on('click', function (evt) {
    var memberId = $('#remove-member-dialog').attr('data-id');

    $.ajax({
        url: '/party/' + memberId,
        type: 'DELETE',
        success: function (result) {
            location = '/party';
        }
    });
});

$('a[href="#import-members"]').on('click', function (evt) {
    var dialog = $('#import-members-dialog');
    dialog.modal();
});

$('#import-members-dialog button.btn-primary').on('click', function (evt) {
    $('#import-members-dialog').find('form').submit();
});