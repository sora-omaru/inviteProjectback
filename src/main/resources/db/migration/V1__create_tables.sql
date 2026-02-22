-- V1__create_tables.sql
-- attendance: 0=未回答, 1=出席, 2=欠席

create table if not exists invites (
  id              bigserial primary key,
  invite_token    varchar(64) not null unique,

  name            varchar(200),
  attendance      smallint not null default 0,

  companions_text text,
  allergies_text  text,
  kids_text       text,
  message_text    text,

  created_at      timestamptz not null default now(),
  updated_at      timestamptz not null default now(),

  constraint chk_invites_attendance check (attendance in (0, 1, 2))
);
