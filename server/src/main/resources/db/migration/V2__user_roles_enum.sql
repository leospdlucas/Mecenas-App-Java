create table if not exists user_roles (
  user_id bigint not null references users(id) on delete cascade,
  role varchar(20) not null,
  primary key (user_id, role)
);
-- migrate data from old users.roles text column if present
do $$
begin
  if exists (select 1 from information_schema.columns where table_name='users' and column_name='roles') then
    insert into user_roles(user_id, role)
    select id, trim(value) from users, unnest(string_to_array(coalesce(roles,''), ',')) as value where roles is not null and roles <> '';
    alter table users drop column roles;
  end if;
end $$;
