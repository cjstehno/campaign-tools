<#--

    Copyright (C) 2017 Christopher J. Stehno <chris@stehno.com>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>DM Campaign Tools</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet"/>
    <link href="css/common.css" rel="stylesheet"/>

    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<#include "navbar.ftl" />

<div class="container-fluid">

    <div class="row">
        <div class="col-md-12">
            <h1><img src="/img/medieval-pavilion.png" style="width: 64px;"/> Party Members</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table">
                <thead>
                <tr>
                    <th>Character</th>
                    <th>Player</th>
                    <th>Race</th>
                    <th>Class (Level)</th>
                    <th>Alignment</th>
                    <th>Armor Class</th>
                    <th>Perception</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>

                    <#list members as member>
                    <tr data-id="${member.id}">
                        <td class="character-name">${member.characterName}</td>
                        <td class="player-name">${member.playerName}</td>
                        <td class="race">${member.race}</td>
                        <td class="class-level">${member.classLevel}</td>
                        <td class="alignment">${member.alignment}</td>
                        <td class="armor-class">${member.armorClass}</td>
                        <td class="perception">${member.perception}</td>
                        <td>
                            <div class="pull-right">
                                <a href="#edit-member" class="btn btn-primary btn-sm" role="button"><span class="glyphicon glyphicon-pencil"></span> Edit</a>
                                <a href="#remove-member" class="btn btn-danger btn-sm" role="button"><span class="glyphicon glyphicon-remove"></span> Delete</a>
                            </div>
                        </td>
                    </tr>
                    </#list>

                </tbody>
            </table>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="pull-right">
                <a href="#add-member" class="btn btn-primary" role="button"><span class="glyphicon glyphicon-plus"></span> Add Member</a>
            </div>
        </div>
    </div>

</div>

<div id="add-member-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/medieval-pavilion.png" style="width: 32px;"/> Add Party Member</h4>
            </div>
            <div class="modal-body">

                <form>
                    <div class="form-group">
                        <label>Character Name</label>
                        <input type="text" class="form-control" name="characterName" placeholder="Character Name">
                    </div>
                    <div class="form-group">
                        <label>Player Name</label>
                        <input type="text" class="form-control" name="playerName" placeholder="Player Name">
                    </div>
                    <div class="form-group">
                        <label>Class (Level)</label>
                        <input type="text" class="form-control" name="classLevel" placeholder="Class (level)...">
                    </div>
                    <div class="form-group">
                        <label>Race</label>
                        <input type="text" class="form-control" name="race" placeholder="Race">
                    </div>
                    <div class="form-group">
                        <label>Alignment</label>
                        <input type="text" class="form-control" name="alignment" placeholder="Alignment">
                    </div>
                    <div class="form-group">
                        <label>Aromor Class</label>
                        <input type="number" class="form-control" name="armorClass" placeholder="Armor Class">
                    </div>
                    <div class="form-group">
                        <label>Passive Perception</label>
                        <input type="number" class="form-control" name="perception" placeholder="Passive Perception">
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Add</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="edit-member-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/medieval-pavilion.png" style="width: 32px;"/> Edit Party Member</h4>
            </div>
            <div class="modal-body">

                <form>
                    <div class="form-group">
                        <label>Character Name</label>
                        <input type="text" class="form-control" name="characterName" />
                    </div>
                    <div class="form-group">
                        <label>Player Name</label>
                        <input type="text" class="form-control" name="playerName" />
                    </div>
                    <div class="form-group">
                        <label>Class (Level)</label>
                        <input type="text" class="form-control" name="classLevel" />
                    </div>
                    <div class="form-group">
                        <label>Race</label>
                        <input type="text" class="form-control" name="race" />
                    </div>
                    <div class="form-group">
                        <label>Alignment</label>
                        <input type="text" class="form-control" name="alignment" />
                    </div>
                    <div class="form-group">
                        <label>Aromor Class</label>
                        <input type="number" class="form-control" name="armorClass" />
                    </div>
                    <div class="form-group">
                        <label>Passive Perception</label>
                        <input type="number" class="form-control" name="perception" /">
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Update</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="remove-member-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/medieval-pavilion.png" style="width: 32px;"/> Remove Party Member?</h4>
            </div>
            <div class="modal-body">

                <p>Are you sure you want to <strong>remove</strong> this member from the party?</p>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger">Remove</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="/js/party.js"></script>
</body>
</html>