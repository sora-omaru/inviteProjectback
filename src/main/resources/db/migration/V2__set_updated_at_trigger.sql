-- V2__set_updated_at_trigger.sql

create or replace function set_updated_at()
returns trigger as $$
begin
  new.updated_at = now();
  return new;
end;
$$ language plpgsql;

drop trigger if exists trg_invites_updated_at on invites;
create trigger trg_invites_updated_at
before update on invites
for each row execute function set_updated_at();
