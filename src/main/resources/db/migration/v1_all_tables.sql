create table payment_provider.card
(
    id          serial
        constraint card_pk
            primary key,
    card_number varchar,
    exp_date    date,
    cvv         varchar,
    amount      numeric
);

alter table payment_provider.card
    owner to postgres;

create table payment_provider.customer
(
    id         serial
        constraint id
            primary key,
    first_name varchar,
    last_name  varchar,
    country    varchar,
    card_id    integer
        constraint customer_card_id_fk
            references payment_provider.card
);

alter table payment_provider.customer
    owner to postgres;

create table payment_provider.webhook
(
    id                integer default nextval('payment_provider.webhook_id_seq'::regclass) not null
        constraint webhook_pk
            primary key,
    notification_url  text,
    attempt           integer,
    last_attempt_time timestamp,
    response_code     integer
);

alter table payment_provider.webhook
    owner to postgres;

create table payment_provider.merchant_wallet
(
    id       integer not null
        constraint merchant_wallet_pk
            primary key,
    amount   numeric,
    currency text
);

alter table payment_provider.merchant_wallet
    owner to postgres;

create table payment_provider.merchant
(
    id         integer not null
        constraint merchant_pk
            primary key,
    name       text,
    secret     text,
    created_at timestamp,
    wallet_id  integer
        constraint merchant_merchant_wallet_id_fk
            references payment_provider.merchant_wallet,
    updated_at timestamp
);

alter table payment_provider.merchant
    owner to postgres;

create table payment_provider.transaction
(
    id             serial
        constraint transaction_id
            primary key,
    status         varchar,
    message        varchar,
    payment_method varchar,
    currency       varchar,
    created_at     timestamp,
    updated_at     timestamp,
    language       varchar,
    customer_id    integer
        constraint transaction_customer_id_fk
            references payment_provider.customer,
    webhook_id     integer
        constraint transaction_webhook_id_fk
            references payment_provider.webhook,
    merchant_id    integer
        constraint transaction_merchant_id_fk
            references payment_provider.merchant,
    card_id        integer
        constraint transaction_card_id_fk
            references payment_provider.card,
    amount         numeric,
    type           varchar
);

alter table payment_provider.transaction
    owner to postgres;

