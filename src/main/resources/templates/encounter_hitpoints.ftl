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

<div id="hp-adjust-dialog" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><img src="/img/encounter.png" style="width: 32px;"/> Ajust Hit Points</h4>
            </div>
            <div class="modal-body">

                <form>
                    <div class="form-group">
                        <label>Current:</label>
                        <input readonly class="form-control" name="current" value="0" />
                    </div>
                    <div class="form-group">
                        <label>Reduce By:</label>
                        <input type="number" class="form-control" name="reduce" value="0" />
                    </div>
                    <div class="form-group">
                        <label>Increase By:</label>
                        <input type="number" class="form-control" name="increase" value="0"/>
                    </div>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary">Adjust</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->