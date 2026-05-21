create table rooms (
    id bigserial primary key,
    title varchar(255) not null,
    cover_url text not null,
    stream_key varchar(255) not null unique,
    play_url text not null,
    status varchar(20) not null,
    created_at timestamp not null,
    updated_at timestamp not null
);
