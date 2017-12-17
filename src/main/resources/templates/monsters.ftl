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
            <h1><img src="/img/evil-minion.png" style="width: 64px;"/> Monsters</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Page</th>
                    <th>Armor Class</th>
                    <th>Hit Dice</th>
                    <th>Experience Points</th>
                    <th>
                        <div class="pull-right">
                            <a href="#add-monster" class="btn btn-primary btn-sm" role="button" title="Add monster"><span
                                class="glyphicon glyphicon-plus"></span></a>
                            <a href="#import-monsters" class="btn btn-default btn-sm" role="button" title="Import monsters"><span
                                class="glyphicon glyphicon-cloud-upload"></span></a>
                            <a href="/monster/export" class="btn btn-default btn-sm" role="button" title="Export monsters"><span
                                class="glyphicon glyphicon-cloud-download"></span></a>
                        </div>
                    </th>
                </tr>
                </thead>
                <tbody>

                    <#list monsters as monster>
                    <tr data-id="${monster.id}">
                        <td class="monster-name">${monster.name}</td>
                        <td class="monster-page">${monster.page}</td>
                        <td class="monster-ac">${monster.armorClass}</td>
                        <td class="monster-hd">${monster.hitDice}</td>
                        <td class="monster-xp">${monster.experiencePoints}</td>
                        <td>
                            <div class="pull-right">
                                <a href="#edit-monster" class="btn btn-primary btn-sm" role="button" title="Edit monster"><span
                                    class="glyphicon glyphicon-pencil"></span></a>
                                <a href="#remove-monster" class="btn btn-danger btn-sm" role="button" title="Remove monster"><span
                                    class="glyphicon glyphicon-remove"></span></a>
                            </div>
                        </td>
                    </tr>
                    </#list>

                </tbody>
            </table>
        </div>
    </div>

</div>

<div id="add-monster-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/evil-minion.png" style="width: 32px;"/> Add Monster</h4>
            </div>
            <div class="modal-body">

                <form>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name" placeholder="Name"/>
                    </div>
                    <div class="form-group">
                        <label>Reference Page</label>
                        <input type="text" class="form-control" name="page" placeholder="MM-?"/>
                    </div>
                    <div class="form-group">
                        <label>Armor Class</label>
                        <input type="number" class="form-control" name="armorClass" placeholder="Armor class"/>
                    </div>
                    <div class="form-group">
                        <label>Hit Dice</label>
                        <input type="text" class="form-control" name="hitDice" placeholder="3d6+1"/>
                    </div>
                    <div class="form-group">
                        <label>Experience Points</label>
                        <input type="number" class="form-control" name="experiencePoints" placeholder="XP"/>
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

<div id="edit-monster-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/evil-minion.png" style="width: 32px;"/> Edit Monster</h4>
            </div>
            <div class="modal-body">

                <form>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" name="name"/>
                    </div>
                    <div class="form-group">
                        <label>Reference Page</label>
                        <input type="text" class="form-control" name="page"/>
                    </div>
                    <div class="form-group">
                        <label>Armor Class</label>
                        <input type="number" class="form-control" name="armorClass"/>
                    </div>
                    <div class="form-group">
                        <label>Hit Dice</label>
                        <input type="text" class="form-control" name="hitDice"/>
                    </div>
                    <div class="form-group">
                        <label>Experience Points</label>
                        <input type="number" class="form-control" name="experiencePoints"/>
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

<div id="remove-monster-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/evil-minion.png" style="width: 32px;"/> Remove Monster?</h4>
            </div>
            <div class="modal-body">

                <p>Are you sure you want to <strong>remove</strong> this monster from the party?</p>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger">Remove</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="import-monsters-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/evil-minion.png" style="width: 32px;"/> Import Monsters</h4>
            </div>
            <div class="modal-body">

                <form action="/monster/import" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="file">File input</label>
                        <input type="file" name="file" id="file">
                        <p class="help-block">Choose a monster JSON file to import.</p>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Import</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="/js/common.js"></script>
<script src="/js/monsters.js"></script>
</body>
</html>