insert into  authority (id, role ) values (1, 'ADMIN');
insert into  authority (id, role ) values (2, 'FLEET_MANAGER');
insert into  authority (id, role ) values (3, 'PILOT');

insert  into users (id, first_name, last_name, email, username, password, average_landing_rate, hours_flown, pireps, points, booking_id) values (1, 'admin', 'admin', 'admin@gmail.com', 'admin@gmail.com', '{bcrypt10}$2a$08$3hM44FCBLudlb5xpmpxSKucY2Hp5Me2B1wRvcoQ5RjK/NHb4V2QKW',0, 0, 0, 0, NULL);

insert into user_authority (user_id, authority_id) values (1,1);


