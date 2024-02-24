BEGIN TRANSACTION;

INSERT INTO "public"."user_record" (
    "created_at",
    "id",
    "email",
    "first_name",
    "last_name",
    "profile_picture_url",
    "username"
)
VALUES (
    '2024-02-18 23:40:58',
    'e02acbc4-2e43-4dfe-8637-467d6b3b1074',
    'hawksea@mail.com',
    'Sea',
    'Hawk',
    'https://i.ibb.co/4p8FSQ7/SeaHawk.jpg',
    'hawksea'
),
(
    '2024-02-18 23:40:58',
    '1075de58-5fa4-439e-8da0-59cdc6927618',
    'saltsamuel@mail.com',
    'Samuel',
    'Salt',
    'https://i.ibb.co/K72ZnsL/Samuel-Salt.jpg',
    'saltsamuel'
),
(
    '2024-02-18 23:40:58',
    'ead598f0-7a55-4266-a5d5-40e56ec19b88',
    'markelharry@mail.com',
    'Harry',
    'Markel',
    'https://i.ibb.co/DkZf1G6/Harry-Markel.jpg',
    'markelharry'
),
(
    '2024-02-18 23:40:58',
    '2b147a04-64b1-4cc9-9522-596c3e459917',
    'adamsmorgan@email.com',
    'Morgan',
    'Adams',
    'https://i.ibb.co/thZdWgV/Morgan-Adams.jpg',
    'adamsmorgan'
);

COMMIT;