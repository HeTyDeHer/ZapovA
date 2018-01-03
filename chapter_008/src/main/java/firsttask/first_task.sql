CREATE TABLE roles (
     role varchar (30) primary key,
    description varchar (30) 
);
INSERT INTO roles (role, description) VALUES ('admin', 'all permissions');
INSERT INTO roles (role, description) VALUES ('moderator', 'read/write permissions');
INSERT INTO roles (role, description) VALUES ('user', 'read permissions');

-- users and roles - one to one

CREATE TABLE users (
	id serial primary key,
   name varchar (30) NOT NULL,
   password varchar (30) NOT NULL,
   roles varchar (30) REFERENCES roles (role) DEFAULT 'user' NOT NULL,
   create_date timestamp
);

INSERT INTO users (name, password) VALUES ('name1', 'pwd1');
INSERT INTO users (name, password) VALUES ('name2', 'pwd2');
INSERT INTO users (name, password) VALUES ('name3', 'pwd3');
INSERT INTO users (name, password) VALUES ('name4', 'pwd4');

CREATE TABLE rules (
	permission varchar (30) primary key,
    description varchar (1000)
); 

INSERT INTO rules (permission, description) VALUES ('kill', 'permission to kill everybody');
INSERT INTO rules (permission, description) VALUES ('delete', 'permission to delete request');
INSERT INTO rules (permission, description) VALUES ('edit', 'permission to edit request');
INSERT INTO rules (permission, description) VALUES ('add', 'permission to add request');
INSERT INTO rules (permission, description) VALUES ('read', 'permission to read requests');

-- rules and roles - many to many

CREATE TABLE rules4roles (
	role varchar REFERENCES roles (role),
    rule varchar REFERENCES rules (permission)
);
INSERT INTO rules4roles (role, rule) VALUES ('admin', 'kill');
INSERT INTO rules4roles (role, rule) VALUES ('admin', 'delete');
INSERT INTO rules4roles (role, rule) VALUES ('admin', 'edit');
INSERT INTO rules4roles (role, rule) VALUES ('admin', 'add');
INSERT INTO rules4roles (role, rule) VALUES ('admin', 'read');
INSERT INTO rules4roles (role, rule) VALUES ('moderator', 'delete');
INSERT INTO rules4roles (role, rule) VALUES ('moderator', 'edit');
INSERT INTO rules4roles (role, rule) VALUES ('moderator', 'add');
INSERT INTO rules4roles (role, rule) VALUES ('moderator', 'read');
INSERT INTO rules4roles (role, rule) VALUES ('user', 'read');

CREATE TABLE items (
	id serial PRIMARY KEY,
    author_id integer REFERENCES users (id) NOT NULL,
    priority varchar (30) NOT NULL DEFAULT 'low',
    text varchar (1000) NOT NULL
  
);

CREATE TABLE comments (
	id serial PRIMARY KEY,
    item_id integer REFERENCES items (id) NOT NULL,
	comment_text varchar (2000) NOT NULL
);

CREATE TABLE files (
	id serial PRIMARY KEY,
	file_path varchar (3000) NOT NULL, 
    item_id integer REFERENCES items (id) NOT NULL
);

CREATE TABLE state (
	state varchar PRIMARY KEY,
    state_description varchar (1000)
);

CREATE TABLE category (
	category varchar PRIMARY KEY,
    category_description varchar (1000)
);

-- items - many items for one user

INSERT INTO items (author_id, text) VALUES (4, 'text for item 1');
INSERT INTO items (author_id, text) VALUES (3, 'text for item 2');
INSERT INTO items (author_id, text) VALUES (2, 'text for item 3');
INSERT INTO items (author_id, text) VALUES (1, 'text for item 4');
INSERT INTO items (author_id, text) VALUES (1, 'text for item 5');

-- comments - many comments for one item

INSERT INTO comments (item_id, comment_text) VALUES (1, 'comment for item 1');
INSERT INTO comments (item_id, comment_text) VALUES (2, 'comment for item 2');
INSERT INTO comments (item_id, comment_text) VALUES (3, 'comment for item 3');
INSERT INTO comments (item_id, comment_text) VALUES (4, 'comment for item 4');
INSERT INTO comments (item_id, comment_text) VALUES (4, 'another one comment for item 4');

-- files - many files for one item

INSERT INTO files (item_id, file_path) VALUES (1, 'C:/Very_long_file_path/file1.file');
INSERT INTO files (item_id, file_path) VALUES (1, 'C:/Very_long_file_path/file2.file');
INSERT INTO files (item_id, file_path) VALUES (3, 'C:/Very_long_file_path/file15.file');
INSERT INTO files (item_id, file_path) VALUES (3, 'C:/Very_long_file_path/file155.file');
INSERT INTO files (item_id, file_path) VALUES (4, 'C:/Very_long_file_path/file1123.file');

INSERT INTO state (state, state_description) VALUES ('created', 'just_created');
INSERT INTO state (state, state_description) VALUES ('opened', 'opened');
INSERT INTO state (state, state_description) VALUES ('closed', 'closed');
INSERT INTO state (state, state_description) VALUES ('processing', 'in_processing');

INSERT INTO category (category, category_description) VALUES ('category1', 'category1 description');
INSERT INTO category (category, category_description) VALUES ('category2', 'category2 description');
INSERT INTO category (category, category_description) VALUES ('category3', 'category3 description');
INSERT INTO category (category, category_description) VALUES ('category4', 'category4 description');

--state and category - one to many items

ALTER TABLE items ADD COLUMN state varchar (1000) REFERENCES state (state) DEFAULT 'created';
ALTER TABLE items ADD COLUMN category varchar (1000) REFERENCES category (category);

UPDATE items SET category = 'category1';
UPDATE items SET category = 'category4' WHERE id = 2;
UPDATE items SET category = 'category3' WHERE id = 3;
UPDATE items SET category = 'category3' WHERE id = 1;

