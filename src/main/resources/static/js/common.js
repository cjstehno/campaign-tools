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
function inputValue(name, parent) {
    return parent.find(`input[name=${name}]`).val();
}

function inputIntValue(name, parent) {
    return parseInt(inputValue(name, parent));
}

const http = {
    get: function (path, handler) {
        $.ajax({
            url: path,
            type: 'GET',
            dataType: 'json',
            success: handler
        });
    },

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

class Form {

    constructor(selector) {
        this.elt = $(selector);
    }

    text(name, value) {
        if (value !== undefined) {
            $(`input[name=${name}]`, this.elt).val(value);
        } else {
            return $(`input[name=${name}]`, this.elt).val();
        }
    }

    textInt(name) {
        return parseInt(this.text(name));
    }
}