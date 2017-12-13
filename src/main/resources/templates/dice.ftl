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
            <h1><img src="/img/rolling-dices.png" style="width: 64px;"/> Dice</h1>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">

            <form action="/dice/roll" method="get">
                <div class="form-group">
                    <label>Dice:</label>
                    <input type="text" class="form-control" name="d" value="${d}"/>
                </div>
                <button type="submit" class="btn btn-default">Roll</button>
            </form>

        </div>
    </div>

    <div class="row" style="margin-top: 12px">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">Rolled</div>
                <div class="panel-body">
                ${rolled} <span class="glyphicon glyphicon-arrow-right"></span> ${result}
                </div>
            </div>
        </div>
    </div>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>