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
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/css/common.css" rel="stylesheet" />
    <link href="/css/encounter.css" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

<#include "navbar.ftl" />

<div class="container-fluid">

    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12">
            <h1 data-id="${encounter.id}"><img src="/img/encounter.png" style="width: 64px;"/> Encounter: ${encounter.name}</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12">

            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">Party</h3>
                </div>

                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Character (player)</th>
                        <th>Class (level)</th>
                        <th>Race</th>
                        <th>Alignment</th>
                        <th>Armor Class</th>
                        <th>Perception</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list party as member>
                    <tr>
                        <td>${member.characterName} (${member.playerName})</td>
                        <td>
                            <#list member.classes as lvl>
                                ${lvl.name} (${lvl.level})
                            </#list>
                        </td>
                        <td>${member.race}</td>
                        <td>${member.alignment}</td>
                        <td>${member.armorClass}</td>
                        <td>${member.perception}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>

            </div>

        </div>
    </div>

    <div class="row">
        <div class="col-md-12">

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Encounter</h3>
                </div>

                <div class="panel-body">
                    <div class="pull-left"><span class="glyphicon glyphicon-time"></span> Round 6 (36s)</div>
                    <div class="pull-right">
                        <a class="btn btn-sm btn-primary" href="#" role="button"><span class="glyphicon glyphicon-play"></span> Next</a>
                        <a class="btn btn-sm btn-success" href="#" role="button"><span class="glyphicon glyphicon-stop"></span> Finish</a>

                        <a class="btn btn-sm btn-default" href="#" role="button" data-toggle="modal" data-target="#add-monster"><span
                            class="glyphicon glyphicon-plus"></span> Add</a>
                    </div>
                </div>

                <table class="table table-striped">
                    <thead class="thead-dark">
                    <tr>
                        <th>Turn</th>
                        <th>Type</th>
                        <th>Initiative</th>
                        <th>Description</th>
                        <th>AC</th>
                        <th>HP</th>
                        <th>Conditions</th>
                        <th>Notes</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list encounter.participants as combatant>
                    <tr data-id="${combatant.id}">
                        <td>
                            <#if combatant.active><span class="glyphicon glyphicon-chevron-right" style="color: green;" title="Active combatant"></span><#else>&nbsp;</#if>
                        </td>
                        <td>
                            <img src="/img/${combatant.type.id}.png" style="width:32px" title="${combatant.type}"/>
                        </td>
                        <td><#if combatant.initiative &gt; 0>${combatant.initiative}<#else><em>n/a</em></#if></td>
                        <td>${combatant.description}</td>
                        <td>${combatant.armorClass}</td>
                        <td><#if combatant.hitPoints??>${combatant.hitPoints}<#else><em>n/a</em></#if></td>
                        <td>
                            <#list combatant.conditions as condition>
                                <span class="label label-info"><span class="glyphicon glyphicon-alert"></span> ${condition}</span>
                            </#list>
                        </td>
                        <td>${combatant.notes}</td>
                        <td class="pull-right">
                            <a class="btn btn-sm btn-primary" href="#" role="button"><span class="glyphicon glyphicon-pencil"></span> Edit</a>
                            <a class="btn btn-sm btn-danger" href="#" role="button" data-toggle="modal" data-target="#kill-monster"><span
                                class="glyphicon glyphicon-ban-circle"></span> Kill</a>
                            <a class="remove-button btn btn-sm btn-warning" href="#" role="button"><span class="glyphicon glyphicon-remove"></span> Remove</a>
                        </td>
                    </tr>
                    </#list>

                    </tbody>
                </table>

            </div>

        </div>
    </div>
</div>

<div id="add-monster" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/encounter.png" style="width: 32px;"/> Add To Encounter</h4>
            </div>
            <div class="modal-body">

                <div>
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#monster-new-panel" role="tab" data-toggle="tab">New Monster</a></li>
                        <li role="presentation"><a href="#monster-existing-panel" role="tab" data-toggle="tab">Existing Monster</a></li>
                        <li role="presentation"><a href="#party-add-panel" role="tab" data-toggle="tab">Party Member</a></li>
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="monster-new-panel">
                            <form>
                                <input type="hidden" name="type" value="MONSTER" />
                                <div class="form-group">
                                    <label>Description</label>
                                    <input type="text" class="form-control" name="description" placeholder="Description">
                                </div>
                                <div class="form-group">
                                    <label>Initiative</label>
                                    <input type="number" class="form-control" name="initiative" placeholder="Initiative">
                                </div>
                                <div class="form-group">
                                    <label>Armor Class</label>
                                    <input type="number" class="form-control" name="ac" placeholder="Armor Class">
                                </div>
                                <div class="form-group">
                                    <label>Hit Points</label>
                                    <input type="number" class="form-control" name="hp" placeholder="Hit Points">
                                </div>
                            </form>
                        </div>

                        <div role="tabpanel" class="tab-pane" id="monster-existing-panel">
                            <form>
                                <input type="hidden" name="type" value="MONSTER" />
                                <div class="form-group">
                                    <label>Template</label>
                                    <select name="monster-template" class="form-control">
                                        <option>Select Monster</option>
                                        <#list monsters as monster>
                                            <option value="${monster.url}">${monster.name}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Description</label>
                                    <input type="text" class="form-control" name="description" placeholder="Description">
                                </div>
                                <div class="form-group">
                                    <label>Initiative</label>
                                    <input type="number" class="form-control" name="initiative" placeholder="Initiative">
                                </div>
                                <div class="form-group">
                                    <label>Armor Class</label>
                                    <input type="number" class="form-control" name="ac" placeholder="Armor Class">
                                </div>
                                <div class="form-group">
                                    <label>Hit Points</label>
                                    <input type="number" class="form-control" name="hp" placeholder="Hit Points">
                                </div>
                            </form>
                        </div>

                        <div role="tabpanel" class="tab-pane" id="party-add-panel">
                            <form>
                                <input type="hidden" name="type" value="PARTY_MEMBER" />
                                <div class="form-group">
                                    <label>Description</label>
                                    <select name="member" class="form-control">
                                        <#list party as member>
                                            <option value="${member.id}">${member.characterName} (${member.playerName})</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Initiative</label>
                                    <input type="number" class="form-control" name="initiative" placeholder="Initiative">
                                </div>
                            </form>
                        </div>
                    </div>

                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Add</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="remove-monster" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/encounter.png" style="width: 32px;"/> Remove Participant?</h4>
            </div>
            <div class="modal-body">

                <p>Are you sure you want to <strong>remove</strong> this participant from the encounter?</p>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger">Remove</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="kill-monster" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/encounter.png" style="width: 32px;"/> Kill Participant?</h4>
            </div>
            <div class="modal-body">

                <p>Are you sure you want to <strong>kill</strong> this participant (it will <em>not</em> be removed from the encounter)?</p>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger">Kill</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>
<script src="/js/encounter.js"></script>
</body>
</html>