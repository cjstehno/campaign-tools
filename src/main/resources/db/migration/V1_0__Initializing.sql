--
-- Copyright (C) 2017 Christopher J. Stehno <chris@stehno.com>
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--         http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

CREATE TABLE IF NOT EXISTS party_member (
  id             BIGINT PRIMARY KEY AUTO_INCREMENT,
  character_name VARCHAR(25) NOT NULL,
  player_name    VARCHAR(25) NOT NULL,
  class_level    VARCHAR(40) NOT NULL,
  race           VARCHAR(10) NOT NULL,
  alignment      VARCHAR(20) NOT NULL,
  armor_class    INT         NOT NULL,
  perception     INT         NOT NULL
);

CREATE TABLE IF NOT EXISTS encounter (
  id        BIGINT PRIMARY KEY   AUTO_INCREMENT,
  name      VARCHAR(30) NOT NULL,
  finished  BOOLEAN     NOT NULL DEFAULT FALSE,
  round     INT                  DEFAULT NULL,
  active_id BIGINT               DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS encounter_participants (
  encounter_id BIGINT REFERENCES encounter (id),
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  ref_id       BIGINT,
  type         VARCHAR(20) NOT NULL,
  initiative   INT         NOT NULL,
  description  VARCHAR(30) NOT NULL,
  armor_class  INT         NOT NULL,
  hit_points   INT                DEFAULT NULL,
  conditions   ARRAY       NOT NULL
);

CREATE TABLE IF NOT EXISTS encounter_timers (
  encounter_id BIGINT REFERENCES encounter (id),
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  description  VARCHAR(25) NOT NULL,
  start_round  INT         NOT NULL,
  end_round    INT         NOT NULL
)