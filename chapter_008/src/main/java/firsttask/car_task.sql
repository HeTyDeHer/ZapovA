CREATE TABLE transmission (
	id serial PRIMARY KEY,
    type varchar
);

CREATE TABLE engine (
	id serial PRIMARY KEY,
    type varchar,
    volume integer
);

CREATE TABLE body (
	id serial PRIMARY KEY,
    type varchar
);

INSERT INTO transmission (type) VALUES ('CVT'), ('AT'), ('MT');
INSERT INTO body (type) VALUES ('SEDAN'), ('ESTATE'), ('COUPE');
INSERT INTO engine (type, volume) VALUES ('diesel', 2000), ('gasoline', 1500), ('gasoline', 3000);

CREATE TABLE VEHICLE (
	id serial,
    transmission_id integer REFERENCES transmission(id),
    body_id integer REFERENCES body(id),
    engine_id integer REFERENCES engine(id)
);

INSERT INTO vehicle VALUES (1,1,1,1); 
INSERT INTO vehicle VALUES (2,1,2,1); 
INSERT INTO vehicle VALUES (3,1,2,3); 

SELECT transmission.type FROM transmission LEFT JOIN vehicle ON vehicle.transmission_id = transmission.id  WHERE vehicle.transmission_id IS null;
SELECT engine.type, engine.volume FROM engine LEFT JOIN vehicle ON vehicle.engine_id = engine.id  WHERE vehicle.engine_id IS null;
SELECT body.type FROM body LEFT JOIN vehicle ON vehicle.body_id = body.id  WHERE vehicle.body_id IS null;
