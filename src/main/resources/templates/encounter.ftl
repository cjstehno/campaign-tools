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
    <link href="/css/common.css" rel="stylesheet"/>
    <link href="/css/encounter.css" rel="stylesheet"/>

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
            <h1 data-id="${encounter.id}"><img src="/img/encounter.png" style="width: 64px;"/> Encounter: ${encounter.name}<#if encounter.finished> (Finished)</#if></h1>
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
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list party as member>
                    <tr data-id="${member.id}">
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
                        <td>
                            <div class="pull-right">
                                <#if encounter.containsPartyMember(member.id) >
                                    <a class="add-party-member-button btn btn-sm btn-default disabled" href="#" role="button"><span
                                        class="glyphicon glyphicon-plus" title="Add to Encounter"></span></a>
                                <#else>
                                    <a class="add-party-member-button btn btn-sm btn-default <#if encounter.finished>disabled</#if>" href="#" role="button"><span
                                        class="glyphicon glyphicon-plus" title="Add to Encounter"></span></a>
                                </#if>
                            </div>
                        </td>
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
                    <div class="pull-left">
                        <span class="glyphicon glyphicon-time"></span>
                    <#if elapsed.time?? >
                        Round ${encounter.round} (${elapsed.time})
                    <#else>
                        <em>Not started</em>
                    </#if>
                    </div>
                    <div class="pull-right">

                        <a class="start-button <#if encounter.finished || encounter.round?? >disabled</#if> btn btn-sm btn-default" href="#" role="button"><span class="glyphicon glyphicon-play"></span> Start</a>
                        <a class="next-button <#if encounter.finished || !encounter.round?? >disabled</#if> btn btn-sm btn-primary" href="#" role="button"><span class="glyphicon glyphicon-step-forward"></span>Next</a>
                        <a class="finish-button <#if encounter.finished || !encounter.round?? >disabled</#if> btn btn-sm btn-success" href="#" role="button"><span class="glyphicon glyphicon-stop"></span>Finish</a>

                        <a class="btn btn-sm btn-default <#if encounter.finished>disabled</#if>" href="#add-monster" role="button"><span class="glyphicon glyphicon-plus"></span> Add</a>
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
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list encounter.participants as combatant>
                    <tr data-id="${combatant.id}">
                        <td>
                            <#if encounter.activeId?? && encounter.activeId == combatant.id >
                                <span class="glyphicon glyphicon-chevron-right" style="color: green;" title="Active combatant"></span>
                            <#else>&nbsp;
                            </#if>
                        </td>
                        <td>
                            <img src="/img/${combatant.type.id}.png" style="width:32px" title="${combatant.type}"/>
                        </td>
                        <td><#if combatant.initiative &gt; 0>${combatant.initiative}<#else><em>n/a</em></#if></td>
                        <td <#if combatant.hitPoints??>class="participant-description"</#if>>${combatant.description}</td>
                        <td>${combatant.armorClass}</td>
                        <#if combatant.hitPoints?? >
                        <td class="participant-hp">${combatant.hitPoints}</td>
                        <#else>
                        <td><em>n/a</em></td>
                        </#if>
                        <td data-conditions="<#list combatant.conditions as c>${c.name()} </#list>">
                            <#list combatant.conditions as condition>
                                <span class="label label-info"><span class="glyphicon glyphicon-alert"></span> ${condition.label}</span>
                            <#else>
                                &nbsp;
                            </#list>
                        </td>
                        <td>
                            <div class="pull-right">
                                <#if combatant.hitPoints?? >
                                <a class="adjust-hp-button btn btn-sm btn-default <#if encounter.finished>disabled</#if>" href="#" role="button"><img src="/img/heart-beats.png"
                                                                                                               style="width: 24px;" title="Adjust HP"></a>
                                </#if>
                                <a class="conditions-button btn btn-sm btn-default <#if encounter.finished>disabled</#if>" href="#" role="button"><img src="/img/heart-inside.png"
                                                                                                                style="width: 24px;"
                                                                                                                title="Conditions"></a>
                                <a class="remove-button btn btn-sm btn-warning <#if encounter.finished>disabled</#if>" href="#" role="button"><span class="glyphicon glyphicon-remove"
                                                                                                             title="Remove from Encounter"></span></a>
                            </div>
                        </td>
                    </tr>
                    </#list>

                    </tbody>
                </table>

            </div>

        </div>
    </div>
</div>

<#include "encounter_add.ftl" />
<#include "encounter_remove.ftl" />
<#include "encounter_initiative.ftl" />
<#include "encounter_hitpoints.ftl" />

<div id="conditions-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/encounter.png" style="width: 32px;"/> Conditions</h4>
            </div>
            <div class="modal-body">

                <form>
                    <#list conditions as condition>
                        <div class="checkbox">
                            <label><input type="checkbox" name="conditions" value="${condition.name()}"> ${condition.label}</label>
                        </div>
                    </#list>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Apply</button>
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