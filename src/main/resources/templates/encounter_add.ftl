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
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content">
                        <div role="tabpanel" class="tab-pane active" id="monster-new-panel">
                            <form>
                                <input type="hidden" name="type" value="MONSTER"/>
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
                                <div class="form-group">
                                    <label>Experience Points</label>
                                    <input type="number" class="form-control" name="xp" placeholder="Experience Points">
                                </div>
                            </form>
                        </div>

                        <div role="tabpanel" class="tab-pane" id="monster-existing-panel">
                            <p>This will pull from a list of existing configured monsters.</p>
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