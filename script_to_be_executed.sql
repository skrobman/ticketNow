create table addresses(
                          id uuid default gen_random_uuid() not null primary key,
                          street varchar(255) not null,
                          state varchar(255) not null,
                          postal_code varchar(255) not null,
                          country varchar(255) not null,
                          created_at  timestamp with time zone default now(),
                          updated_at  timestamp with time zone default now()
);

create table stadiums(
                         id uuid default gen_random_uuid() not null primary key,
                         name varchar(255) not null,
                         created_at  timestamp with time zone default now(),
                         updated_at  timestamp with time zone default now()
);

create table stadium_addresses(
                                  stadium_id uuid not null references stadiums(id),
                                  address_id uuid not null references addresses(id),
                                  created_at  timestamp with time zone default now(),
                                  updated_at  timestamp with time zone default now()
);

create table tickets(
                        id uuid default gen_random_uuid() not null primary key,
                        price float8 not null,
                        created_at  timestamp with time zone default now(),
                        updated_at  timestamp with time zone default now()
);

create table tickets_stadiums(
                                stadium_id uuid not null references stadiums(id),
                                tickets_id uuid not null references tickets(id),
                                created_at  timestamp with time zone default now(),
                                updated_at  timestamp with time zone default now()
);