DROP TABLE IF EXISTS STATE;
DROP TABLE IF EXISTS COMMAND;

CREATE TABLE STATE (
    id INT AUTO_INCREMENT PRIMARY KEY ,
    state VARCHAR(100) NOT NULL
);

INSERT INTO STATE (state) VALUES
('alaska'),
('arizona'),
('alabama'),
('arkansas'),
('california'),
('colorado'),
('connecticut'),
('delaware'),
('florida'),
('georgia'),
('hawaii'),
('idaho'),
('illinois'),
('indiana'),
('iowa'),
('kansas'),
('kentucky'),
('louisiana'),
('maine'),
('maryland'),
('massachusetts'),
('michigan'),
('minnesota'),
('mississippi'),
('missouri'),
('montana'),
('nebraska'),
('nevada'),
('new hampshire'),
('new jersey'),
('new mexico'),
('new york'),
('north carolina'),
('north dakota'),
('ohio'),
('oklahoma'),
('oregon'),
('pennsylvania'),
('rhode island'),
('south carolina'),
('south dakota'),
('tennessee'),
('texas'),
('utah'),
('vermont'),
('virginia'),
('washington'),
('west virginia'),
('wisconsin'),
('wyoming');


CREATE TABLE COMMAND (
  state_Id INT,
  command VARCHAR(250) NOT NULL,
  frequency INT DEFAULT 0
);

