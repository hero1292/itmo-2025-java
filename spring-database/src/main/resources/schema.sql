create table if not exists regions
(
    id      bigserial primary key,
    code    varchar(32)  not null unique,
    name_ru varchar(255) not null,
    name_en varchar(255) not null
);

create table if not exists cities
(
    id         bigserial primary key,
    code       varchar(32)  not null unique,
    name_ru    varchar(255) not null,
    name_en    varchar(255) not null,
    population bigint       not null check (population >= 0),
    region_id  bigint references regions (id) on delete cascade
);

create index if not exists idx_cities_region_id on cities (region_id);

