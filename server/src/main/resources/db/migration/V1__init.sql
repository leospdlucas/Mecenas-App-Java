
create table users (
  id bigserial primary key,
  email varchar(255) not null unique,
  roles varchar(255) not null default 'READER'
);
create table works (
  id bigserial primary key,
  author_id bigint not null references users(id),
  titulo varchar(255) not null,
  categoria varchar(64) not null,
  descricao text not null,
  status varchar(20) default 'ACTIVE'
);
create table demos (
  id bigserial primary key,
  work_id bigint not null references works(id),
  storage_url text not null,
  mime_type varchar(128) not null
);
create table campaigns (
  id bigserial primary key,
  work_id bigint not null unique references works(id),
  meta_brl int not null,
  min_pledge_brl int not null default 500,
  deadline_at timestamp not null
);
create table pledges (
  id bigserial primary key,
  campaign_id bigint not null references campaigns(id),
  user_id bigint not null references users(id),
  valor_brl int not null,
  status varchar(20) default 'PENDING'
);
create table contract_offers (
  id bigserial primary key,
  work_id bigint not null references works(id),
  partner_id bigint not null references users(id),
  author_id bigint not null references users(id),
  termos_json text not null default '{}',
  status varchar(20) default 'DRAFT',
  expira_em timestamp not null
);
create table subscriptions (
  id bigserial primary key,
  org_user_id bigint not null references users(id),
  plano varchar(10) not null,
  preco_brl int not null,
  current_period_end timestamp not null,
  status varchar(20) default 'ACTIVE'
);
