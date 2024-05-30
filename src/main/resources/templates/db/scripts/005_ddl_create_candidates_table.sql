create table candidates
(
    id            serial primary key NOT NULL UNIQUE,
    name         varchar not null,
    description   varchar not null,
    creation_date timestamp,
    city_id       int references cities(id),
    file_id       int references files(id)
);