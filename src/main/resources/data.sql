INSERT INTO users (first_name, last_name, job_description, email, password, profile_image, enabled, apikey)
VALUES ('Cyan', 'Dalebout','Music Producer', 'cyan@example.com', '$2a$10$WxL.vHS2YHw4hovNoCUCSuv2lh5Lrn6yP4foEbITfJr59zD30.Opa', 'profile_image_one.jpeg1707838272170', true, '');
INSERT INTO users (first_name, last_name, job_description, email, password, profile_image, enabled, apikey)
VALUES ('Emma', 'Johnson','A&R Manager', 'emma@example.com', '$2a$10$WxL.vHS2YHw4hovNoCUCSuv2lh5Lrn6yP4foEbITfJr59zD30.Opa', 'profile_image_one.jpeg1707838272170', true, '');
INSERT INTO users (first_name, last_name, job_description, email, password, profile_image, enabled, apikey)
VALUES ('Liam', 'Martinez','Song Writer', 'liam@example.com', '$2a$10$WxL.vHS2YHw4hovNoCUCSuv2lh5Lrn6yP4foEbITfJr59zD30.Opa', 'profile_image_one.jpeg1707838272170', true, '');
INSERT INTO users (first_name, last_name, job_description, email, password, profile_image, enabled, apikey)
VALUES ('Olivia', 'Smith','Song Writer', 'olivia@example.com', '$2a$10$WxL.vHS2YHw4hovNoCUCSuv2lh5Lrn6yP4foEbITfJr59zD30.Opa', 'profile_image_one.jpeg1707838272170', true, '');
INSERT INTO users (first_name, last_name, job_description, email, password, profile_image, enabled, apikey)
VALUES ('Noah', 'Brown','Song Writer', 'noah@example.com', '$2a$10$WxL.vHS2YHw4hovNoCUCSuv2lh5Lrn6yP4foEbITfJr59zD30.Opa', 'profile_image_one.jpeg1707838272170', true, '');

INSERT INTO projects (project_owner_id, project_cover_image, project_name, project_artist, project_release)
VALUES ('1','project-img-one.jpg1709670493946', 'CODENAME_NAGA', 'NAGA', '2023');
INSERT INTO projects (project_owner_id, project_cover_image, project_name, project_artist, project_release)
VALUES ('1','project-img-two.jpeg1709672866873', 'New Lights', 'Daniel Bananiel', '2024');

INSERT INTO songs (project_project_id, song_id, song_name, song_url)
VALUES ('1', '9999', 'BEAT_ONE', 'Dilla.mp31709673750468');

INSERT INTO authorities (project_id, user_id, authority)
VALUES ('1', '1', 'ROLE_OWNER');
INSERT INTO authorities (project_id, user_id, authority)
VALUES ('2', '1', 'ROLE_OWNER');

INSERT INTO project_contributors (contributor_id, project_id)
VALUES ('1', '1');
INSERT INTO project_contributors (contributor_id, project_id)
VALUES ('1', '2');
INSERT INTO project_contributors (contributor_id, project_id)
VALUES ('2', '1');
INSERT INTO project_contributors (contributor_id, project_id)
VALUES ('2', '2');
