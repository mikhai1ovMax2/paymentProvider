insert into payment_provider.transaction
    (id, status, message, payment_method, currency, created_at, updated_at, language, customer_id, webhook_id, merchant_id, card_id, amount, type)
values (22, 'FAILED', '123123', 'CARD', 'BRL', '25/01/2024 09:12:34.413', '25/01/2024 09:12:34.413', 'en', null, null, null, null, 1000, 'PAYMENT')